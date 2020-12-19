package ejbs;

import entities.Client;
import entities.Designer;
import entities.Project;
import entities.Structure;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, Long idClient, Long idDesigner)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {

        Project project = findProject(name);
        if (project != null) {
            throw new MyEntityExistsException(String.format("Project with name %s already exists", name));
        }

        Client client = entityManager.find(Client.class, idClient);
        if (client == null) {
            throw new MyEntityNotFoundException(String.format("%s", "asd"));
        }

        Designer designer = entityManager.find(Designer.class, idDesigner);
        if (designer == null){
            throw new MyEntityNotFoundException(String.format("%s", "nameDesigner"));
        }

        try {
            project = new Project(name, client, designer);
            client.addProject(project);
            designer.getProjects().add(project);
            entityManager.persist(project);
        } catch (ConstraintViolationException constraintViolationException){
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<Project> getAll(){
        return entityManager.createNamedQuery("getAllProjects").getResultList();
    }

    public Project findProject(String name) {
        return entityManager.find(Project.class, name);
    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Project project = findProject(name);

        if (project == null) {
            throw new MyEntityNotFoundException("ERROR");
        }
        project.getClient().getProjects().remove(project);
        project.getDesigner().getProjects().remove(project);
        entityManager.remove(project);
    }

    public void update(String name, Long idClient, Long idDesigner)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Project project = entityManager.find(Project.class, name);

        if (project == null)
            throw new MyEntityNotFoundException("ERROR");

        Client client = entityManager.find(Client.class, idClient);

        if (client == null)
            throw new MyEntityNotFoundException("");

        Designer designer = entityManager.find(Designer.class, idDesigner);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        try {
//            entityManager.lock(project, LockModeType.OPTIMISTIC);
            project.setName(name);
            project.setClient(client);
            project.setDesigner(designer);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void availableToClient(String name) throws MyEntityNotFoundException, MyConstraintViolationException {
        Project project = findProject(name);
        if (project == null)
            throw new MyEntityNotFoundException("");

        try {
            entityManager.lock(project, LockModeType.OPTIMISTIC);
            project.setAvailableToClient(true);
            for (Project proj : project.getClient().getProjects()
            ) {
                if (proj.getName().equals(project.getName())) {
                    entityManager.lock(proj, LockModeType.OPTIMISTIC);
                    proj.setAvailableToClient(true);
                }
            }
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void removeStructure(String projectName, String structureName)
            throws MyEntityNotFoundException, MyIllegalArgumentException {

        Project project = findProject(projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("Projeto com o nome " + projectName + " não existe");
        }

        Structure structure = entityManager.find(Structure.class, structureName);

        if (structure == null) {
            throw new MyEntityNotFoundException("Estrutura com o nome " + structureName + " não existe");
        }

        if (project.getStructures().contains(structure)) {
            project.getStructures().remove(structure);
        } else {
            throw new MyIllegalArgumentException("");
        }

//        List<String> structureNames = new ArrayList<>();

//        for (Structure struct: project.getStructures()) {
//            structureNames.add(struct.getName());
//        }
//
//        if (!structureNames.contains(structure.getName())) {
//            throw new MyIllegalArgumentException("Projeto com o nome "+projectName+" não contém a estrutura com o nome "+structureName+" - impossível remover");
//        }
//
//        project.getStructures().remove(structure); // TODO: quando se remove uma estrutura de um projeto ela continua na lista de estruturas da aplicação? Solução: dar a opção de remover manualmente estruturas na página das estruturas
    }
}
