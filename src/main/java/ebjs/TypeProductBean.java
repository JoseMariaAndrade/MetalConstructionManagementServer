package ebjs;

import entities.TypeProduct;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class TypeProductBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String description)
            throws MyEntityExistsException, MyConstraintViolationException {

        TypeProduct typeProduct = findTypeProduct(description);

        if (typeProduct != null)
            throw new MyEntityExistsException("");

        try {
            typeProduct = new TypeProduct(description);
            entityManager.persist(typeProduct);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<TypeProduct> getAll() {
        return entityManager.createNamedQuery("getAllTypesProduct").getResultList();
    }

    public TypeProduct findTypeProduct(String description) {
        return entityManager.find(TypeProduct.class, description);
    }
}
