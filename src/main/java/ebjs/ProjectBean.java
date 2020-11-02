package ebjs;

import entities.Client;
import entities.Designer;
import entities.Project;
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

    public void create(String name, String nameClient, String nameDesigner)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException, MyIllegalArgumentException {

        Project project = findProject(name);
        if (project != null){
            throw  new MyEntityExistsException(String.format("Project with name %s already exists", name));
        }

        Client client = entityManager.find(Client.class, nameClient);
        if (client == null){
            throw new MyEntityNotFoundException(String.format("%s", nameClient));
        }

        Designer designer = entityManager.find(Designer.class, nameDesigner);
        if (designer == null){
            throw new MyEntityNotFoundException(String.format("%s", nameDesigner));
        }

        try {
            project = new Project(name, client, designer);
            client.getProjects().add(project);
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

        entityManager.remove(project);
    }

    public void update(String name, String clientName, String designerName)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Project project = entityManager.find(Project.class, name);

        if (project == null)
            throw new MyEntityNotFoundException("ERROR");

        Client client = entityManager.find(Client.class, clientName);

        if (client == null)
            throw new MyEntityNotFoundException("");

        Designer designer = entityManager.find(Designer.class, designerName);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        try {
            entityManager.lock(project, LockModeType.OPTIMISTIC);
            project.setName(name);
            project.setClient(client);
            project.setDesigner(designer);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }
}
