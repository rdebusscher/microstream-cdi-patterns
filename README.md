# MicroStream Jakarta Usage Patterns

Patterns in using MicroStream's CDI integration

This repository contains a few example projects that demonstrate how MicroStream and
the MicroStream CDI/MicroProfile Config integration can be used in a project.

It is not the idea to have all best practices around application development covered
in these examples but only some patterns that can be used to work with the Object Graph that
makes your database within the JVM heap (and persist data with MicroStream).  
So aspects like security and observability are not covered but are also not affected by using MicroStream in your Jakarta application.

## General concepts

The following are a few general concepts that are applied within the examples.

- The examples are mainly centered around using JAX-RS endpoints to process user requests.
- The *Controller* classes are responsible for defining the JAX-RS endpoint signatures (HTTP method, URLs, ...) and some validation on the received data (structures). So the _Controller_ is not responsible for validating the business rules (does the user exists, can the customer place an order, ...) but merely some validation of the received values/structures (Are values in a certain range like non-negative for age, does the supplied JSON has a property email, ...)
- The *Service* classes are responsible for validating the business rules of your application.  They should not have any JAX-RS-specific relation so in case there is a problem with a request, a RuntimeException-based exception can be thrown.

## Example

The example supports (in a limited way) the management of a library.  You have the concept books and users who can lend a book.

You can perform the following actions

- Retrieve all known books.
- Retrieve all known users.
- Add a new User.
- Update the email address of a User.
- Retrieve the books assigned to a user.
- Assign a book to the user.

Although the application is still limited in functionality, it is already a bit more elaborated than a hello-world style application. With a hello-world style example, we would have the danger of showing patterns that do not apply to the real world.

# Plain

Without the integration code, see directory _plain_.

Without any integration code, you need to expose a CDI bean for the `StorageManager`. This is performed by the `DataConfiguration` class.  The class is injected with the configuration value `one.microstream.config` which will give us the location of the MicroStream configuration file. It also has a reference to the class that performs the initialisation of books and users when the storage is started for the very first time.

The CDI producer method performs the different steps in a similar way as when you use MicroStream in a Java SE program.  It creates the `EmbeddedStorageFoundation` with the configuration file, it customizes the Foundation, creates the _StorageManager_, and initializes with data if needed.

# Lazily started StorageManager

Todo

# Proposed changes

The following is the list of proposed changes to make the code (of your application) better structured and integration with MicroStream easier.

The examples require the code from ???TODO??? to compile and work.

# Foundation customizer

See directory _foundation-customizer_

The new version of the integration has implemented the following steps so that you can use a _StorageManager_ based CDI bean. (and as the developer keep full control of customizations and initializations)

- Build `EmbeddedStorageFoundation` from the MicroProfile Configuration values
- Allow customizations by the developer `EmbeddedStorageFoundation` using `EmbeddedStorageFoundationCustomizer`
- Integration creates the `StorageManager`
- Allow initialization of the `StorageManager` (like adding initial data when storage is created at the first run) through `StorageManagerInitializer`.

The code within the `DataConfiguration` of the _plain_ example becomes now better structured and results in the classes `FoundationCustomizer` and `RootPreparation`.

```
@ApplicationScoped
public class FoundationCustomizer implements EmbeddedStorageFoundationCustomizer {

    @Override
    public void customize(EmbeddedStorageFoundation embeddedStorageFoundation) {
      // Do customization
    }
}
```

```
@ApplicationScoped
public class RootPreparation implements StorageManagerInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootPreparation.class);

    @Override
    public void initialize(StorageManager storageManager) {
       // Check if Root is available (and assign if needed) and add initial data if needed.
    }
}    
```

The addition of these 2 interfaces and using them when the CDI Bean for the _StorageManager_ is created, makes the code better structured as each class has its own purpose.
It also allows to make use of all the MicroProfile Config sources that your runtime supports to define the configuration of the MicroStream engine.
