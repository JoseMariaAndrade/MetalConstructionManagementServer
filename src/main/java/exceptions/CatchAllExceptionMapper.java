package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger("exceptions.CatchAllExceptionMapper");

    @Override
    public Response toResponse(Exception exception) {
        String errorMessage = exception.getMessage();
        LOGGER.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}
