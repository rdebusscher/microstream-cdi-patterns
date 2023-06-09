package be.rubus.microstream.cdi.example.product;


import be.rubus.microstream.cdi.example.model.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;

@ApplicationScoped
@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    private ProductRepository repository;

    @GET
    public Collection<Product> getAll() {
        return repository.getAll();
    }

    @DELETE
    @Path("{id}")
    public Response delete(
            @PathParam("id") long id) {
        repository.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
