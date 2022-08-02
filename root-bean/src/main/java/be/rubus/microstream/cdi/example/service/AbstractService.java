package be.rubus.microstream.cdi.example.service;

import be.rubus.microstream.cdi.example.database.Locks;
import be.rubus.microstream.cdi.example.database.Root;

import javax.inject.Inject;

public abstract class AbstractService {

    @Inject
    protected Locks locks;

    @Inject
    protected Root root;
}
