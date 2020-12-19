package ws;

import dtos.ProductDTO;
import ejbs.ProductBean;
import entities.Product;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {

    @EJB
    ProductBean productBean;

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getFamilyProduct().getTypeProduct().getDescription(),
                product.getFamilyProduct().getName(),
                product.getManufacturer().getId()
        );
    }

    private List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProductDTO> getAllProductsWS() {
        return toDTOs(productBean.getAll());
    }

    @GET
    @Path("{name}")
    public Response getProductDetails(@PathParam("name") String name)
            throws MyEntityNotFoundException {

        Product product = productBean.findProduct(name);

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @POST
    @Path("/")
    public Response create(ProductDTO productDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyIllegalArgumentException, MyEntityNotFoundException {

        productBean.create(
                productDTO.getName(),
                productDTO.getFamily(),
                productDTO.getManufacturer()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(ProductDTO productDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

//        productBean.update(
//                productDTO.getName(),
//                productDTO.getFamily(),
//                productDTO.getManufacturer()
//        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {
        productBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
