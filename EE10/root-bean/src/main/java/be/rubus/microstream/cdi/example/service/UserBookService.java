package be.rubus.microstream.cdi.example.service;

import be.rubus.microstream.cdi.example.exception.BookAlreadyAssignedException;
import be.rubus.microstream.cdi.example.exception.BookNotFoundException;
import be.rubus.microstream.cdi.example.exception.UserNotFoundException;
import be.rubus.microstream.cdi.example.model.Book;
import be.rubus.microstream.cdi.example.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class UserBookService extends AbstractService {

    private static final Object USER_BOOK_LOCK = new Object();

    @Inject
    private UserService userService;

    @Inject
    private BookService bookService;

    public void addBookToUser(String id, String isbn) {
        synchronized (USER_BOOK_LOCK) {
            Optional<User> byId = userService.getById(id);
            if (byId.isEmpty()) {
                throw new UserNotFoundException();
            }
            Optional<Book> bookByISBN = bookService.getBookByISBN(isbn);
            if (bookByISBN.isEmpty()) {
                throw new BookNotFoundException();
            }

            User user = byId.get();
            Book book = bookByISBN.get();
            if (user.getBooks().contains(book)) {
                throw new BookAlreadyAssignedException();
            }
            root.addBookToUser(user, book);
        }
    }
}
