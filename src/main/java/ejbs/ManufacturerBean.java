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
}
