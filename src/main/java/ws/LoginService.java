package ws;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import ejbs.JwtBean;
import ejbs.UserBean;
import entities.User;
import jwt.Jwt;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.logging.Logger;

@Path("/login")
public class LoginService {

    private static final Logger log = Logger.getLogger(LoginService.class.getName());

    @EJB
    JwtBean jwtBean;

    @EJB
    UserBean userBean;

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response authenticateUser(AuthDTO authDTO) {
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("email") String email,
                                     @FormParam("password") String password) {
        try {
//            User user = userBean.authenticate(authDTO.getUsername(), authDTO.getPassword());
            User user = userBean.authenticate(email, password);
            if (user != null) {
                if (user.getId() != null) {
                    log.info(String.format("Generating JWT for user %s", user.getEmail()));
                }
                String token = jwtBean.createJwt(user.getEmail(), new String[]{user.getClass().getSimpleName()});
                return Response.ok(new Jwt(token)).build();
            }
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/claims")
    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                JWT jwt = JWTParser.parse(auth.substring(7));
                return Response.ok(jwt.getJWTClaimsSet().getClaims()).build();
            } catch (ParseException e) {
                log.warning(e.toString());
                return Response.status(400).build();
            }
        }
        return Response.status(204).build();
    }
}