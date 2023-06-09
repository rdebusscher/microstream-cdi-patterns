package be.rubus.microstream.cdi.example.model;

import one.microstream.persistence.types.Persister;

import java.util.*;

public class RedRoot {

    private transient Persister persister;

    public RedRoot(Persister persister) {
        this.persister = persister;
    }

    private final Set<Product> products = new HashSet<>();

    public void add(Product product) {
        Objects.requireNonNull(product, "Product is required");
        products.add(product);
        persister.store(products);
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(this.products);
    }

    public Optional<Product> findById(long id) {
        return this.products.stream().filter(p -> p.getId() == id).findAny();
    }

    public void deleteById(final long id) {
        products.removeIf(p -> p.getId() == id);
        persister.store(products);
    }

}