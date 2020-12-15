package ws;

import dtos.ProductDTO;
import dtos.ProjectDTO;
import dtos.StructureDTO;
import ejbs.DesignerBean;
import ejbs.ProjectBean;
import ejbs.StructureBean;
import entities.Designer;
import entities.Product;
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
import java.util.ArrayList;
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

    @EJB
    private DesignerBean designerBean;

    @GET
    @Path("{projectName}/structures/{structureName}")
    public Response getProjectStructure(@PathParam("projectName") String projectName, @PathParam("structureName") String structureName) throws MyEntityNotFoundException, MyIllegalArgumentException {

    Structure structure = projectBean.getProjectStructure(projectName, structureName);

    return Response.status(Response.Status.OK).entity(toDTO(structure)).build();
    }

    @PUT
    @Path("{projectName}/designers/{designerName}")
    public Response editDesigner(@PathParam("projectName") String projectName, @PathParam("designerName") String designerName) throws MyEntityNotFoundException, MyIllegalArgumentException, MyConstraintViolationException {

        Project project = projectBean.findProject(projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("Projeto com o nome "+projectName+" não existe");
        }

        Designer designer = designerBean.findDesigner(designerName);

        if (designer == null) {
            throw new MyEntityNotFoundException("Designer com o nome "+designerName+" não existe");
        }

        if (designer.getProjects().contains(project)) {
            throw new MyIllegalArgumentException("Designer com o nome" + designerName + " já tem o projeto" + projectName);
        }

        projectBean.update(projectName, designerName);

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

    private List<StructureDTO> toStructureDTOs(List<Structure> structures) {
        return structures.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private void addStructure (String projectName, String structureName) throws MyEntityNotFoundException {
        Structure structure = structureBean.findStructure(structureName);
        Project project = projectBean.findProject(projectName);
        projectBean.addStructure(projectName, structureName);
    }

    private ProjectDTO toDTO(Project project){

        ProjectDTO projectDTO = new ProjectDTO(
                project.getName(),
                project.getClient().getName(),
                project.getDesigner().getName(),
                toStructureDTOs(project.getStructures()),
                project.isApproved()
        );

        return projectDTO;
    }

    private StructureDTO toDTO(Structure structure){

        StructureDTO structureDTO = new StructureDTO(
                structure.getName(),
                structure.getProject().getName(),
                toProductDTOs(structure.getProducts())
        );

        return structureDTO;
    }

    private StructureDTO toDTOWithoutProducts(Structure structure){

        StructureDTO structureDTO = new StructureDTO(
                structure.getName(),
                structure.getProject().getName()
        );

        return structureDTO;
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

    private List<ProjectDTO> toDTOs(List<Project> projects){
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ProductDTO> toProductDTOs(List<Product> products){
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProjectDTO> getAllProjectsWS() {
        return toDTOs(projectBean.getAll());
    }

    @GET
    @Path("{name}")
    public Response getProjectDetails(@PathParam("name") String name) throws MyEntityNotFoundException {

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

    @PUT
    @Path("/")
    public Response update(ProjectDTO projectDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        projectBean.update(
                projectDTO.getName(),
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
