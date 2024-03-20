package org.example;

import java.util.Comparator;

public class Comparatoring implements Comparator<Mage> {
    @Override
    public int compare(Mage o1, Mage o2) {
        int compare = 0;
        compare = Double.compare(o1.getPower(), o2.getPower());


        if(compare != 0 ){
            return compare;
        }
        compare = Integer.compare(o1.getLevel(), o2.getLevel());
        if(compare != 0){
            return compare;
        }
        compare = o1.getName().compareTo(o2.getName());
        return compare;
    }
}
