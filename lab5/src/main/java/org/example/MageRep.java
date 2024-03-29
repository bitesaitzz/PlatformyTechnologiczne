package org.example;
import java.util.HashSet;
import java.util.Collection;
import java.util.Optional;

public class MageRep {
    private Collection<Mage> collection = new HashSet<Mage>();

    public MageRep() {
    }
    public void save(Mage mage) throws IllegalArgumentException {
        if (!this.collection.add(mage)) {
            throw new IllegalArgumentException();
        }
    }
    public void delete(String name) throws IllegalArgumentException {
        if (this.collection.isEmpty() || !this.find(name).isPresent() || !this.collection.remove(this.find(name).get())) {
            throw new IllegalArgumentException();
        }
    }
    public Optional <Mage> find(String name) throws IllegalArgumentException {
        for (Mage mage : this.collection) {
            if (mage.getName().equals(name)) {
                return Optional.of(mage);
            }
        }
        return Optional.empty();
    }
}
