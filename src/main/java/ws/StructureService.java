package ws;

import dtos.ProductDTO;
import dtos.StructureDTO;
import ejbs.StructureBean;
import entities.Product;
import entities.Structure;
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

@Path("/structures")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StructureService {

    @EJB
    private StructureBean structureBean;

    private StructureDTO toDTO(Structure structure) {
        return new StructureDTO(
                structure.getName(),
                structure.getProject().getName(),
                structure.getNb(),
                structure.getLVao(),
                structure.getQ()
        );
    }

    private StructureDTO toDTOProducts(Structure structure) {

        StructureDTO structureDTO = new StructureDTO(
                structure.getName(),
                structure.getProject().getName(),
                structure.getNb(),
                structure.getLVao(),
                structure.getQ()
        );

        List<ProductDTO> structureDTOS = toDTOProducts(structure.getProducts());
        structureDTO.setProducts(structureDTOS);

        return structureDTO;
    }

    private List<StructureDTO> toDTOs(List<Structure> structures) {
        return structures.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ProductDTO> toDTOProducts(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getFamilyProduct().getTypeProduct().getDescription(),
                product.getFamilyProduct().getName(),
                product.getManufacturer().getId()
        );
    }

    @GET
    @Path("/")
    public List<StructureDTO> getAllStructuresWS() {
        return toDTOs(structureBean.getAll());
    }

    @GET
    @Path("{name}")
    public Response getProjectDetails(@PathParam("name") String name) {

        Structure structure = structureBean.findStructure(name);

        if (structure != null)
            return Response.status(Response.Status.OK).entity(toDTOProducts(structure)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR").build();
    }

    @POST
    @Path("/")
    public Response create(StructureDTO structureDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {
        System.out.println(structureDTO.getName() + " - " + structureDTO.getProducts().toString());
        structureBean.create(structureDTO.getName(), structureDTO.getProject(), structureDTO.getNb(), structureDTO.getLVao(), structureDTO.getQ());

        for (ProductDTO productDTO : structureDTO.getProducts()) {
            structureBean.productOnStru(productDTO.getName(), structureDTO.getName());
        }

        return Response.status(Response.Status.CREATED).build();
    }

//    @PUT
//    public Response update(ProjectDTO projectDTO)
//            throws MyEntityNotFoundException, MyConstraintViolationException {
//
//        projectBean.update(
//                projectDTO.getName(),
//                projectDTO.getIdClient(),
//                projectDTO.getIdDesigner()
//        );
//
//        return Response.status(Response.Status.CREATED).build();
//    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {

        structureBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
