package ebjs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton(name = "ConfigBean")
@Startup
public class ConfigBean {

    private static final Logger LOGGER = Logger.getLogger("ebjs.ConfigBean");

    @EJB
    ProjectBean projectBean;
    @EJB
    ClientBean clientBean;
    @EJB
    DesignerBean designerBean;

    @PostConstruct
    void populateDB(){
        designerBean.create("Joao", "joao@mail.pt");
        try {
            clientBean.create("Jose","jose@mail.pt", "123456", "asdasfgafdg");

            projectBean.create("JoseProjeto", "Jose", "Joao");
        } catch (Exception exception){
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }
}
