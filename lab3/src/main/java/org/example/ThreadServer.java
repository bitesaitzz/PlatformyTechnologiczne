package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServer implements Runnable{
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private static ArrayList<ThreadServer> allThreads = new ArrayList<>();
    ThreadServer(Socket socket) throws IOException {
        this.socket = socket;
        allThreads.add(this);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

    }
    @Override
    public void run() {
        System.out.println("Thread started");
        String ready = "ready";
        try {
            this.oos.writeObject(ready);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int number;
        while(true) {

            try {
                number = (Integer) ois.readObject();
                System.out.println("Server got number = " + number);
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        String readyForMessages = "ready for messages";
        try {
            this.oos.writeObject(readyForMessages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < number; i++) {
            Message message;
            try {
                message = (Message) this.ois.readObject();
                if(message.getContent().equals("quit")){
                    oos.writeObject("Error");
                    break;

                }
                System.out.println(message.getNumber() + " " +message.getContent());
                Thread.sleep(100);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String finish = "finish";
        try {
            this.oos.writeObject(finish);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while(true){
            try {
                Message exit = (Message)ois.readObject();
                if(exit.getContent().equals( "exit")){

                    break;
                }
            } catch (IOException e) {
                //throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                //throw new RuntimeException(e);
            }
            catch (ClassCastException e){
                System.out.println("");
            }
        }
        try {
            this.ois.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
            System.out.println("Thread dead");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
