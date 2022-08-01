package be.rubus.microstream.cdi.example;

import be.rubus.microstream.cdi.example.database.Root;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class DataConfiguration {

    @Inject
    @ConfigProperty(name = "one.microstream.config")
    private String microStreamConfigLocation;

    @Inject
    private DataInit dataInit;

    @Produces
    @ApplicationScoped
    public StorageManager defineStorageManager() {

        EmbeddedStorageFoundation<?> embeddedStorageFoundation = embeddedStorageFoundation();
        // do additional configuration
        /*
        storageFoundation.onConnectionFoundation(BinaryHandlersJDK8::registerJDK8TypeHandlers);
        storageFoundation.onConnectionFoundation(f -> f.registerCustomTypeHandler());
        storageFoundation.onConnectionFoundation(f -> f
                        .getCustomTypeHandlerRegistry()
                        .registerLegacyTypeHandler(
                                new LegacyTypeHandlerBook()
                        )
        );

         */

        StorageManager storageManager = embeddedStorageFoundation.start();

        // Check Root available within StorageManager
        Root root = (Root) storageManager.root();
        boolean initRequired = false;
        if (root == null) {
            root = new Root();
            initRequired = true;
        }
        // Prep Root
        root.setStorageManager(storageManager);

        // Init 'database' with some data
        if (initRequired) {
            dataInit.init(root, storageManager);
            storageManager.setRoot(root);
            storageManager.storeRoot();
        }
        return storageManager;
    }

    private EmbeddedStorageFoundation<?> embeddedStorageFoundation() {

        return EmbeddedStorageConfiguration.load(microStreamConfigLocation)
                .createEmbeddedStorageFoundation();

    }

    public void dispose(@Disposes StorageManager manager) {

        manager.close();
    }
}
