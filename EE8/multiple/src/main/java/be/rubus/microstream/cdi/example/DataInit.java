package be.rubus.microstream.cdi.example;

import be.rubus.microstream.cdi.example.model.GreenRoot;
import be.rubus.microstream.cdi.example.model.Product;
import be.rubus.microstream.cdi.example.model.RedRoot;
import one.microstream.integrations.cdi.types.config.StorageManagerInitializer;
import one.microstream.storage.types.StorageManager;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataInit implements StorageManagerInitializer {

    @Override
    public void initialize(StorageManager storageManager) {
        String databaseName = storageManager.databaseName();
        if (databaseName.equals("red")) {
            initRedDatabase(storageManager);
        }
        if (databaseName.equals("green")) {
            initGreenDatabase(storageManager);
        }
    }

    private void initGreenDatabase(StorageManager storageManager) {
        Object root = storageManager.root();
        if (root != null) {
            throw new IllegalStateException("In this test we can only have an empty storage");
        }
        GreenRoot greenRoot = new GreenRoot(storageManager);

        storageManager.setRoot(greenRoot);
        storageManager.storeRoot();

        greenRoot.add("Rudy");
        greenRoot.add("Markus");

    }

    private void initRedDatabase(StorageManager storageManager) {
        Object root = storageManager.root();

        if (root != null) {
            throw new IllegalStateException("In this test we can only have an empty storage");
        }

        RedRoot redRoot = new RedRoot(storageManager);
        storageManager.setRoot(redRoot);
        storageManager.storeRoot();

        redRoot.add(createProduct1());
        redRoot.add(createProduct2());
    }

    private Product createProduct1() {
        Product product = new Product();
        product.setId(1L);
        product.setDescription("A fruit");
        product.setName("banana");
        product.setRating(4);
        return product;
    }

    private Product createProduct2() {
        Product product = new Product();
        product.setId(2L);
        product.setDescription("watermelon sugar ahh");
        product.setName("watermelon");
        product.setRating(3);
        return product;
    }
}
