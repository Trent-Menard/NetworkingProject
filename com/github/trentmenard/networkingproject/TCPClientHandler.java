package com.github.trentmenard.networkingproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TCPClientHandler extends Thread {
    final DataInputStream serverInputStream;
    final DataOutputStream serverOutputStream;

    final Socket clientSocket;

    public TCPClientHandler(Socket clientSocket, DataInputStream serverInputStream, DataOutputStream serverOutputStream){
        this.clientSocket = clientSocket;
        this.serverInputStream = serverInputStream;
        this.serverOutputStream = serverOutputStream;
    }

    @Override
    public void run() {
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleWithFixedDelay(new Runnable() {
            int countdown = 20;
            @Override
            public void run() {
                countdown-=10;

                if (countdown == 0){
                    System.out.println("[Server Info:] Client ran out of time. Stopping the Server.");
                    System.exit(0);
                    scheduler.shutdown();
                }
            }
        }, 10, 10, TimeUnit.SECONDS);

        String fromClient;
        String toClient;

        // Keep connection alive.
        while(true){
            try {
                // Send message to Client.
                toClient =
                """
                [Server Info] Solve. You have 60 seconds. Type 'hint' for a hint or '@exit' to end connection.
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
                serverOutputStream.writeUTF(toClient);

                // Read Client response.
                fromClient = this.serverInputStream.readUTF().toLowerCase();

                // Close connection if typed.
                if (fromClient.equals("@exit")){
                    this.clientSocket.close();
                    System.out.println("[Server Info:] Client " + this.clientSocket.getInetAddress() +  " terminated their connection.");
                    break;
                }

                switch (fromClient) {
                    case "hint" -> {
                        toClient = "https://onlinehextools.com/convert-hex-to-text\n";
                        serverOutputStream.writeUTF(toClient);
                    }
                    case "home" -> {
                        toClient = "Close, but enter the entire phrase.";
                        serverOutputStream.writeUTF(toClient);
                    }
                    case "there's no place like home" -> {
                        toClient = "Correct! You win!";
                        serverOutputStream.writeUTF(toClient);
                        this.clientSocket.close();
                    }
                    default -> {
                        toClient = "Wrong. Try again.\n";
                        serverOutputStream.writeUTF(toClient);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Close streams.
        try {
            this.serverInputStream.close();
            this.serverOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}