package ws;

import dtos.DesignerDTO;
import dtos.ProjectDTO;
import ebjs.DesignerBean;
import entities.Designer;
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

@Path("/designers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DesignerService {

    @EJB
    private DesignerBean designerBean;

    private DesignerDTO toDTONoProjects(Designer designer) {
        return new DesignerDTO(
                designer.getName(),
                designer.getEmaill()
        );
    }

    private DesignerDTO toDTO(Designer designer) {
        DesignerDTO designerDTO = new DesignerDTO(
                designer.getName(),
                designer.getEmaill()
        );

        List<ProjectDTO> projectDTOs = projectsToDTOs(designer.getProjects());
        designerDTO.setProjects(projectDTOs);

        return designerDTO;
    }

    private List<ProjectDTO> projectsToDTOs(List<Project> projects) {
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProjectDTO toDTO(Project project){
        return new ProjectDTO(
                project.getName(),
                project.getClient().getName(),
                project.getDesigner().getName()
        );
    }

    private List<DesignerDTO> toDTOsNoProjects(List<Designer> designers) {
        return designers.stream().map(this::toDTONoProjects).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<DesignerDTO> getAllDesignersWS() {
        return toDTOsNoProjects(designerBean.getAll());
    }

    @GET
    @Path("{name}")
    public Response getDesignerDetails(@PathParam("name") String name) {

        Designer designer = designerBean.findDesigner(name);

        if (designer != null)
            return Response.status(Response.Status.OK).entity(toDTO(designer)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR").build();
    }

    @POST
    @Path("/")
    public Response create(DesignerDTO designerDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyIllegalArgumentException {

        designerBean.create(
                designerDTO.getName(),
                designerDTO.getEmail()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(DesignerDTO designerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        designerBean.update(
                designerDTO.getName(),
                designerDTO.getEmail()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {

        designerBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}