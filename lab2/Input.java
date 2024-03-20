package org.example;

import java.util.ArrayList;
import java.util.List;

public class Input {

    private List<Pair> queue = new ArrayList();
    private Integer howMany = 0;

    public synchronized Pair take() throws InterruptedException {
        while(this.queue.isEmpty()){
            this.wait();
        }
        return this.queue.remove(0);
    }


    public synchronized Integer getHowMany(){
        return howMany;
    }

    public synchronized void put(int value, int id){
        if(value > 0){
            Pair a = new Pair();
            a.element = value;
            a.id = id;
            this.queue.add(a);
            howMany++;}
        if (this.queue.size() == 1) {
            this.notifyAll();
        }
    }
}