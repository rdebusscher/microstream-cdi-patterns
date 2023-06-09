package be.rubus.microstream.cdi.example.names;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
