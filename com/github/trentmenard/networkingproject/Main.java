package com.github.trentmenard.networkingproject;

public class Main {

    public static void main(String[] args) {

        // Main doesn't actually do anything. My original plan was to have Main as the (well, main)
        // that would treat the Client & Server as separate entities, allowing multiple to be run from this program,
        // but I ran into issues as I'm not familiar with asynchronous programming. Thus, I resulted to
        // scrapping the idea and having the Client and Server be run separately. I,e.:
        // the Server can be accessed by other Clients not dependent upon this program.

        // Change opening server port here (if port is already in use).
/*        final int SERVER_PORT = 9999;

        TCPServer server = new TCPServer(SERVER_PORT);
        TCPClient client = new TCPClient("127.0.0.1", 9999);*/
    }
}
