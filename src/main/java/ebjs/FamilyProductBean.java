package ebjs;

import entities.FamilyProduct;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class FamilyProductBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name)
            throws MyEntityExistsException, MyConstraintViolationException {

        FamilyProduct familyProduct = findFamilyProduct(name);

        if (familyProduct != null)
            throw new MyEntityExistsException("");

        try {

            familyProduct = new FamilyProduct(name);
            entityManager.persist(familyProduct);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<FamilyProduct> getAll() {
        return entityManager.createNamedQuery("getAllFamiliesProduct").getResultList();
    }

    public FamilyProduct findFamilyProduct(String name) {
        return entityManager.find(FamilyProduct.class, name);
    }

    /*public void addProduct(String familyName, String productName)
            throws MyEntityNotFoundException {

        FamilyProduct familyProduct = findFamilyProduct(familyName);

        if (familyName == null) {
            throw new MyEntityNotFoundException("");
        }

        Product product = entityManager.find(Product.class, productName);

        if (product == null) {
            throw new MyEntityNotFoundException("");
        }

        familyProduct.addProduct(product);
        product.setFamilyProduct(familyProduct);
    }*/
}
