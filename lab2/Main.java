package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Input in = new Input();
        Output out = new Output();
        if(args.length <1 ){
            System.out.println("You have 0 calculators to work");
            return;
        }
        List <Thread> calculators = new ArrayList<>();
        for(int i = 0; i < Integer.parseInt(args[0]); i++ ){
            Thread thread = new Thread(new Calculator(in, out, i));
            calculators.add(thread);
            thread.start();
        }
        String a = "0";
        int counter = 0;
        Scanner sc = new Scanner(System.in);
        while(!a.equals("quit")){
            if(!a.equals("")){
                in.put(Integer.parseInt(a), counter);
                counter++;
               }
            a =  sc.nextLine();

        }

        int howMany = calculators.size();
        for(int i = 0; i < howMany; i++){
            Thread thread = (Thread)calculators.get(i);
            thread.interrupt();
        }
        out.print();

    }


}