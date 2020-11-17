package ejbs;

import entities.FamilyProduct;
import entities.Manufacturer;
import entities.Product;
import entities.TypeProduct;
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
public class ProductBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String descriptionTypeProduct, String familyProductName, Long idManufacturer)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {

        Product product = findProduct(name);

        if (product != null)
            throw new MyEntityExistsException("");

        TypeProduct typeProduct = entityManager.find(TypeProduct.class, descriptionTypeProduct);

        if (typeProduct == null)
            throw new MyEntityNotFoundException("");

        FamilyProduct familyProduct = entityManager.find(FamilyProduct.class, familyProductName);

        if (familyProduct == null)
            throw new MyEntityNotFoundException("");

        Manufacturer manufacturer = entityManager.find(Manufacturer.class, idManufacturer);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        try {
            product = new Product(name, typeProduct, familyProduct, manufacturer);
            addProductToManufacturer(product, manufacturer);
            entityManager.persist(product);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    private void addProductToManufacturer(Product product, Manufacturer manufacturer) {
        if (!manufacturer.getProducts().contains(product))
            manufacturer.getProducts().add(product);
    }

    public List<Product> getAll() {
        return entityManager.createNamedQuery("getAllProducts").getResultList();
    }

    public Product findProduct(String name) {
        return entityManager.find(Product.class, name);
    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Product product = findProduct(name);

        if (product == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(product);
    }

    public void update(String name, String description, String familyName, Long idManufacturer)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Product product = findProduct(name);

        if (product == null)
            throw new MyEntityNotFoundException("");

        TypeProduct typeProduct = entityManager.find(TypeProduct.class, description);

        if (typeProduct == null)
            throw new MyEntityNotFoundException("");

        FamilyProduct familyProduct = entityManager.find(FamilyProduct.class, familyName);

        if (familyProduct == null)
            throw new MyEntityNotFoundException("");

        Manufacturer manufacturer = entityManager.find(Manufacturer.class, idManufacturer);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        try {
            entityManager.lock(product, LockModeType.OPTIMISTIC);
            product.setName(name);
            product.setTypeProduct(typeProduct);
            product.setFamilyProduct(familyProduct);
            product.setManufacturer(manufacturer);
            addProductToManufacturer(product, manufacturer);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }
}
