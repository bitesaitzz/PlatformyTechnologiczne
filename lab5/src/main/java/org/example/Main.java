package org.example;

public class Main {
    public static void main(String[] args) {
        MageRep mageRep = new MageRep();
        MageController mageController = new MageController(mageRep);

        System.out.println(mageController.save("pasha", 5));
        System.out.println(mageController.save("masha", 6));
        System.out.println(mageController.save("lesha", 7));
        System.out.println(mageController.save("lesha", 7));
        System.out.println('\n');
        System.out.println(mageController.find("pasha"));
        System.out.println(mageController.find("sasha"));
        System.out.println('\n');
        System.out.println(mageController.delete("masha"));
        System.out.println(mageController.delete("sasha"));
        System.out.println('\n');
        System.out.println('\n');
        System.out.println(mageRep.find("pasha"));
        System.out.println(mageRep.find("sasha"));
        System.out.println("\n");
        mageRep.save(new Mage("ilya", 9));
        //mageRep.save(new Mage("ilya", 9));
        System.out.println("\n");
        mageRep.delete("ilya");
        //mageRep.delete("ilya");


    }
}