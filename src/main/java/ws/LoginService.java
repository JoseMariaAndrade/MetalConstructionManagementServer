//package ws;
//
//import com.nimbusds.jwt.JWT;
//import com.nimbusds.jwt.JWTParser;
//import dtos.AuthDTO;
//import ejbs.JwtBean;
//import ejbs.UserBean;
//import entities.User;
//import jwt.Jwt;
//
//import javax.ejb.EJB;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.text.ParseException;
//import java.util.logging.Logger;
//
//@Path("/login")
//@Consumes({MediaType.APPLICATION_JSON})
//@Produces({MediaType.APPLICATION_JSON})
//public class LoginService {
//
//    private static final Logger log = Logger.getLogger(LoginService.class.getName());
//
//    @EJB
//    JwtBean jwtBean;
//
//    @EJB
//    UserBean userBean;
//
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("/token")
//    public Response authenticateUser(AuthDTO authDTO) {
//        try {
//
//            System.out.println(authDTO.getEmail());
//            User user = userBean.authenticate(authDTO.getEmail(), authDTO.getPassword());
//            System.out.println(user);
////            User user = userBean.authenticate(email, password);
//            if (user != null) {
//                if (user.getId() != null) {
//                    log.info(String.format("Generating JWT for user %s", user.getEmail()));
//                }
//                String token = jwtBean.createJwt(user.getEmail(), new String[]{user.getClass().getSimpleName()});
////                return Response.ok(new Jwt(token)).build();
//                return Response.status(Response.Status.OK).build();
//            }
//        } catch (Exception exception) {
//            log.info(exception.getMessage());
//        }
//
//        return Response.status(Response.Status.ACCEPTED).build();
//    }
//
//    @GET
//    @Path("/claims")
//    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
//        if (auth != null && auth.startsWith("Bearer ")) {
//            try {
//                JWT jwt = JWTParser.parse(auth.substring(7));
//                return Response.ok(jwt.getJWTClaimsSet().getClaims()).build();
//            } catch (ParseException e) {
//                log.warning(e.toString());
//                return Response.status(400).build();
//            }
//        }
//        return Response.status(204).build();
//    }
//}