package org.example;

import java.util.Optional;

public class MageController {
    private MageRep repository;
    public MageController(MageRep repository) {
        this.repository = repository;
    }
    public String find(String name) {
        Optional<Mage> mage = this.repository.find(name);
        if(mage.isPresent()){
            return mage.get().toString();
        }
        else{
            return "not found";
        }
    }
    public String delete(String name) {
        try{
            this.repository.delete(name);
            return "done";
        }
        catch(IllegalArgumentException e){
            return "not found";
        }
    }
    public String save(String name, int level) {
        try{
            this.repository.save(new Mage(name, level));
            return "done";
        }
        catch(IllegalArgumentException e){
            return "bad request";
        }
    }

}
