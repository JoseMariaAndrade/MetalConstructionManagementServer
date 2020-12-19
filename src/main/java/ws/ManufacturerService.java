package ws;

import dtos.ManufacturerDTO;
import dtos.ProductDTO;
import dtos.VariantDTO;
import dtos.VariantesDTO;
import ejbs.ManufacturerBean;
import ejbs.ProductBean;
import entities.Manufacturer;
import entities.Product;
import entities.Variante;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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

    private ProductDTO toDTOVariantes(Product product, Boolean needStock) {
        return new ProductDTO(
                product.getName(),
                needStock,
                product.getFamilyProduct().getTypeProduct().getDescription(),
                product.getFamilyProduct().getName()

        );
    }

    private ProductDTO toDTOStock(Product product, Boolean needStock) {
        return new ProductDTO(
                product.getName(),
                needStock,
                product.getFamilyProduct().getTypeProduct().getDescription(),
                product.getFamilyProduct().getName()
        );
    }

    private ProductDTO toDTOStockVariantes(Product product) {
        ProductDTO productDTO = new ProductDTO(
                product.getName(),
                product.getNeedStock(),
                product.getFamilyProduct().getTypeProduct().getDescription(),
                product.getFamilyProduct().getName()
        );

        List<VariantesDTO> variantesDTOS = variantesToDTOs(product.getVariantes());
        productDTO.setVariantes(variantesDTOS);

        return productDTO;
    }

    private ProductDTO toDTOStockVariantesComDados(Product product) {

        List<VariantDTO> variantsDTOS = new ArrayList<>();
        if (product.getVariantes().size() == 0) {
            // do nothing
        } else {
            variantsDTOS = variantsToDTOs(product.getVariantes());
        }
        ProductDTO productDTO = new ProductDTO(
                product.getName(),
                product.getNeedStock(),
                product.getFamilyProduct().getTypeProduct().getDescription(),
                product.getFamilyProduct().getName(),
                variantsDTOS
        );

        return productDTO;
    }

    private List<VariantesDTO> variantesToDTOs(List<Variante> variantes) {
        return variantes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<VariantDTO> variantsToDTOs(List<Variante> variantes) {
        return variantes.stream().map(this::toDTOComDados).collect(Collectors.toList());
    }

    private VariantesDTO toDTO(Variante variante) {
        return new VariantesDTO(
                variante.getNome()
        );
    }

    private VariantDTO toDTOComDados(Variante variante) {
        return new VariantDTO(variante.getCodigo(), variante.getProduto().getName(), variante.getNome(), variante.getWeff_p(), variante.getWeff_n(), variante.getAr(), variante.getSigmaC());

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

        manufacturerBean.verifyStockProducts(id, name);

        Product product = productBean.findProduct(name);

        if (product == null) {
            throw new MyEntityNotFoundException("Product not found");
        }

        return Response.status(Response.Status.OK).entity(toDTOStockVariantes(product)).build();
    }

    @GET
    @Path("{id}/product/{name}/variants")
    public Response getDetailsProductVariants(@PathParam("id") Long id, @PathParam("name") String name)
            throws MyEntityNotFoundException {

        manufacturerBean.verifyStockProducts(id, name);

        Product product = productBean.findProduct(name);

        if (product == null) {
            throw new MyEntityNotFoundException("Product not found");
        }

        ProductDTO productDTO = toDTOStockVariantesComDados(product);

        return Response.status(Response.Status.OK).entity(productDTO).build();
    }

    @POST
    @Path("{id}/product/create")
    public Response createProduct(@PathParam("id") Long id, ProductDTO productDTO)
            throws MyEntityNotFoundException, MyIllegalArgumentException, MyEntityExistsException, MyConstraintViolationException {

        Manufacturer manufacturer = manufacturerBean.findManufacturer(id);

        productBean.create(productDTO.getName(), productDTO.getFamily(), manufacturer.getId());

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}/product/{name}/update")
    public Response updateProduct(@PathParam("id") Long id, @PathParam("name") String name, ProductDTO productDTO)
            throws MyEntityNotFoundException, MyIllegalArgumentException, MyEntityExistsException, MyConstraintViolationException {

        productBean.update(name, productDTO.getFamily(), id);
//        productBean.create(productDTO.getName(), productDTO.getFamily(), manufacturer.getId());

        return Response.status(Response.Status.ACCEPTED).build();
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
