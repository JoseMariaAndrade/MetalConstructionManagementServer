package ejbs;

import entities.*;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void addStructure(String projectName, String structureName) throws MyEntityNotFoundException {

        Project project = findProject(projectName);
        Structure structure = entityManager.find(Structure.class, structureName);

        if (structure == null) {
            throw new MyEntityNotFoundException("ERRO: Estrutura com o nome "+structureName+" não existe.");
        }

        if (project.getStructures().contains(structure)){
            throw new MyEntityNotFoundException("ERRO: O projeto "+projectName+" já possui uma estrutura com o nome "+structureName);
        }

        project.getStructures().add(structure);
    }

    public void removeStructure(String projectName, String structureName) throws MyEntityNotFoundException, MyIllegalArgumentException {

        Project project = findProject(projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("Projeto com o nome "+projectName+" não existe");
        }

        Structure structure = entityManager.find(Structure.class, structureName);

        if (structure == null) {
            throw new MyEntityNotFoundException("Estrutura com o nome "+structureName+" não existe");
        }

        List<String> structureNames = new ArrayList<>();

        for (Structure struct: project.getStructures()) {
            structureNames.add(struct.getName());
        }

        if (!structureNames.contains(structure.getName())) {
            throw new MyIllegalArgumentException("Projeto com o nome "+projectName+" não contém a estrutura com o nome "+structureName+" - impossível remover");
        }

        project.getStructures().remove(structure); // TODO: quando se remove uma estrutura de um projeto ela continua na lista de estruturas da aplicação? Solução: dar a opção de remover manualmente estruturas na página das estruturas



    }

    public void removeDocumentFromProject(String projectName, String documentName) throws MyEntityNotFoundException {

        Project project = findProject(projectName);

        Document document = entityManager.find(Document.class, documentName);

        if (document == null) {
            throw new MyEntityNotFoundException("Documento com o nome "+documentName+" não existe");
        }

        List<Document> projectDocuments = project.getDocuments();

        if (projectDocuments.contains(document)) {
            projectDocuments.remove(document);
            entityManager.remove(document);
        }
    }

    public void addDocumentToProject(String projectName, List<String> documentsNames) throws MyEntityNotFoundException, MyIllegalArgumentException {

        // TODO: investigar como adicionar vários documentos de uma vez a um projeto

        Project project = findProject(projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("Projeto com o nome "+projectName+" não existe");
        }

        for (String documentName: documentsNames) {
            Document document = entityManager.find(Document.class, documentName);

            if (document == null) {
                throw new MyEntityNotFoundException("Documento com o nome "+documentName+" não existe");
            }

            List<Document> projectDocuments = project.getDocuments();

            if (projectDocuments.contains(document)) {
                throw new MyIllegalArgumentException("O projeto "+projectName+" já contém o documento "+documentName+" na sua lista de documentos");
            }

            projectDocuments.add(document);
        }
    }

    public void approveProject(String projectName, String observations) throws MyEntityNotFoundException {
        Project project = findProject(projectName);

        entityManager.lock(project, LockModeType.OPTIMISTIC);

        if (observations != null) {
                project.setObservations(observations);
        }

        project.setContracted(true);

        // TODO: enviar email de notificação de rejeição de projeto

    }

    public void rejectProject(String projectName, String observations) throws MyEntityNotFoundException {
        Project project = findProject(projectName);

        if (observations != null) {
            project.setObservations(observations);
        }

        project.setContracted(false);

        // TODO: enviar email de notificação de rejeição de projeto

    }

    public void create(String name, String client, String designer)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException, MyIllegalArgumentException {

        Project project = findProject(name);
        if (project != null){
            throw  new MyEntityExistsException(String.format("Project with name %s already exists", name));
        }

        Client client1 = entityManager.find(Client.class, client);
        if (client == null){
            throw new MyEntityNotFoundException(String.format("%s", client));
        }

        Designer designer1 = entityManager.find(Designer.class, designer);
        if (designer == null){
            throw new MyEntityNotFoundException(String.format("%s", designer));
        }

        try {
            project = new Project(name, client1, designer1);
            client1.getProjects().add(project);
            designer1.getProjects().add(project);
            entityManager.persist(project);
        } catch (ConstraintViolationException constraintViolationException){
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Project> getAll(){
        return entityManager.createNamedQuery("getAllProjects").getResultList();
    }

    public Project findProject(String name) throws MyEntityNotFoundException {

        Project project = entityManager.find(Project.class, name);

        return project;
    }

    public void delete(String name)
            throws MyEntityNotFoundException {

        Project project = findProject(name);

        if (project == null) {
            throw new MyEntityNotFoundException("ERROR");
        }

        entityManager.remove(project);
    }

    public void update(String projectName, String designerName)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        if (projectName != null) {
            Project project = entityManager.find(Project.class, projectName);

            if (project == null) {
                throw new MyEntityNotFoundException("ERRO: Não existe um projecto com o nome " + projectName);
            }

            if (designerName != null) {
                Designer designer = entityManager.find(Designer.class, designerName);
                if (designer == null) {
                    throw new MyEntityNotFoundException("ERRO: não existe um designer com o nome "+designerName);
                }

                try {
                    entityManager.lock(project, LockModeType.OPTIMISTIC);
                    project.setName(projectName);
                    project.setDesigner(designer);
                    designer.getProjects().add(project);
                } catch (ConstraintViolationException e){
                    throw new MyConstraintViolationException(e);
                }
            }
        }
    }

    public Structure getProjectStructure(String projectName, String structureName) throws MyEntityNotFoundException, MyIllegalArgumentException {

        Project project = findProject(projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("Projeto com o nome " + projectName + " não existe");
        }

        Structure structure = entityManager.find(Structure.class, structureName);

        if (structure == null) {
            throw new MyEntityNotFoundException("Estrutura com o nome " + structureName + " não existe");
        }

        List<String> structureNames = new ArrayList<>();

        for (Structure struct : project.getStructures()) {
            structureNames.add(struct.getName());
        }

        if (!structureNames.contains(structure.getName())) {
            throw new MyIllegalArgumentException("Projeto com o nome " + projectName + " não contém a estrutura com o nome " + structureName + " - impossível remover");
        }

        return structure;
    }

}
