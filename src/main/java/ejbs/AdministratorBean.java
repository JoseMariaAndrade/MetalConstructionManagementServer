package ejbs;

import entities.Administrator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AdministratorBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String password, String email) {
        Administrator administrator = new Administrator(name, password, email);
        entityManager.persist(administrator);
    }

}
