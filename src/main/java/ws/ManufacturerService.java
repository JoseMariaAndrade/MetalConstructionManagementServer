package ws;

import dtos.ManufacturerDTO;
import dtos.ProductDTO;
import ejbs.ManufacturerBean;
import entities.Manufacturer;
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

@Path("/manufacturers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ManufacturerService {

    @EJB
    ManufacturerBean manufacturerBean;

    private ManufacturerDTO toDTONoProducts(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getId(),
                manufacturer.getName(),
                manufacturer.getPassword(),
                manufacturer.getEmail()
        );
    }

    private ManufacturerDTO toDTO(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(
                manufacturer.getId(),
                manufacturer.getName(),
                manufacturer.getPassword(),
                manufacturer.getEmail()
        );

        List<ProductDTO> productDTOS = productsToDTOs(manufacturer.getProducts());
        manufacturerDTO.setProducts(productDTOS);

        return manufacturerDTO;
    }

    private List<ProductDTO> productsToDTOs(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getTypeProduct().getDescription(),
                product.getFamilyProduct().getName()
        );
    }

    private List<ManufacturerDTO> toDTOsNoProducts(List<Manufacturer> manufacturers) {
        return manufacturers.stream().map(this::toDTONoProducts).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ManufacturerDTO> getAllManufacturersWS() {
        return toDTOsNoProducts(manufacturerBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getManufacturerDetails(@PathParam("id") Long id) {

        Manufacturer manufacturer = manufacturerBean.findManufacturer(id);

        if (manufacturer != null)
            return Response.status(Response.Status.OK).entity(toDTO(manufacturer)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR").build();
    }

    @POST
    @Path("/")
    public Response create(ManufacturerDTO manufacturerDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyIllegalArgumentException {

        manufacturerBean.create(
                manufacturerDTO.getName(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getEmail()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(ManufacturerDTO manufacturerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        manufacturerBean.update(
                manufacturerDTO.getId(),
                manufacturerDTO.getName(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getEmail()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id)
            throws MyEntityNotFoundException {

        manufacturerBean.delete(id);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
