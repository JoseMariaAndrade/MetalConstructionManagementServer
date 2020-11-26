package ejbs;

import entities.Project;
import entities.Structure;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class StructureBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String projectName)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Structure structure = findStructure(name);

        if (structure != null)
            throw new MyEntityExistsException("");

        Project project = entityManager.find(Project.class, projectName);

        if (project == null)
            throw new MyEntityNotFoundException("");

        try {
            structure = new Structure(name, project);
            project.structures.add(structure);
            entityManager.persist(structure);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Structure> getAll() {
        return entityManager.createNamedQuery("getAllStructures").getResultList();
    }

    private Structure findStructure(String name) {
        return entityManager.find(Structure.class, name);
    }

    /*public void productOnStru(String product, String stru) throws MyEntityNotFoundException{
        Structure structure = findStructure(stru);

        if (structure == null)
            throw new MyEntityNotFoundException("");

        Product product1 = entityManager.find(Product.class, product);

        structure.getProducts().add(product1);
        product1.getStructures().add(structure);
    }*/
}
