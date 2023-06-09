package be.rubus.microstream.cdi.example.controller;


import be.rubus.microstream.cdi.example.model.Book;
import be.rubus.microstream.cdi.example.service.BookService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.Optional;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookController {

    @Inject
    private BookService bookService;


    @GET()
    public Collection<Book> getAll() {
        return bookService.getAll();
    }

    @GET()
    @Path("{isbn}")
    public Response getByIsbn(@PathParam("isbn") String isbn) {
        Optional<Book> byISBN = bookService.getBookByISBN(isbn);
        if (byISBN.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(byISBN.get()).build();
        }
    }
}
