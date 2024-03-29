package org.example;
import javax.accessibility.AccessibleStateSet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MageControllerTest {

    @org.junit.jupiter.api.Test
    void save1() {
        MageRep mageRep = mock(MageRep.class);
        MageController mageController = new MageController(mageRep);
        doNothing().when(mageRep).save(new Mage("lesha", 12));
        assertEquals(mageController.save("lesha", 12), "done");
    }
    @org.junit.jupiter.api.Test
    void save2(){
        MageRep mageRep = mock(MageRep.class);
        MageController mageController = new MageController(mageRep);
        doThrow(IllegalArgumentException.class).when(mageRep).save(new Mage("lesha", 12));
        assertEquals(mageController.save("lesha", 12), "bad request");
    }

    @org.junit.jupiter.api.Test
    void delete1() {
        MageRep mageRep = mock(MageRep.class);
        MageController mageController = new MageController(mageRep);
        doNothing().when(mageRep).delete("lesha");
        assertEquals(mageController.delete("lesha"), "done");
    }
    @org.junit.jupiter.api.Test
    void delete2() {
        MageRep mageRep = mock(MageRep.class);
        MageController mageController = new MageController(mageRep);
        doThrow(IllegalArgumentException.class).when(mageRep).delete("lesha");
        assertEquals(mageController.delete("lesha"), "not found");
    }
    @org.junit.jupiter.api.Test
  void find1(){
      MageRep mageRep = mock(MageRep.class);
      MageController mageController = new MageController(mageRep);
      when(mageRep.find("lesha")).thenReturn(Optional.empty());
      assertEquals(mageController.find("lesha"), "not found");
  }
    @org.junit.jupiter.api.Test
  void find2(){
      MageRep mageRep = mock(MageRep.class);
      MageController mageController = new MageController(mageRep);
      Mage mage = new Mage("lesha", 12);
      when(mageRep.find("lesha")).thenReturn(Optional.of(mage));
      assertEquals(mageController.find("lesha"), "Mage{name='lesha', level=12}");
  }




}