package ws;

import dtos.TypeProductDTO;
import ebjs.TypeProductBean;
import entities.TypeProduct;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/types")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class TypeProductService {

    @EJB
    TypeProductBean typeProductBean;

    private TypeProductDTO toDTO(TypeProduct typeProduct) {
        return new TypeProductDTO(
                typeProduct.getDescription()
        );
    }

    private List<TypeProductDTO> toDTOs(List<TypeProduct> typeProducts) {
        return typeProducts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<TypeProductDTO> getAllTypesProductsWS() {
        return toDTOs(typeProductBean.getAll());
    }

    @POST
    @Path("/")
    public Response create(TypeProductDTO typeProductDTO)
            throws MyEntityExistsException, MyConstraintViolationException {

        typeProductBean.create(
                typeProductDTO.getDescription()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{description}")
    public Response getTypeDeatils(@PathParam("description") String description) {

        TypeProduct typeProduct = typeProductBean.findTypeProduct(description);

        if (typeProduct != null) {
            return Response.status(Response.Status.OK).entity(toDTO(typeProduct)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    public Response update(TypeProductDTO typeProductDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

//        familyProductBean.update(
//                familyProductDTO.getName()
//        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{description}")
    public Response delete(@PathParam("description") String description)
            throws MyEntityNotFoundException {

//        familyProductBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
