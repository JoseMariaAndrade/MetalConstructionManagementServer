package ebjs;

import entities.Designer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DesignerBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String email) {

        Designer designer = new Designer(name, email);
        entityManager.persist(designer);
    }
}
