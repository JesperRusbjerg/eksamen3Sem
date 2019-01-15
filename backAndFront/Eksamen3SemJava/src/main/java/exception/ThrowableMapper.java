/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author adamlass
 */
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable exception) {
        JsonObject jo = new JsonObject();
        jo.addProperty("errorTitle", "Something went wrong!");
        jo.addProperty("errorMessage", "Please try again.");
        jo.addProperty("status", 400);
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new Gson().toJson(jo))
                .build();
    }
    
}
