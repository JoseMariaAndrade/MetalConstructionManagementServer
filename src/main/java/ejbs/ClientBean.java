package ejbs;

import entities.Client;
import entities.Project;
import entities.Structure;
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
public class ClientBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String name, String password, String email, String contact, String address)
            throws MyEntityExistsException, MyConstraintViolationException {

//        Client client = new Client();

//        if (client != null)
//            throw new MyEntityExistsException("");

        try {
            Client client = new Client(name, password, email, contact, address);
            entityManager.persist(client);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<Client> getAll() {
        return entityManager.createNamedQuery("getAllClients").getResultList();
    }

    public Client findClient(Long id) {
        return entityManager.find(Client.class, id);
    }

    public void update(Long id, String name, String password, String email, String contact, String address)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        Client client = findClient(id);

        if (client == null) {
            throw new MyEntityNotFoundException("");
        }

        try {
            entityManager.lock(client, LockModeType.OPTIMISTIC);
            client.setName(name);
            client.setPassword(password);
            client.setEmail(email);
            client.setContact(contact);
            client.setMorada(address);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public void delete(Long id)
            throws MyEntityNotFoundException {

        Client client = findClient(id);

        if (client == null)
            throw new MyEntityNotFoundException("");

        entityManager.remove(client);
    }

    public void clientDecision(Long id, String nameProject, String nameStructure, Boolean decision, String observation)
            throws MyEntityNotFoundException, MyIllegalArgumentException, MyConstraintViolationException {

        Client client = findClient(id);

        if (client == null) {
            throw new MyEntityNotFoundException();
        }

        Project project = entityManager.find(Project.class, nameProject);

        if (project == null)
            throw new MyEntityNotFoundException();

        Structure structure = entityManager.find(Structure.class, nameStructure);

        if (structure == null)
            throw new MyEntityNotFoundException();

        if (!client.getId().equals(project.getClient().getId()))
            throw new MyIllegalArgumentException();

        for (Structure structure1 : project.getStructures()
        ) {
            if (structure1.getName().equals(structure.getName())) {
                entityManager.lock(client, LockModeType.OPTIMISTIC);
                structure1.setDecision(decision);
                structure1.setObservation(observation);
            }
        }

//        for (Project project1 : client.getProjects()) {
//            if (project1.getName().equals(project.getName())) {
//
//            }
//        }

        try {
            entityManager.lock(project, LockModeType.OPTIMISTIC);
            structure.setDecision(decision);
            structure.setObservation(observation);

        } catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }
}
