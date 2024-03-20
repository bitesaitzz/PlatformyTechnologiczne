package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Mage implements Comparable <Mage> {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String var1, int var2, double var3, String var4) {
        name = var1;
        level = var2;
        power = var3;
        if (var4.equals("normal")) {
            apprentices = new TreeSet<>();
        } else if (var4.equals("alternative")) {
            apprentices = new TreeSet<>(new Comparatoring());
        } else {
            apprentices = new HashSet<>();
        }
    }
    @Override
    public int compareTo(Mage o) {
        int compare = 0;
        compare = name.compareTo(o.name);
        if(compare != 0 ){
            return compare;
        }
        compare = Integer.compare(this.level, o.level);
        if(compare != 0){
            return compare;
        }
        compare = Double.compare(this.power, o.power);
        return compare;
    }
    public int compare(Mage o1, Mage o2) {
        int compare = 0;
        compare = Integer.compare(o1.getLevel(), o2.getLevel());
        if(compare != 0){
            return compare;
        }
        compare = o1.getName().compareTo(o2.getName());
        if(compare != 0){
            return compare;
        }

        compare = Double.compare(o1.getPower(), o2.getPower());

        return compare;
    }


    public boolean equals(Mage second){
        if(this.name.compareTo(second.name) == 0 && (this.level == second.level) && (this.power == second.power)){
            return true;
        }
        return false;
    }
    public int hashCode(){
        int hash= 7;
        hash = 31*hash + (int)level;
        hash = 31*hash + (name == null ? 0 : name.hashCode());
        hash = 31*hash + (int)power;
        return hash;

    }
    public String toString(){
        return ("Mage{name='"+(this.name!=null ? this.name : "noname") +"', level="+this.level+", power="+this.power+"}");
    }
    public int getLevel(){
        return this.level;
    }
    public double getPower(){
        return this.power;
    }
    public String getName(){
        return this.name;
    }
    public void addApprentice(Mage mage){
        apprentices.add(mage);
    }

    public void print(int level) {
        System.out.print(level + ".");
        System.out.println(this);
        for (Mage ap : apprentices) {
            System.out.print(level + ".");
            ap.print(level + 1);
        }

    }
    public int getApprenticeNumber(){
        int wynik = 0;
        for(Mage appr:apprentices){
            wynik += appr.getApprenticeNumber()+1;
        }
        return wynik;
    }

    public void getStatisics(int level){
        System.out.print(level + ".");
        System.out.print(this);
        System.out.println(" - "+this.getApprenticeNumber());
        System.out.println();
        int it = 1;
        for (Mage ap : apprentices) {
            System.out.print(level + ".");
            ap.getStatisics(it);
            it++;
        }
    }

    public void getStatistics(String level){
        System.out.println(level+this+" - "+this.getApprenticeNumber());
        int it = 1;
        for (Mage ap: apprentices  ){
            ap.getStatistics("\t"+level+it+".");
            it++;
        }

    }

    public TreeMap<Mage, Integer> counterr(){
        TreeMap<Mage, Integer> counterApp = new TreeMap<>();
        for(Mage mage: apprentices){
            counterApp.put(mage, mage.getApprenticeNumber());
        }
        return counterApp;

    }
}
