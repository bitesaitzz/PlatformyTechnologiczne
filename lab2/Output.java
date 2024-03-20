package org.example;

public class Output {
    private String answers;
    public int howManyDone = 0;
    public Output(){
        answers = "";
    }
    public synchronized void put(String answer){
        answers += answer;
        answers += "\n";
    }

    public void print(){
        System.out.println(answers);
    }

}