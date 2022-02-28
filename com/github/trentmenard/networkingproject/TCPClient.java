package com.github.trentmenard.networkingproject;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static java.lang.System.in;

public class TCPClient {

    public static void main(String[] args) {
        // Change server port here
        final int SERVER_PORT = 9999;

        // Change host address here (could default to loopback but trying to generify program)
        final String HOST_ADDRESS = "localhost";

        String toServer, fromServer;

        // Create socket connection (try-with-resources auto-closes).
        try(Socket connectionSocket = new Socket(InetAddress.getByName(HOST_ADDRESS), SERVER_PORT);
            DataInputStream clientServerInput = new DataInputStream(connectionSocket.getInputStream());
            DataOutputStream clientServerOutput = new DataOutputStream(connectionSocket.getOutputStream());
            final Scanner clientInput = new Scanner(in)) {

            System.out.println("[Info:] Connected to Server: " + connectionSocket.getLocalAddress());

            while(!(connectionSocket.isClosed())){

                fromServer = clientServerInput.readUTF();
                System.out.println("[Server]: " + fromServer + "\n");

                if (fromServer.equals("Correct! You win! :)"))
                    break;

                System.out.print("Enter Response: ");
                toServer = clientInput.nextLine();
                clientServerOutput.writeUTF(toServer);

                if (toServer.equals("@exit")){
                    connectionSocket.close();
                    System.out.println("[Client-Info] You terminated the connection.");
                }
            }
        } catch (IOException e) {
            System.err.println("[Client-Error:]: Something went wrong during Server communication.");

            if (e.getLocalizedMessage().equalsIgnoreCase("Connection reset"))
                System.err.println("[Client-Error:]: The connection was terminated.");
            else if (e.getLocalizedMessage().equalsIgnoreCase("An established connection was aborted by the software in your host machine"))
                System.err.println("[Client-Error:]: The Server terminated the connection because time ran out. You lost. :(");
            else
                e.printStackTrace();
        }
    }
}