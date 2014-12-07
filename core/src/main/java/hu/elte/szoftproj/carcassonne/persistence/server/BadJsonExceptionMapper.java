package hu.elte.szoftproj.carcassonne.persistence.server;

import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.ErrorDto;
import com.fasterxml.jackson.core.JsonParseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadJsonExceptionMapper implements ExceptionMapper<JsonParseException >
{

    @Override
    public Response toResponse(JsonParseException exception)
    {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorDto("ERROR_BAD_REQUEST", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
