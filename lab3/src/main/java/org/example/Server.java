package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args) {
        int port = 9998;
        while(true){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Waiting for connection on port 9999");
            Socket fromClienSocket =  serverSocket.accept();
            System.out.println("Is connected!");
            new Thread(new ThreadServer(fromClienSocket)).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }}
    }
}