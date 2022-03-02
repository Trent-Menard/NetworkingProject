package com.github.trentmenard.networkingproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPClientHandler {
    private DataInputStream serverClientInput;
    private DataOutputStream serverClientOutput;
    private final Socket serverClientSocket;
    private final AtomicInteger countdown = new AtomicInteger(90);
    private final AtomicBoolean won = new AtomicBoolean(false);

    public TCPClientHandler(Socket serverClientSocket) {
        this.serverClientSocket = serverClientSocket;
        boolean hasIOStream = getIOStream(serverClientSocket);

        if (hasIOStream)
            this.beginGame();
        else {
            System.err.println("[Server-Error:] Cannot start the game for Client: " + serverClientSocket.getInetAddress() + " as they are lacking an I/O Stream. Closing their connection.");
        }
    }

    private boolean getIOStream(Socket serverClientSocket){
        try {
            this.serverClientInput = new DataInputStream(serverClientSocket.getInputStream());
            this.serverClientOutput = new DataOutputStream(serverClientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("[Server-Error:] Failed to get I/O stream for Client: " + serverClientSocket.getInetAddress() + ".");
            e.printStackTrace();
        }
        return (this.serverClientInput != null && this.serverClientOutput != null);
    }

    private void beginGame(){

        CompletableFuture<Integer> countdownFuture = CompletableFuture.supplyAsync(() -> {
            while(countdown.get() >= 0){
                try {
                    // Completes Future (& exit w/ countdown)
                    if (won.get()){
                        return countdown.get();
                    }
                    // Continues Future
                    else{
                        TimeUnit.SECONDS.sleep(1);
                        countdown.getAndDecrement();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Executes if countdown runs out & player hasn't won
            return countdown.get();
        });

        countdownFuture.thenRun(this::terminateConnection);

        String message =
                """
                Solve. You have 90 seconds.
                Type 'hint' for a hint.
                Type 'help' for this.
                Type '@exit' to end connection.
                
                    0x53 0x6f 0x6c 0x76 0x65 0x20 0x74 0x68 0x65 0x6e
                    0x20 0x74 0x79 0x70 0x65 0x20 0x74 0x68 0x65 0x20
                    0x65 0x6e 0x74 0x69 0x72 0x65 0x20 0x70 0x68 0x72
                    0x61 0x73 0x65 0x20 0x69 0x6e 0x74 0x6f 0x20 0x74
                    0x68 0x65 0x20 0x43 0x6c 0x69 0x65 0x6e 0x74 0x20
                    0x63 0x6f 0x6e 0x73 0x6f 0x6c 0x65 0x3a 0x20 0x54
                    0x68 0x65 0x72 0x65 0x27 0x73 0x20 0x6e 0x6f 0x20
                    0x70 0x6c 0x61 0x63 0x65 0x20 0x6c 0x69 0x6b 0x65
                    0x20 0x31 0x32 0x37 0x2e 0x30 0x2e 0x30 0x2e 0x31
                """;

        String toClient = message;
        String fromClient;

        while (!(this.serverClientSocket.isClosed())){

            // Read Client Input
            try {
                // Send message to Client
                serverClientOutput.writeUTF(toClient);

                // Read response from Client
                fromClient = this.serverClientInput.readUTF().toLowerCase();
                System.out.println("[Server Info:] Client " + this.serverClientSocket.getInetAddress() +  " says: " + fromClient);

                // Close connection if typed.
                if (fromClient.equals("@exit")){
                    this.terminateConnection();
                    System.out.println("[Server Info:] Client " + this.serverClientSocket.getInetAddress() +  " terminated their connection.");
                    break;
                }

                switch (fromClient) {
                    case "hint" -> toClient = "https://onlinehextools.com/convert-hex-to-text\n";
                    case "help" -> toClient = message + "\n";
                    case "home" -> toClient = "Close, but enter the entire phrase.\n";
                    case "there's no place like home", "there is no place like home" -> {
                        serverClientOutput.writeUTF("Correct! You win! :)");
                        this.won.set(true);
                    }
                    default -> toClient = "Wrong. Try again.\n";
                }

                toClient += "Time Remaining: " + this.countdown.get() + " seconds.\n";

            } catch (IOException e) {
                System.err.println("[Server-Error:] Failed to read I/O Stream from Client: " + serverClientSocket.getInetAddress() + ". They likely disconnected so closing this Socket.");
                // Only need to be called if time expires bc otherwise would be called twice.
                if (this.won.get())
                    break;
                this.terminateConnection();
            }
        }
    }

    private void terminateConnection(){
        try {
            this.serverClientSocket.close();
            if (this.won.get()){
                System.out.println("[Server-Info:] Client: " + serverClientSocket.getInetAddress() + " won the game with " + this.countdown.get() + " seconds remaining!");
            }
            else {
                System.out.println("[Server-Info:] Client: " + serverClientSocket.getInetAddress() + " ran out of time.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}