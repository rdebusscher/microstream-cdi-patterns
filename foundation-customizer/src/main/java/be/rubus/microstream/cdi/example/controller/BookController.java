package be.rubus.microstream.cdi.example.controller;


import be.rubus.microstream.cdi.example.model.Book;
import be.rubus.microstream.cdi.example.service.BookService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookController {

    @Inject
    private BookService bookService;


    @GET()
    public Collection<Book> getAll() {
        return bookService.getAll();
    }


}
