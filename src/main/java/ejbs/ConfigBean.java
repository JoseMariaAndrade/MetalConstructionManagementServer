package ejbs;

import entities.Project;

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
    @EJB
    FamilyProductBean familyProductBean;
    @EJB
    TypeProductBean typeProductBean;
    @EJB
    ProductBean productBean;
    @EJB
    StructureBean structureBean;
    @EJB
    ManufacturerBean manufacturerBean;

    @PostConstruct
    void populateDB() {

        try {
            clientBean.create("Jose", "jose@mail.pt", "123456", "asdasfgafdg", "123");
            designerBean.create("Joao", "joao@mail.pt", "123");
            designerBean.create("Pedro", "pedro@mail.pt", "123");
            manufacturerBean.create("XXX", "zxcx@asd.com", "123");
            projectBean.create("JoseProjeto", "Jose", "Joao");
            structureBean.create("ASDasd", "JoseProjeto");
            typeProductBean.create("Tipo");
            familyProductBean.create("Familia");
            productBean.create("Product", "Tipo", "Familia", "XXX");
            structureBean.addProduct("ASDasd", "Product");



        } catch (Exception exception){
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }
}
