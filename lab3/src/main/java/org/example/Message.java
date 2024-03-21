package org.example;

import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private int number;

    Message(String content, int number){
        this.content = content;
        this.number = number;
    }
    public String getContent(){
        return this.content;
    }
    public int getNumber() {
        return this.number;
    }
    void setContent(String content){
        this.content = content;
    }
    void setNumber(int number){
        this.number = number;
    }
}
