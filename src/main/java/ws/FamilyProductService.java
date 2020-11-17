package ws;

import dtos.FamilyProductDTO;
import ejbs.FamilyProductBean;
import entities.FamilyProduct;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/families")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class FamilyProductService {

    @EJB
    FamilyProductBean familyProductBean;

    private FamilyProductDTO toDTO(FamilyProduct familyProduct) {
        return new FamilyProductDTO(
                familyProduct.getName()
        );
    }

    private List<FamilyProductDTO> toDTOs(List<FamilyProduct> familiesProducts) {
        return familiesProducts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<FamilyProductDTO> getAllFamiliesProductsWS() {
        return toDTOs(familyProductBean.getAll());
    }

    @POST
    @Path("/")
    public Response create(FamilyProductDTO familyProductDTO)
            throws MyEntityExistsException, MyConstraintViolationException {

        familyProductBean.create(
                familyProductDTO.getName()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{name}")
    public Response getFamilyDeatils(@PathParam("name") String name) {

        FamilyProduct familyProduct = familyProductBean.findFamilyProduct(name);

        if (familyProduct != null) {
            return Response.status(Response.Status.OK).entity(toDTO(familyProduct)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    public Response update(FamilyProductDTO familyProductDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

//        familyProductBean.update(
//                familyProductDTO.getName()
//        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {

//        familyProductBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
