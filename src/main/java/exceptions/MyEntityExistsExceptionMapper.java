package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyEntityExistsExceptionMapper implements ExceptionMapper<MyEntityExistsException> {

    private static final Logger LOGGER = Logger.getLogger("exceptions.MyEntityExistsExceptionMapper");

    @Override
    public Response toResponse(MyEntityExistsException e) {
        String errorMessage = e.getMessage();
        LOGGER.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
    }
}
