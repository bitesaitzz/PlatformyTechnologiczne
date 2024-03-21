package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Klient {
    public Socket socket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public boolean isOk = true;
    Klient(Socket socket) throws IOException {
        this.socket = socket;
        OutputStream outputStream = socket.getOutputStream();
        this.oos = new ObjectOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        this.ois = new ObjectInputStream(inputStream);
    }
    public void waitt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mes = "";

                while (socket.isConnected() && isOk==true) {
                    try {
                       mes = (String)ois.readObject();
                        if (mes.equals("Error")){
                            isOk = false;
                            System.out.println(mes);

                        }

                    } catch (IOException e) {
                        closeEverything(socket, oos,ois );
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }
    public void closeEverything(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 9998;
        Socket socket = new Socket("127.0.0.1", port);
        Scanner sc = new Scanner(System.in);
        System.out.println("Client has started");

        Klient klient = new Klient(socket);
        String answer = "";

            while(true){
                answer = (String)klient.ois.readObject();
                System.out.println(answer);
                if(answer.equals("ready")){
                    break;
                }
            }
            int number = sc.nextInt();
        klient.oos.writeObject(number);

            while(!answer.equals("ready for messages")){
                answer = (String)klient.ois.readObject();
                System.out.println(answer);
            }
            sc.nextLine();
            Message mess = new Message("Empty", 1);
            klient.waitt();
            for(int i = 0 ;  i < number; i++){


                mess = new Message(sc.nextLine(), i+1);
                //mess.setContent(sc.nextLine());
                //mess.setNumber(i+1);
                if (mess.getContent().equals("")){
                    mess.setContent("empty message");
                }
                klient.oos.writeObject(mess);
                if(!klient.isOk){
                    break;
                }
                if (i == number -2 ){
                    klient.isOk= false;
                }
            }


            while(!answer.equals("finish")){
                answer = (String)klient.ois.readObject();
                System.out.println(answer);
            }

        klient.oos.writeObject("exit");
            System.out.println("The end of work.");
        klient.ois.close();
        klient.oos.close();
            socket.close();





    }

}
