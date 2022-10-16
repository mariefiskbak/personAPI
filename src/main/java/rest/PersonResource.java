package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
import facades.FacadeExample;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("person")
public class PersonResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("p/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson(@PathParam("id") int id) {
        try {
            PersonDTO p = FACADE.getPerson(id);
            return GSON.toJson(p);
        } catch (Exception e) {
            throw new WebApplicationException(String.format("No person with id \"%s\" found", id), 404);
        }
    }
    @GET
    @Path("p/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons() {
        try {
            PersonsDTO p = FACADE.getAllPersons();
            return GSON.toJson(p);
        } catch (Exception e) {
            throw new WebApplicationException(String.format("Could not find all persons"), 404);
        }
    }

    @POST
    @Path("add")
    @Consumes("application/json")
    @Produces("application/json")
    public String addPerson(String personJSON) { // input is the body of the request, generated in the frontend
        PersonDTO personDTO = GSON.fromJson(personJSON, PersonDTO.class);
        if (!Objects.equals(personDTO.getFName(), null)
                && !Objects.equals(personDTO.getLName(), null)
                && !Objects.equals(personDTO.getPhone(), null)) {
            //Person newPerson = new Person(personDTO);
            PersonDTO addedPerson = FACADE.addPerson(personDTO.getFName(), personDTO.getLName(), personDTO.getPhone(), personDTO.getAddress().getAddress(), personDTO.getAddress().getZip(), personDTO.getAddress().getCity());
            //FullPersonDTO fullPersonDTO = new FullPersonDTO(createdPerson);
            return GSON.toJson(addedPerson);
        } else {
            List<String> msg = new ArrayList<>();
            if (Objects.equals(personDTO.getFName(), null)) msg.add("Field \"First name\" is required. ");
            if (Objects.equals(personDTO.getLName(), null)) msg.add("Field \"Last name\" is required. ");
            if (Objects.equals(personDTO.getPhone(), null)) msg.add("Field \"Phone\" is required. ");
            throw new WebApplicationException(String.join("\n", msg), 400);
        }

    }

    @PUT
    @Path("p/edit/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String editPerson(@PathParam("id") String id, String personJSON) {
        PersonDTO personDTO = GSON.fromJson(personJSON, PersonDTO.class);
        int idInt = Integer.parseInt(id);
        personDTO.setId(idInt);
        PersonDTO editedPerson = FACADE.editPerson(personDTO);
        return GSON.toJson(editedPerson);
    }

    @DELETE
    @Path("p/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) {
        PersonDTO deleted = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }


}