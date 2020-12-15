package ws;

import dtos.DocumentDTO;
import ejbs.DocumentBean;
import ejbs.ProjectBean;
import entities.Document;
import entities.Project;
import exceptions.MyEntityNotFoundException;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/file")
public class DocumentsService {

    @EJB
    private DocumentBean documentBean;

    @EJB
    private ProjectBean projectBean;

    @POST
    @Path("{projectName}/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @PathParam("projectName") String projectName,
            MultipartFormDataInput input) throws MyEntityNotFoundException,
            IOException {
        Project project = projectBean.findProject(projectName);
        if (project == null) {
            throw new MyEntityNotFoundException("Project with name "+projectName+" not found.");
        }
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
// Get file data to save
        List<InputPart> inputParts = uploadForm.get("attachment");
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);
// convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,
                        null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                String path = System.getProperty("user.home") + File.separator +
                        "uploads";
                File customDir = new File(path);
                if (!customDir.exists()) {
                    customDir.mkdir();
                }
                fileName = customDir.getCanonicalPath() + File.separator +
                        fileName;
                writeFile(bytes, fileName);
                documentBean.create(path, fileName, projectName);
                return Response.status(200).entity("Uploaded file name : " +
                        fileName).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @POST
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes("application/x-www-form-urlencoded")
    public Response downloadFileWithPost(@FormParam("file") String file) {
        String path = System.getProperty("user.home") + File.separator +
                "uploads";
        File fileDownload = new File(path + File.separator + file);
        Response.ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + file);
        return response.build();
    }

    @GET
    @Path("{projectName}/documents/")
    public List<DocumentDTO> getDocuments(@PathParam("projectName") String projectName)
            throws MyEntityNotFoundException {
        Project project = projectBean.findProject(projectName);
        if (projectName == null) {
            throw new MyEntityNotFoundException("Student with username "+projectName+" not found.");
        }
        return documentsToDTOs(documentBean.getProjectDocuments(projectName));
    }

    @GET
    @Path("{username}/hasDocuments/")
    public Response hasDocuments(@PathParam("username") String projectName)
            throws MyEntityNotFoundException {
        Project project = projectBean.findProject(projectName);
        if (project == null) {
            throw new MyEntityNotFoundException("Student with username " +
                    projectName + " not found.");
        }
        return Response.status(Response.Status.OK)
                .entity(!project.getDocuments().isEmpty())
                .build();
    }

    DocumentDTO toDTO(Document document) {
        return new DocumentDTO(
                document.getId(),document.getFilepath(),
                document.getFilename());
    }
    List<DocumentDTO> documentsToDTOs(List<Document> documents) {
        return documents.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("ContentDisposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
        System.out.println("Written: " + filename);
    }
}