package org.example;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TowerRep {
    private EntityManager em;
    public TowerRep(EntityManager em){
        this.em = em;
    }
    public List<Tower> showAll(){
        return em.createQuery("from Tower").getResultList();
    }
    public void update(Tower tower){
        try {
            em.getTransaction().begin();
            em.refresh(tower);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Tower>save(Tower tower){
        try {
            em.getTransaction().begin();
            em.persist(tower);
            em.getTransaction().commit();
            return Optional.of(tower);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void remove(Tower tower){
        em.getTransaction().begin();
        for (Mage mage : tower.getMages()){
            em.remove(mage);
        }
        em.remove(tower);
        em.getTransaction().commit();
    }
}
