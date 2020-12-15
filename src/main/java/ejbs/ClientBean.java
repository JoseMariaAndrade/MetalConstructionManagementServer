package ejbs;

import entities.Client;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ClientBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String email, String contact, String address, String password)
            throws MyEntityExistsException, MyConstraintViolationException {

        Client client = findClient(name);

        if (client != null)
            throw new MyEntityExistsException("");

        try {
            client = new Client(name, email, contact, address, password);
            entityManager.persist(client);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<Client> getAll() {
        return entityManager.createNamedQuery("getAllClients").getResultList();

    }

    public Client findClient(String name) {
        return entityManager.find(Client.class, name);
    }

    public void update(String name, String email, String contact, String address)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Client client = findClient(name);

        if (client == null) {
            throw new MyEntityNotFoundException("");
        }

        try {
            entityManager.lock(client, LockModeType.OPTIMISTIC);
            client.setEmaill(email);
            client.setContact(contact);
            client.setMorada(address);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Client client = findClient(name);

        if (client == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(client);
    }
}
