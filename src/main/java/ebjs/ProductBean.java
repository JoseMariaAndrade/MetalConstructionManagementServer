package ebjs;

import entities.FamilyProduct;
import entities.Product;
import entities.TypeProduct;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String descriptionTypeProduct, String familyProductName)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        Product product = findProduct(name);

        if (product != null)
            throw new MyEntityExistsException("");

        TypeProduct typeProduct = entityManager.find(TypeProduct.class, descriptionTypeProduct);

        if (typeProduct == null)
            throw new MyEntityNotFoundException("");

        FamilyProduct familyProduct = entityManager.find(FamilyProduct.class, familyProductName);

        if (familyProduct == null)
            throw new MyEntityNotFoundException("");


        try {
            product = new Product(name, typeProduct, familyProduct);
            entityManager.persist(product);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<Product> getAll() {
        return entityManager.createNamedQuery("getAllProducts").getResultList();
    }

    private Product findProduct(String name) {
        return entityManager.find(Product.class, name);
    }
}
