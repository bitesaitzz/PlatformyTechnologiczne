package org.example;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.stream.events.EntityDeclaration;
import java.lang.reflect.Array;
import java.util.*;


public class Main {
    public static void new_element( MageRep magerep, TowerRep towerrep,  Map<String, Tower> towersMap, List<String> mages ){
        Scanner sc = new Scanner(System.in);

        System.out.println("What new element?\n1.Tower\n2.Mage\n3.Leave\n");
        int option = sc.nextInt();
        while(true){
            switch(option){
                case 1:{
                    System.out.println("Print name of tower!\n");

                    String str = sc.nextLine();
                    str = sc.nextLine();
                    while(true){
                        if(towersMap.containsKey(str)){
                            System.out.println("Try another\n");
                            str = sc.nextLine();
                        }
                        else {
                            System.out.println("Enter Height");
                            int height = sc.nextInt();

                            Tower tower = new Tower(str, height);
                            towersMap.put(str, tower);
                            towerrep.save(tower);
                            return;
                        }

                    }
                }
                case 2:{
                    System.out.println("Print name of mage!");
                    String name = sc.nextLine();
                    name = sc.nextLine();
                    while(true){
                        if(mages.contains(name)){
                            System.out.println("Try another\n");
                            name = sc.nextLine();
                        }
                        else {
                            System.out.println("Enter Level");
                            int level = sc.nextInt();

                            System.out.println("Enter name of tower");
                            String str = sc.nextLine();
                            str = sc.nextLine();
                            while(true){
                                if(towersMap.containsKey(str)){
                                    Mage magic = new Mage(name, level);
                                    mages.add(magic.getName());
                                    towersMap.get(str).addMage(magic);
                                    towerrep.save(towersMap.get(str));
                                    return;
                                }
                                else{
                                    System.out.println("Try another");
                                    str = sc.nextLine();
                                }
                            }


                        }

                    }

                }
                case 3:{
                    break;
                }
                default:{
                    System.out.println("Something gets wrong! Please repeat\n");
                }
            }
        }

    }

    public static void showAll( MageRep magerep, TowerRep towerrep){
        Scanner sc = new Scanner(System.in);
        System.out.println("What show?\n1.Tower\n2.Mage\n3.Leave\n");

        int option = sc.nextInt();

        while(true) {
            switch (option) {
                case 1: {
                    List<Tower> towers = towerrep.showAll();
                    towers.forEach(System.out::println);
                    return;
                }
                case 2: {
                    List<Mage> magess = magerep.showAll();
                    magess.forEach(System.out::println);
                    return;
                }
                case 3: {
                    return;
                }
                default: {
                    System.out.println("Try again");
                    option = sc.nextInt();
                }

            }
        }

    }

    public static void query(EntityManager em, MageRep magerep, TowerRep towerrep,  Map<String, Tower> towersMap, List<String> mages){
        Scanner sc = new Scanner(System.in);
        System.out.println("What query?\n1.Pobranie wszystkie wież niższych niż\n2.Pobranie wszystkich magów z poziomem wyższym niż z danej wieży\n3.Pobranie wszystkich magów z poziomem większym niż\n4.Leave\n");
        int option = sc.nextInt();
        while(true){
            switch(option){
                case 1:
                {
                    System.out.println("Give x");
                    int number = sc.nextInt();
                    List<Tower> tmp = em.createQuery("SELECT t FROM Tower t WHERE t.height > :x"). setParameter("x", number).getResultList();

                    tmp.forEach(System.out::println);
                    return;
                }
                case 2:
                {
                    System.out.println("Give tower name");
                    String name = sc.nextLine();
                    name = sc.nextLine();
                    while(true){
                        if(towersMap.containsKey(name)){
                            Tower tower = towersMap.get(name);
                            System.out.println("Give me maximum level of mage");
                            int number = sc.nextInt();
                            List<Mage> magess= em.createQuery("SELECT m FROM Mage m WHERE m.tower = :x AND m.level > :y").setParameter("x", tower).setParameter("y", number).getResultList();
                            magess.forEach(System.out::println);
                            return;
                        }
                        else {
                            System.out.println("Try again\n");
                            name = sc.nextLine();
                        }
                    }
                }
                case 3:
                {
                    System.out.println("Give x");
                    int number = sc.nextInt();
                    List<Mage> tmp = em.createQuery("SELECT m FROM Mage m WHERE m.level > :x"). setParameter("x", number).getResultList();
                    tmp.forEach(System.out::println);
                    return;

                }
                case 4:{
                    return;
                }
                default:{
                    System.out.println("Try again!\n");
                }
            }
        }
    }

    public static void delete( MageRep magerep, TowerRep towerrep,  Map<String, Tower> towersMap, List<String> mages){
        Scanner sc = new Scanner(System.in);
        System.out.println("Who do you want to delete?\n1.tower\n2.mage\n3.leave");
        int option = sc.nextInt();
        while(true) {
            switch (option){
                case 1:{
                    System.out.println("Enter name of tower you want to delete");
                    String name = sc.nextLine();
                    name = sc.nextLine();
                    while (true){
                        if(towersMap.containsKey(name)){
                            for( Mage mage : magerep.showAll()){
                                if(mage.getTower().getTower_name().equals(name)) {
                                    mages.remove(mage.getName());
                                    magerep.remove(mage);
                                }
                            }
                            towerrep.remove(towersMap.get(name));
                            towersMap.remove(name);
                            return;
                        }
                        else{
                            System.out.println("Try again!");
                            name=  sc.nextLine();
                        }
                    }

                }
                case 2:{
                    System.out.println("Enter name of mage you want to delete");
                    String name = sc.nextLine();
                    name = sc.nextLine();
                    while(true) {
                        if (mages.contains(name)) {
                            magerep.remove(magerep.findName(name).get());
                            mages.remove(name);
                            return;
                        }else{
                            System.out.println("Try another\n");
                            name = sc.nextLine();
                        }
                    }

                }
                case 3:{
                    return;
                }
                default:{
                    System.out.println("try again!\n");
                    option = sc.nextInt();
                }
            }
        }}

    public static void main(String[] args) {

        boolean first = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("labPu");
        EntityManager em = emf.createEntityManager();
        MageRep magerep = new MageRep(em);
        TowerRep towerrep = new TowerRep(em);

        List<String> mages = new ArrayList<>();
        Map<String, Tower> towersMap = new HashMap<String, Tower>();
        if(first) {
            Tower tower1 = new Tower("Grodno", 1000);
            Tower tower2 = new Tower("Rudensk", 500);
            Tower tower3 = new Tower("Pinsk", 10);
            Mage mage1 = new Mage("pashok", 1);
            Mage mage2 = new Mage("leha", 1);
            Mage mage3 = new Mage("arsen", 14);
            Mage mage4 = new Mage("ilya", 124);
            Mage mage5 = new Mage("antoha", 14);
            Mage mage6 = new Mage("bob", 124);
            Mage mage7 = new Mage("alice", 12);

            tower1.addMage(mage1);
            tower1.addMage(mage2);
            tower1.addMage(mage3);
            tower1.addMage(mage4);
            tower3.addMage(mage5);
            tower3.addMage(mage6);
            tower3.addMage(mage7);
            towerrep.save(tower1);
            towerrep.save(tower1);
            towerrep.save(tower2);
            towerrep.save(tower3);
        }
        List<Tower> towersFull = towerrep.showAll();

        for (Tower towerFull : towersFull){
            towersMap.put(towerFull.getTower_name(), towerFull);
        }
        for( Mage mage : magerep.showAll()){
            mages.add(mage.getName());
        }
        System.out.println("\n\n\n\n\n\n\t Start");
        String options = "Enter number what you want:\n1.New Element\n2.Get Elements\n3.Query\n4.Delete element\n5.End\n";
        System.out.println(options);
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        while(option != 5){
            switch(option){
                case 1:{
                    new_element( magerep, towerrep,  towersMap, mages );
                    break;
                }
                case 2:{
                    showAll(  magerep,  towerrep);
                    break;
                }
                case 3:{
                    query( em,  magerep,  towerrep,  towersMap, mages);
                    break;
                }
                case 4:{
                    delete( magerep, towerrep,  towersMap, mages );
                    break;
                }
                default:{
                    System.out.println("Something gets wrong!\nPlease repeat");

                }
            }
            System.out.println(options);
            option = sc.nextInt();
        }

    }
}