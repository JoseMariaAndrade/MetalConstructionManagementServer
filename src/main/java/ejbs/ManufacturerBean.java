package ejbs;

import entities.Manufacturer;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ManufacturerBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String email, String password)
            throws MyEntityExistsException, MyConstraintViolationException {

        Manufacturer manufacturer = findManufacturer(name);

        if (manufacturer != null) {
            throw new MyEntityExistsException("");
        }

        try {
            manufacturer = new Manufacturer(name, email, password);
            entityManager.persist(manufacturer);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Manufacturer> getAll() {
        return entityManager.createNamedQuery("getAllManufacturers").getResultList();
    }

    public Manufacturer findManufacturer(String name) {
        return entityManager.find(Manufacturer.class, name);
    }

    public void update(String name, String email)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Manufacturer manufacturer = findManufacturer(name);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        try {
            entityManager.lock(manufacturer, LockModeType.OPTIMISTIC);
            manufacturer.setName(name);
            manufacturer.setEmaill(email);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Manufacturer manufacturer = findManufacturer(name);

        if (manufacturer == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(manufacturer);
    }
}
