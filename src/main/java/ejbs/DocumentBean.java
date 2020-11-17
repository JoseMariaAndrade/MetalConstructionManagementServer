package ejbs;

import entities.Document;
import entities.Project;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class DocumentBean {

    @PersistenceContext
    EntityManager em;

    public void create(String username, String filepath, String filename, String projectName) throws MyEntityNotFoundException, MyConstraintViolationException {

        Project project = em.find(Project.class, projectName);

        if (project == null)

        try {
            Document document = new Document(filepath, filename, project);
            em.persist(document);
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }

    }

    public Document findDocument(int id) throws MyEntityNotFoundException {

        Document document = em.find(Document.class, id);

        if (document == null) {
            throw new MyEntityNotFoundException("ERRO: Não existe um documento com o username "+id+"na base de dados");
        }

        return document;
    }

    public List<Document> getProjectDocuments(String projectName) throws MyEntityNotFoundException {
        Project project = em.find(Project.class, projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("ERRO: Não existe um estudante com o username "+projectName+"na base de dados");
        }

        return project.getDocuments();
    }
}
