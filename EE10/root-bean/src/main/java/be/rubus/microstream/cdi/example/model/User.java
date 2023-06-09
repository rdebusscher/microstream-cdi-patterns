package be.rubus.microstream.cdi.example.model;

import be.rubus.microstream.cdi.example.dto.CreateUser;
import one.microstream.persistence.types.Persister;

import jakarta.json.bind.annotation.JsonbTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends CreateUser {

    private final String id;
    // We don't need a technical id when using MicroStream but need some kind of identification of the instance.
    // but email address is not a good candidate as that might change.
    @JsonbTransient
    private final List<Book> books = new ArrayList<>();

    public User(String name, String email) {
        super(name, email);
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }


    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book, Persister persister) {
        books.add(book);
        // Since we don't like to expose the actual list of Books (through getBooks)  as that
        // means we could alter the list outside the root, we provide the StorageManager as
        // parameter.
        persister.store(books);
    }
}
