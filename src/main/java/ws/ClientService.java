package ws;

import dtos.ClientDTO;
import dtos.ProductDTO;
import dtos.ProjectDTO;
import dtos.StructureDTO;
import ejbs.ClientBean;
import ejbs.StructureBean;
import entities.Client;
import entities.Product;
import entities.Project;
import entities.Structure;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientService {

    @EJB
    ClientBean clientBean;

    @EJB
    StructureBean structureBean;

    @GET
    @Path("{clientName}/projects/{projectName}/structures/{structureName}")
    public Response getClientProjectStructures(@PathParam("clientName") String clientName, @PathParam("projectName") String projectName, @PathParam("structureName") String structureName) throws MyEntityNotFoundException {

        Structure structure = structureBean.findStructure(structureName);

        if (structureName == null) {
            throw new MyEntityNotFoundException("Estrutura com o nome "+structureName+" não existe.");
        }

        return Response.status(Response.Status.OK).entity(toDTO(structure)).build();
    }

    @GET
    @Path("{clientName}/projects")
    public Response getClientProjects(@PathParam("clientName") String clientName) throws MyEntityNotFoundException {
        Client client = clientBean.findClient(clientName);

        if (client == null) {
            throw new MyEntityNotFoundException("Cliente com o nome "+ clientName +" não existe");
        }

        return Response.status(Response.Status.OK).entity(toProjectDTOs(client.getProjects())).build();
    }

    private List<ProjectDTO> toProjectDTOs(List<Project> projects) {
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response create(ClientDTO clientDTO)
            throws MyEntityExistsException, MyConstraintViolationException {

        clientBean.create(
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getContact(),
                clientDTO.getAddress(),
                clientDTO.getPassword()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{name}")
    public Response getClientDetails(@PathParam("name") String name) {

        Client client = clientBean.findClient(name);

        if (client != null) {
            return Response.status(Response.Status.OK).entity(toDTO(client)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Path("/")
    public Response update(ClientDTO clientDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        clientBean.update(
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getContact(),
                clientDTO.getAddress()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}/delete")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {

        clientBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/")
    public List<ClientDTO> getAllClientsWS() {
        return toDTOs(clientBean.getAll());
    }


    private ClientDTO toDTONoProjects(Client client) {
        return new ClientDTO(
                client.getName(),
                client.getEmaill(),
                client.getContact(),
                client.getMorada()
        );
    }

    private List<ClientDTO> toDTOs(List<Client> clients) {
        return clients.stream().map(this::toDTONoProjects).collect(Collectors.toList());
    }

    private ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO(
                client.getName(),
                client.getEmaill(),
                client.getContact(),
                client.getMorada()
        );

        List<ProjectDTO> projectDTOS = projectsToDTOs(client.getProjects());
        clientDTO.setProjects(projectDTOS);

        return clientDTO;
    }

    private List<ProjectDTO> projectsToDTOs(List<Project> projects) {
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProjectDTO toDTO(Project project) {
        return new ProjectDTO(
                project.getName(),
                project.getClient().getName(),
                project.getDesigner().getName(),
                toStructureDTOs(project.getStructures())
        );
    }

    private StructureDTO toStructureDTONoProducts(Structure structure) {

        StructureDTO structureDTO = new StructureDTO(
                structure.getName(),
                structure.getProject().getName()
        );

        return structureDTO;
    }

    private List<StructureDTO> toStructureDTOs(List<Structure> structures) {
        return structures.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private StructureDTO toDTO(Structure structure){

        StructureDTO structureDTO = new StructureDTO(
                structure.getName(),
                structure.getProject().getName(),
                toProductDTOs(structure.getProducts())
        );

        return structureDTO;
    }

    private List<ProductDTO> toProductDTOs(List<Product> products){
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product){

        ProductDTO productDTO = new ProductDTO(
                product.getName(),
                product.getTypeProduct().getDescription(),
                product.getFamilyProduct().getName(),
                product.getManufacturer().getName()
        );

        return productDTO;
    }

}


