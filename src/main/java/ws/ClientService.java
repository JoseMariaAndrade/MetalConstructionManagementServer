package ws;

import dtos.ClientDTO;
import dtos.ProjectDTO;
import ejbs.ClientBean;
import ejbs.ProjectBean;
import entities.Client;
import entities.Project;
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

@Path("/clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientService {

    @EJB
    ClientBean clientBean;

    @EJB
    ProjectBean projectBean;

    private ClientDTO toDTONoProjects(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getPassword(),
                client.getEmail(),
                client.getContact(),
                client.getMorada()
        );
    }

    private List<ClientDTO> toDTOs(List<Client> clients) {
        return clients.stream().map(this::toDTONoProjects).collect(Collectors.toList());
    }

    private ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO(
                client.getId(),
                client.getName(),
                client.getPassword(),
                client.getEmail(),
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
                project.getClient().getId(),
                project.getClient().getName(),
                project.getDesigner().getId(),
                project.getDesigner().getName()
        );
    }

    @GET
    @Path("/")
    public List<ClientDTO> getAllClientsWS() {
        return toDTOs(clientBean.getAll());
    }

    @POST
    @Path("/")
    public Response create(ClientDTO clientDTO)
            throws MyEntityExistsException, MyConstraintViolationException {

        clientBean.create(
                clientDTO.getName(),
                clientDTO.getPassword(),
                clientDTO.getEmail(),
                clientDTO.getContact(),
                clientDTO.getAddress()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("{id}/approve/{nameProject}")
    public Response create(@PathParam("id") Long id, @PathParam("nameProject") String nameProject)
            throws MyEntityNotFoundException, MyIllegalArgumentException, MyConstraintViolationException {

        clientBean.approveProject(id, nameProject);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response getClientDeatils(@PathParam("id") Long id)
            throws MyEntityNotFoundException {

        Client client = clientBean.findClient(id);

        if (client != null) {
            return Response.status(Response.Status.OK).entity(toDTO(client)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    public Response update(ClientDTO clientDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        clientBean.update(
                clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getPassword(),
                clientDTO.getEmail(),
                clientDTO.getContact(),
                clientDTO.getAddress()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id)
            throws MyEntityNotFoundException {

        clientBean.delete(id);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
