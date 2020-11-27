package ws;

import dtos.ManufacturerDTO;
import dtos.ProductDTO;
import ejbs.ManufacturerBean;
import ejbs.ProductBean;
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

    @EJB
    ProductBean productBean;

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
                product.getFamilyProduct().getTypeProduct().getDescription(),
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
    public Response getManufacturerDetails(@PathParam("id") Long id)
            throws MyEntityNotFoundException {

        Manufacturer manufacturer = manufacturerBean.findManufacturer(id);

        return Response.status(Response.Status.OK).entity(toDTO(manufacturer)).build();
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

    @GET
    @Path("{id}/product/{name}")
    public Response getDetailsProduct(@PathParam("id") Long id, @PathParam("name") String name)
            throws MyEntityNotFoundException {

        Manufacturer manufacturer = manufacturerBean.findManufacturer(id);

        System.out.println(name);
        Product product = productBean.findProduct(name);

        for (Product p : manufacturer.getProducts()
        ) {
            if (p.getName().equals(product.getName()))
                return Response.status(Response.Status.OK).entity(toDTO(p)).build();
        }


        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("{id}")
    public Response createProduct(@PathParam("id") Long id, ProductDTO productDTO)
            throws MyEntityNotFoundException, MyIllegalArgumentException, MyEntityExistsException, MyConstraintViolationException {

        Manufacturer manufacturer = manufacturerBean.findManufacturer(id);

        productBean.create(productDTO.getName(), productDTO.getFamily(), manufacturer.getId());

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{id}/product/{name}")
    public Response deleteProduct(@PathParam("id") Long id, @PathParam("name") String name)
            throws MyEntityNotFoundException {

        manufacturerBean.deleteProduct(id, name);
        productBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
