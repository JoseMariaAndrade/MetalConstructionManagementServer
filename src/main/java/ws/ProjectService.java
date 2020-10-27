package ws;

import dtos.ProjectDTO;
import ebjs.ProjectBean;
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

@Path("/projects")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectService {

    @EJB
    private ProjectBean projectBean;

    private ProjectDTO toDTO(Project project){
        ProjectDTO projectDTO = new ProjectDTO(
                project.getName(),
                project.getClient().getName(),
                project.getDesigner().getName()
        );

        return projectDTO;
    }

    private List<ProjectDTO> toDTOs(List<Project> projects){
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProjectDTO> getAllProjectsWS() {
        return toDTOs(projectBean.getAll());
    }

    @GET
    @Path("{name}")
    public Response getProjectDetails(@PathParam("name") String name) {

        Project project = projectBean.findProject(name);

        if (project != null)
            return Response.status(Response.Status.OK).entity(toDTO(project)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR").build();
    }

    @POST
    @Path("/")
    public Response create(ProjectDTO projectDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {

        projectBean.create(
                projectDTO.getName(),
                projectDTO.getClient(),
                projectDTO.getDesigner()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {

        projectBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
