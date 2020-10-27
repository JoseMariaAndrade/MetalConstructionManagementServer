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
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

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
            throw new MyEntityNotFoundException(String.format("",nameClient));
        }

        Designer designer = entityManager.find(Designer.class, nameDesigner);
        if (designer == null){
            throw new MyEntityNotFoundException(String.format("", nameDesigner));
        }

        try {
            project = new Project(name, client, designer);
            entityManager.persist(project);
        } catch (ConstraintViolationException constraintViolationException){
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    private Project findProject(String name) {
        return entityManager.find(Project.class, name);
    }
}
