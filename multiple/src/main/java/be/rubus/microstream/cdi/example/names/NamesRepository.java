package be.rubus.microstream.cdi.example.names;

import be.rubus.microstream.cdi.example.model.GreenRoot;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class NamesRepository {

    @Inject
    @ConfigProperty(name = "one.microstream.green")
    private StorageManager storageManager;

    private GreenRoot greenRoot;

    @PostConstruct
    public void init() {
        greenRoot = (GreenRoot) storageManager.root();
    }

    public void add(String name) {
        greenRoot.add(name);
    }

    public Set<String> getNames() {
        return greenRoot.get();
    }

}
