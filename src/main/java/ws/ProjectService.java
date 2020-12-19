package ws;

import dtos.ProjectDTO;
import dtos.StructureDTO;
import ejbs.ProjectBean;
import ejbs.StructureBean;
import entities.Project;
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

@Path("/projects")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectService {

    @EJB
    private ProjectBean projectBean;

    @EJB
    private StructureBean structureBean;

    private ProjectDTO toDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO(
                project.getName(),
                project.getClient().getId(),
                project.getClient().getName(),
                project.getDesigner().getId(),
                project.getDesigner().getName()
        );

        return projectDTO;
    }

    private StructureDTO toDTO(Structure structure) {
        return new StructureDTO(
                structure.getName(),
                structure.getDecision(),
                structure.getObservation()
        );
    }

    private ProjectDTO toDTOStructures(Project project) {
        ProjectDTO projectDTO = new ProjectDTO(
                project.getName(),
                project.getClient().getId(),
                project.getClient().getName(),
                project.getDesigner().getId(),
                project.getDesigner().getName()
        );

        List<StructureDTO> structureDTOS = toDTOStructures(project.getStructures());
        projectDTO.setStructures(structureDTOS);

        return projectDTO;
    }

    private List<ProjectDTO> toDTOs(List<Project> projects) {
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<StructureDTO> toDTOStructures(List<Structure> structures) {
        return structures.stream().map(this::toDTO).collect(Collectors.toList());
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
            return Response.status(Response.Status.OK).entity(toDTOStructures(project)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR").build();
    }

    @POST
    @Path("/")
    public Response create(ProjectDTO projectDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {

        projectBean.create(
                projectDTO.getName(),
                projectDTO.getIdClient(),
                projectDTO.getIdDesigner()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(ProjectDTO projectDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        projectBean.update(
                projectDTO.getName(),
                projectDTO.getIdClient(),
                projectDTO.getIdDesigner()
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

    @DELETE
    @Path("{projectName}/structures/{structureName}")
    public Response delete(@PathParam("projectName") String projectName, @PathParam("structureName") String structureName)
            throws MyEntityNotFoundException, MyIllegalArgumentException {


        projectBean.removeStructure(projectName, structureName);
        structureBean.delete(structureName);

        // TODO: remover da BD uma estrutura que não esteja associada a um projeto?

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
