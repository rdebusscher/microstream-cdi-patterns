package be.rubus.microstream.cdi.example.service;

import be.rubus.microstream.cdi.example.database.Locks;
import be.rubus.microstream.cdi.example.database.Root;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public abstract class AbstractService {

    @Inject
    @ConfigProperty(name = "one.microstream.config")
    protected StorageManager storageManager;

    @Inject
    protected Locks locks;

    protected Root root;

    @PostConstruct
    private void init() {
        root = (Root) storageManager.root();
    }
}
