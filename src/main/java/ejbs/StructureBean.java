package ejbs;

import entities.Client;
import entities.Product;
import entities.Project;
import entities.Structure;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class StructureBean {

    @PersistenceContext
    EntityManager entityManager;

    public void delete(String name)
            throws MyEntityNotFoundException {

        Structure structure = findStructure(name);

        if (structure == null)
            throw new MyEntityNotFoundException("Estrutura com o nome "+name+" não encontrada");

        entityManager.remove(structure);
    }

    public void create(String name, String projectName)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {

        Structure structure = findStructure(name);
        if (structure != null)
            throw new MyEntityExistsException("");

        Project project = entityManager.find(Project.class, projectName);
        if (project == null)
            throw new MyEntityNotFoundException("");

        List<Structure> projectStructures = project.getStructures();
        if (projectStructures.contains(structure)){
            throw new MyIllegalArgumentException("O projeto com o nome "+projectName+" já contém a estrutura com o nome"+name);
        }

        try {
            structure = new Structure(name, project);
            projectStructures.add(structure);
            entityManager.persist(structure);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Structure> getAll() {
        return entityManager.createNamedQuery("getAllStructures").getResultList();
    }

    public Structure findStructure(String name) {
        return entityManager.find(Structure.class, name);
    }

    public void  addProduct(String structureName, String productName) throws MyEntityExistsException, MyEntityNotFoundException, MyIllegalArgumentException, MyConstraintViolationException {

        Structure structure = findStructure(structureName);
        if (structure == null)
            throw new MyEntityExistsException("A estrutura com o nome "+structureName+" não existe");

        Product product = entityManager.find(Product.class, productName);
        if (product == null)
            throw new MyEntityNotFoundException("O produto com o nome "+productName+" não existe");

        List<Product> structureProducts = structure.getProducts();
        if (structureProducts.contains(product)){
            throw new MyIllegalArgumentException("A estrutura com o nome "+structureName+" já contém o produto com o nome "+productName);
        }

        try {
            structure.getProducts().add(product);
            product.getStructures().add(structure);
        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    /* public void productOnStru(String product, String stru) throws MyEntityNotFoundException{
        Structure structure = findStructure(stru);

        if (structure == null)
            throw new MyEntityNotFoundException("");

        Product product1 = entityManager.find(Product.class, product);

        structure.getProducts().add(product1);
        product1.getStructures().add(structure);
    }*/
}
