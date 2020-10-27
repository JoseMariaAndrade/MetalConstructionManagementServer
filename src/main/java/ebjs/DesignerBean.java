package ebjs;

import entities.Designer;
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
public class DesignerBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String email)
            throws MyEntityExistsException, MyConstraintViolationException {

        Designer designer = findDesigner(name);

        if (designer != null)
            throw new MyEntityExistsException("");

        try {

            designer = new Designer(name, email);
            entityManager.persist(designer);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Designer> getAll() {
        return entityManager.createNamedQuery("getAllDesigners").getResultList();
    }

    public Designer findDesigner(String name) {
        return entityManager.find(Designer.class, name);
    }

    public void update(String name, String email)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Designer designer = findDesigner(name);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        try {

            entityManager.lock(designer, LockModeType.OPTIMISTIC);
            designer.setName(name);
            designer.setEmaill(email);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Designer designer = findDesigner(name);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(designer);
    }
}
