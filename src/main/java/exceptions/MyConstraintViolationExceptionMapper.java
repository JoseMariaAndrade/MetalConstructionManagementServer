package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyConstraintViolationExceptionMapper implements ExceptionMapper<MyConstraintViolationException> {

    private static final Logger LOGGER = Logger.getLogger("exceptions.MyConstraintViolationException");

    @Override
    public Response toResponse(MyConstraintViolationException e) {
        String errorMessage = e.getMessage();
        LOGGER.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
    }
}
