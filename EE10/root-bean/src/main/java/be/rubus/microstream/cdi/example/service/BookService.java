package be.rubus.microstream.cdi.example.service;

import be.rubus.microstream.cdi.example.database.Root;
import be.rubus.microstream.cdi.example.model.Book;
import one.microstream.storage.types.StorageManager;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {

    @Inject
    private StorageManager storageManager;

    private Root root;

    @PostConstruct
    private void init() {
        root = (Root) storageManager.root();
    }

    public List<Book> getAll() {
        return root.getBooks();
    }

    public Optional<Book> getBookByISBN(String isbn) {
        return root.getBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findAny();
    }

}
