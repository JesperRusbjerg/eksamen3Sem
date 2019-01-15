/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facade.EntityFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("resource")
public class Resource {

    private EntityFacade<> facade;
    private Gson gson;

    public Resource() {
        this.facade = new EntityFacade(.class
        );
        
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResource() {

        String jsonReturn = "";

        return Response.ok().entity(jsonReturn).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postResource(String json) {

        String jsonReturn = "";

        return Response.ok().entity(jsonReturn).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response putJson(String json) {

        String jsonReturn = "";

        return Response.ok().entity(jsonReturn).build();
    }

    @DELETE
    @Path("{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteJson(@PathParam("id") int id) {

        String json = "i will delete " + id;

        return Response.ok().entity(json).build();
    }
}
