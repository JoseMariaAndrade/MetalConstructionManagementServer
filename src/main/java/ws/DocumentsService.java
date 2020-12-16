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
    public Response uploadFile(@PathParam("projectName") String projectName, MultipartFormDataInput multipartFormDataInput)
            throws MyEntityNotFoundException, IOException {

        Project project = projectBean.findProject(projectName);

        if (project == null) {
            throw new MyEntityNotFoundException("Project with name " + projectName + " not found.");
        }

        Map<String, List<InputPart>> uploadForm = multipartFormDataInput.getFormDataMap();

        //Get file data to save
        List<InputPart> inputParts = uploadForm.get("attachment");

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                // Convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                String path = System.getProperty("user.home") + File.separator + "uploads";

                File customDir = new File(path);

                if (!customDir.exists())
                    customDir.mkdir();

                String filePath = customDir.getCanonicalPath() + File.separator + fileName;

                writeFile(bytes, filePath);

                documentBean.create(path, fileName, projectName);

                return Response.status(Response.Status.OK).entity(String.format("Upload file name: %s", fileName)).build();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    @GET
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("id") Integer id)
            throws MyEntityNotFoundException {

        Document document = documentBean.findDocument(id);

        File fileDownload = new File(document.getFilepath() + File.separator + document.getFilename());

        Response.ResponseBuilder responseBuilder = Response.ok((Object) fileDownload);
        responseBuilder.header("Content-Disposition", "attachment;filename=" + document.getFilename());

        return responseBuilder.build();
    }

    @GET
    @Path("{projectName}/documents/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DocumentDTO> getDocuments(@PathParam("projectName") String projectName)
            throws MyEntityNotFoundException {
        Project project = projectBean.findProject(projectName);
        if (project == null) {
            throw new MyEntityNotFoundException("Student with username " + projectName + " not found.");
        }
        return documentsToDTOs(documentBean.getProjectDocuments(projectName));
    }

    @GET
    @Path("{projectName}/hasDocuments/")
    public Response hasDocuments(@PathParam("projectName") String projectName)
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
                document.getId(),
                document.getFilename(),
                document.getFilepath()
        );
    }

    List<DocumentDTO> documentsToDTOs(List<Document> documents) {
        return documents.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String fileName : contentDisposition) {
            if ((fileName.trim()).startsWith("filename")) {

                String[] name = fileName.split("=");

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
//        System.out.println("Written: " + filename);
    }
}