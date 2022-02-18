package com.github.trentmenard.networkingproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.in;

public class TCPClient {
    private Socket socket = null;
    private ServerSocket serverSocket;
    private BufferedReader dIStream;
    private DataOutputStream dOStream;
    private final String address;
    private final int port;

    public TCPClient(String address, int port){
        this.address = address;
        this.port = port;
        this.connect(address, port);
    }

    void connect(String address, int port){
        try {
            this.socket = new Socket(address, port);
            System.out.println("[Info:] Connected to Server: " + socket.getLocalAddress());

            // Input Stream (from terminal)
            dIStream = new BufferedReader(new InputStreamReader(in));

            // Output Stream (to socket)
            this.dOStream = new DataOutputStream(socket.getOutputStream());

            String line = "";

            while (!(line.equals("@exit"))){
                line = dIStream.readLine();
                dOStream.writeUTF(line);

                // Count to 10.
                for (int i = 0; i <= 10; i++)
                    System.out.println(i + "...\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
