package hu.elte.szoftproj.carcassonne.persistence.server;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.ErrorDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException>
{

    @Override
    public Response toResponse(UnrecognizedPropertyException exception)
    {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorDto("ERROR_UNKNOWN_PROPERTY", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
