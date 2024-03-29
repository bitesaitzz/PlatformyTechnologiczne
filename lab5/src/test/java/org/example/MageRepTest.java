package org.example;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MageRepTest {

    @Test
    void save1() {
        MageRep mageRepository = new MageRep();
        assertEquals(mageRepository.find("Noname"), Optional.empty());
    }

    @Test
    void save2() {
        MageRep mageRepository = new MageRep();
        Mage mage = new Mage("lesha", 20);
        mageRepository.save(mage);
        assertEquals(mageRepository.find("lesha"), Optional.of(mage));
    }

    @Test
    void delete1() {
        MageRep mageRepository = new MageRep();
        Mage mage = new Mage("lesha", 20);
        mageRepository.save(mage);
        try{
            mageRepository.delete("lesha");
        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    void delete2(){
        MageRep mageRepository = new MageRep();
        try{
            mageRepository.delete("lesha");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    @Test
    void find1() {
        MageRep mageRepository = new MageRep();
        Mage mage = new Mage("lesha", 20);
        mageRepository.save(mage);
        assertEquals(mageRepository.find("lesha"), Optional.of(mage));
    }
    @Test
    void find2(){
        MageRep mageRepository = new MageRep();
        assertEquals(mageRepository.find("lesha"), Optional.empty());
    }
}