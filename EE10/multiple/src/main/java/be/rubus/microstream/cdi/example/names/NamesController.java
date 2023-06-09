package be.rubus.microstream.cdi.example.names;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Collection;

@ApplicationScoped
@Path("/names")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NamesController {
    @Inject
    private NamesRepository repository;

    @GET
    public Collection<String> getAll() {
        return repository.getNames();
    }

    @POST
    public void addName(String name) {
        repository.add(name);
    }

}
