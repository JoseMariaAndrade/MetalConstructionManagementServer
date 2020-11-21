package ejbs;

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

    public void create(String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException {

//        Designer designer = findDesigner(name);
//
//        if (designer != null)
//            throw new MyEntityExistsException("");

        try {

            Designer designer = new Designer(name, password, email);
            entityManager.persist(designer);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Designer> getAll() {
        return entityManager.createNamedQuery("getAllDesigners").getResultList();
    }

    public Designer findDesigner(Long id) {
        return entityManager.find(Designer.class, id);
    }

    public void update(Long id, String name, String password, String email)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Designer designer = findDesigner(id);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        try {

            entityManager.lock(designer, LockModeType.OPTIMISTIC);
            designer.setName(name);
            designer.setPassword(password);
            designer.setEmail(email);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void delete(Long id)
            throws MyEntityNotFoundException {

        Designer designer = findDesigner(id);

        if (designer == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(designer);
    }
}
