package com.github.trentmenard.networkingproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) {

        // Change server port here (in case of conflicts).
        final int SERVER_PORT = 9999;

        // Not the best practice to group all these potential exceptions, but it
        // should be fine for this program's case.
        try {
            // Create Server Socket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("[Server-Info:] Created new Server on port: " + SERVER_PORT);
            System.out.println("[Server-Info:] Waiting for Client connection...");

            // Accept Incoming Requests (blocks until connection established)
            Socket socketConnection = serverSocket.accept();
            System.out.println("[Server-Info:] Connected to Client: " + serverSocket.getInetAddress());

            // Get Client/Server Input stream.
            System.out.println("[Server-Info:] Listening for Client input.");

            DataInputStream clientServerInput = new DataInputStream(socketConnection.getInputStream());

            String fromClient;

            // Keeps connection alive until Client sends '@exit' (or connection is closed, ofc.)
            while(!(socketConnection.isClosed())){
                fromClient = clientServerInput.readUTF();
                System.out.println("[Server-Info:] Client " + socketConnection.getInetAddress() +  " says: " + fromClient);

                if (fromClient.equals("@exit")){
                    socketConnection.close();
                    System.out.println("[Server-Info] Client " + socketConnection.getInetAddress() + " terminated their connection.");
                }
            }

        } catch (IOException e) {
            System.err.println("[Server-Error:] Something went wrong during Server setup.");
            e.printStackTrace();
        }
    }
}