package com.github.trentmenard.networkingproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private Socket socket = null;
    private ServerSocket serverSocket;
    private DataInputStream iStream;
    private final int port;

    public TCPServer(int port){
        this.port = port;
        System.out.println("[Info:] Started Server using Port: " + port);
        this.start();
    }

    private void start(){
        System.out.println("[Info:] Waiting for Client connection...");
        try {
            // Create socket
            this.serverSocket = new ServerSocket(port);

            // Allow connections.
            this.serverSocket.accept();
            System.out.println("[Info:] Connected to Client: " + this.serverSocket.getLocalSocketAddress());

            // Create BufferedReader to read from socket.
            this.iStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            System.out.println("[Info:] Listening for Input (use @exit to close connection.): ");

            String line = "";

            while (!(line.equals("@exit"))){
                line = iStream.readUTF();
                System.out.println(line);

                // Count to 10.
                for (int i = 0; i <= 10; i++)
                    System.out.println(i + "...\n");
            }

            System.out.println("[Info:] Closing Connection.");
            this.socket.close();
            this.iStream.close();
        } catch (IOException e) {
            System.err.println("[Error:] Client refused.");
            e.printStackTrace();
        }
    }
}