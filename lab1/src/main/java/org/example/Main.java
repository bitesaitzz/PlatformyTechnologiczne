package org.example;

public class Main {
    public static void main(String[] args) {
        String type;
        if(args.length == 1){
             type = "normal";
        }
        else if (args.length == 2){
             type = "alternative";}
        else {
            type = "hash";
            }
        System.out.println(type);
        Mage mage1 = new Mage("mage1", 1, 1, type);
        Mage mage2 = new Mage("mage2", 2, 2, type);
        Mage mage3 = new Mage("mage3", 3, 3, type);
        Mage mage4 = new Mage("mage4", 4, 4, type);
        Mage mage5 = new Mage("mage5", 5, 5, type);
        Mage mage6 = new Mage("mage6", 4, 6, type);
        Mage mage7 = new Mage("mage7", 3, 7, type);
        Mage mage8 = new Mage("mage8", 2, 8, type);
        Mage mage9 = new Mage("mage9", 1, 9, type);
        Mage mage10 = new Mage("mage10", 1, 10, type);

        mage1.addApprentice(mage2);
        mage1.addApprentice(mage3);
        mage1.addApprentice(mage4);
        mage2.addApprentice(mage5);
        mage2.addApprentice(mage6);
        mage5.addApprentice(mage7);


        mage1.getStatistics("1.");
        System.out.println(mage1.counterr());


    }
}