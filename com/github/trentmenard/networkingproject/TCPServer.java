package com.github.trentmenard.networkingproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class TCPServer {

    public static void main(String[] args) {

        // Change server port here (in case of conflicts).
        final int SERVER_PORT = 9999;

        try {
            // Create Server Socket
            final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("[Server-Info:] Created new Server on port: " + SERVER_PORT);

            while (!(serverSocket.isClosed())){
                System.out.println("[Server-Info:] Waiting for Client connection...");

                // Accept Incoming Requests (blocks until connection established)
                final Socket socketConnection = serverSocket.accept();
                System.out.println("[Server-Info:] Connected to Client: " + serverSocket.getInetAddress());
                System.out.println("[Server-Info:] Client Games In-progress: " + ForkJoinPool.commonPool().getRunningThreadCount());
                System.out.println("[Server-Info:] Clients Awaiting Game Start: " + ForkJoinPool.commonPool().getQueuedSubmissionCount());
                System.out.println("[Server-Info:] Clients Queued: " + ForkJoinPool.commonPool().getQueuedTaskCount() + "\n");

                // Handle Client on ForkJoinPool (separate threads) & add to count.
                CompletableFuture.runAsync(() -> new TCPClientHandler(socketConnection));
            }

        } catch (IOException e) {
            System.err.println("[Server-Error:] Failed to create a Server on port: " + SERVER_PORT + " (already in use?).");
            e.printStackTrace();
        }
    }
}