package be.rubus.microstream.cdi.example.controller;

import be.rubus.microstream.cdi.example.dto.CreateUser;
import be.rubus.microstream.cdi.example.model.User;
import be.rubus.microstream.cdi.example.service.UserBookService;
import be.rubus.microstream.cdi.example.service.UserService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {


    @Inject
    private UserService userService;

    @Inject
    private UserBookService userBookService;

    @GET
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        Optional<User> byId = userService.getById(id);

        Response.ResponseBuilder builder = byId.map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND));
        return builder.build();

    }

    @GET
    @Path("/by/{email}")
    public Response findBy(@PathParam("email") String email) {
        Optional<User> byEmail = userService.findByEmail(email);

        Response.ResponseBuilder builder = byEmail.map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND));
        return builder.build();
    }

    @POST
    public Response addUser(CreateUser user, @Context UriInfo uriInfo) {
        User savedUser = userService.add(user);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(savedUser.getId());
        return Response.created(uriBuilder.build()).entity(savedUser).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        userService.removeById(id);
        return Response.noContent().build();
    }


    @PATCH
    @Path("/{id}")
    public Response updateEmail(@PathParam("id") String id, JsonObject json) {
        String email = json.getString("email");
        if (email == null || email.isBlank()) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        User user = userService.updateEmail(id, email);
        return Response.ok(user).build();
    }

    @GET
    @Path("/{id}/book")
    public Response getUserBooks(@PathParam("id") String id) {
        Optional<User> byId = userService.getById(id);
        if (byId.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(byId.get().getBooks()).build();
    }

    @POST
    @Path(value = "/{id}/book/{isbn}")
    public void addBookToUser(@PathParam("id") String id, @PathParam("isbn") String isbn) {
        userBookService.addBookToUser(id, isbn);
    }
}
