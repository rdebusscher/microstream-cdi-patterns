package be.rubus.microstream.cdi.example.product;

import be.rubus.microstream.cdi.example.model.RedRoot;
import be.rubus.microstream.cdi.example.model.Product;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ProductRepository {

    @Inject
    @ConfigProperty(name = "one.microstream.red")
    private StorageManager storageManager;

    private RedRoot redRoot;

    @PostConstruct
    public void init() {
        redRoot = (RedRoot) storageManager.root();
    }

    public Collection<Product> getAll() {
        return this.redRoot.getProducts();
    }


    public void deleteById(long id) {
        redRoot.deleteById(id);
    }
}
