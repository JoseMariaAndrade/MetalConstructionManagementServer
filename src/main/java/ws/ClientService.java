package ws;

import dtos.ClientDTO;
import dtos.EmailDTO;
import dtos.ProjectDTO;
import dtos.StructureDTO;
import ejbs.ClientBean;
import ejbs.DesignerBean;
import ejbs.EmailBean;
import ejbs.ProjectBean;
import entities.Client;
import entities.Project;
import entities.Structure;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.mail.MessagingException;
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
    DesignerBean designerBean;

    @EJB
    ProjectBean projectBean;

    @EJB
    EmailBean emailBean;

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

    private ClientDTO toDTONoStructures(Client client) {
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
        return projects.stream().map(this::toDTONoStructures).collect(Collectors.toList());
    }

    private ProjectDTO toDTONoStructures(Project project) {
        return new ProjectDTO(
                project.getName(),
                project.getClient().getId(),
                project.getClient().getName(),
                project.getDesigner().getId(),
                project.getDesigner().getName(),
                project.getDecision(),
                project.getObservation()
        );
    }

    private ProjectDTO toDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO(
                project.getName(),
                project.getClient().getId(),
                project.getClient().getName(),
                project.getDesigner().getId(),
                project.getDesigner().getName(),
                project.getDecision(),
                project.getObservation()
        );

        List<StructureDTO> structureDTOS = structuresToDTOS(project.getStructures());
        projectDTO.setStructures(structureDTOS);

        return projectDTO;
    }

    private List<StructureDTO> structuresToDTOS(List<Structure> structures) {
        return structures.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private StructureDTO toDTO(Structure structure) {
        return new StructureDTO(
                structure.getName(),
                structure.getProject().getName()
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
    @Path("{id}/project/{nameProject}")
    public Response create(@PathParam("id") Long id, @PathParam("nameProject") String nameProject, ProjectDTO projectDTO)
            throws MyEntityNotFoundException, MyIllegalArgumentException, MyConstraintViolationException {

        clientBean.clientDecision(id, nameProject, projectDTO.getDecision(), projectDTO.getObservation());

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response getClientDetails(@PathParam("id") Long id)
            throws MyEntityNotFoundException {

        Client client = clientBean.findClient(id);

        if (client == null)
            throw new MyEntityNotFoundException("");

        return Response.status(Response.Status.OK).entity(toDTONoStructures(client)).build();
    }

    @GET
    @Path("{id}/project/{name}")
    public Response getClientProject(@PathParam("id") Long id, @PathParam("name") String name)
            throws MyEntityNotFoundException {

        Client client = clientBean.findClient(id);

        if (client == null)
            throw new MyEntityNotFoundException("");

        Project project = projectBean.findProject(name);

        if (project == null)
            throw new MyEntityNotFoundException("");

        List<Project> projects = client.getProjects();

        for (Project project1 : projects) {
            if (project1.getName().equals(project.getName())) {
                return Response.status(Response.Status.OK).entity(toDTO(project1)).build();
            }
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

    @POST
    @Path("/{id}/email/send")
    public Response sendEmail(@PathParam("id") Long id, EmailDTO email)
            throws MyEntityNotFoundException, MessagingException {

        Client client = clientBean.findClient(id);

        if (client == null)
            throw new MyEntityNotFoundException("");

        Project project = projectBean.findProject(email.getProject());
        if (project == null)
            throw new MyEntityNotFoundException("");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(project.getName());
        stringBuilder.append(" - ");
        stringBuilder.append(email.getSubject());

        emailBean.send(project.getDesigner().getEmail(), stringBuilder.toString(), email.getMessage());
        return Response.status(Response.Status.OK).entity("Email sent").build();
    }
}
