package dtos;

public class EmailDTO {

    private String project;
    private String subject;
    private String message;

    public EmailDTO() {
    }

    public EmailDTO(String project, String subject, String message) {
        this.project = project;
        this.subject = subject;
        this.message = message;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
