package ejbs;

import entities.Manufacturer;
import entities.Product;
import entities.Structure;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ManufacturerBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException {

//        Manufacturer manufacturer = findManufacturer(name);
//
//        if (manufacturer != null) {
//            throw new MyEntityExistsException("");
//        }

        try {
            Manufacturer manufacturer = new Manufacturer(name, password, email);
            entityManager.persist(manufacturer);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Manufacturer> getAll() {
        return entityManager.createNamedQuery("getAllManufacturers").getResultList();
    }

    public List<Structure> getAllProjectsApprovedNotDone() {
        return entityManager.createNamedQuery("getAllStructuresOfProjectApprovedAndNotDone").getResultList();
    }

    public Manufacturer findManufacturer(Long id) {
        return entityManager.find(Manufacturer.class, id);
    }

    public void update(Long id, String name, String password, String email)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Manufacturer manufacturer = findManufacturer(id);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        try {
            entityManager.lock(manufacturer, LockModeType.OPTIMISTIC);
            manufacturer.setName(name);
            manufacturer.setPassword(password);
            manufacturer.setEmail(email);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void delete(Long id)
            throws MyEntityNotFoundException {

        Manufacturer manufacturer = findManufacturer(id);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(manufacturer);
    }

    public void deleteProduct(Long id, String name)
            throws MyEntityNotFoundException {

        Manufacturer manufacturer = findManufacturer(id);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        entityManager.lock(manufacturer, LockModeType.OPTIMISTIC);
        manufacturer.getProducts().removeIf(p -> p.getName().equals(name));
    }

    public void verifyStockProducts(Long idManufacturer, String productName) throws MyEntityNotFoundException {

        Manufacturer manufacturer = findManufacturer(idManufacturer);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("Manufacturer not found");

        Product product = entityManager.find(Product.class, productName);

        if (product == null)
            throw new MyEntityNotFoundException("Manufacturer not found");

        List<Structure> structures = getAllProjectsApprovedNotDone();
        List<Product> products = new ArrayList<>();
        for (Structure s : structures) {
            if (s.getProducts().contains(product)) {
                for (Product p2 : s.getProducts()) {
                    if (p2.getName().equals(product.getName()))
                        products.add(p2);
                }
            }
        }

        for (Product p : manufacturer.getProducts()) {
            for (Product p2 : products) {
                if (p.getName().equals(p2.getName())) {
                    entityManager.lock(p, LockModeType.OPTIMISTIC);
                    p.setNeedStock(true);
                } else {
                    entityManager.lock(p, LockModeType.OPTIMISTIC);
                    p.setNeedStock(false);
                }
            }
        }
    }
}
