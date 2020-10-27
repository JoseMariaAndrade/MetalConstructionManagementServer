package ebjs;

import entities.Client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String email) {

        Client client = new Client(name, email);
        entityManager.persist(client);
    }
}
