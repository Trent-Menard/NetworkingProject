package com.github.trentmenard.networkingproject;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.in;

public class TCPClient {

    public static void main(String[] args) {
        // Change server port here
        final int SERVER_PORT = 9999;

        // Change host address here
        // (Could default to loopback address but trying to make this
        // program more "generic". I.e.: ability to connect to host's other
        // than this machine)
        final String HOST_ADDRESS = "localhost";

        final Scanner clientInput = new Scanner(in);

        // Create socket connection.
        try {
            Socket connectionSocket = new Socket(InetAddress.getByName(HOST_ADDRESS), SERVER_PORT);
            System.out.println("[Info:] Connected to Server: " + connectionSocket.getLocalAddress());

            // Server -> Client
            DataInputStream clientServerInput = new DataInputStream(connectionSocket.getInputStream());

            // Client -> Server
            DataOutputStream clientServerOutput = new DataOutputStream(connectionSocket.getOutputStream());

            String toServer = "";

            while((!toServer.equals("@exit"))){
                System.out.print("Enter Response: ");
                toServer = clientInput.nextLine();
                clientServerOutput.writeUTF(toServer);
            }

            connectionSocket.close();
            System.out.println("[Client-Info] You terminated the connection.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
