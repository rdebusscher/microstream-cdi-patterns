
package be.rubus.microstream.cdi.example.model;

import one.microstream.persistence.types.Persister;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class GreenRoot {

    private transient Persister persister;

    private final Set<String> names = new HashSet<>();

    public GreenRoot(Persister persister) {
        this.persister = persister;
    }

    public void add(String name) {
        names.add(Objects.requireNonNull(name, "name is required"));
        persister.store(names);
    }

    public Set<String> get() {
        return Collections.unmodifiableSet(names);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GreenRoot names = (GreenRoot) o;
        return Objects.equals(this.names, names.names);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(names);
    }

    @Override
    public String toString() {
        return "GreenRoot{"
                +
                "names="
                + names
                +
                '}';
    }
}
