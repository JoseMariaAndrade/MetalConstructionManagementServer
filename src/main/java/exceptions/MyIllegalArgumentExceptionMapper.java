package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyIllegalArgumentExceptionMapper implements ExceptionMapper<MyIllegalArgumentException> {

    private static final Logger LOGGER = Logger.getLogger("exceptions.MyIllegalArgumentException");

    @Override
    public Response toResponse(MyIllegalArgumentException e) {
        String errorMessage = e.getMessage();
        LOGGER.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
