package ejbs;

import entities.Variante;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


@Singleton(name = "ConfigBean")
@Startup
public class ConfigBean {

    private static final Logger LOGGER = Logger.getLogger("ejbs.ConfigBean");

    @EJB
    AdministratorBean administratorBean;
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
    @EJB
    VarianteBean varianteBean;
    @EJB
    SimulacaoBean simulacaoBean;

    @PostConstruct
    void populateDB() {

        try {
            clientBean.create("Jose", "123", "jose@mail.pt", "123456", "asdasfgafdg");
            designerBean.create("Joao", "123", "joao@mail.pt");
            manufacturerBean.create("XXX", "123", "zxcx@asd.com");
            administratorBean.create("JoseMaria", "123123", "j@mail.com");
            clientBean.create("Paulo", "123", "joses@mail.pt", "123456", "asdasfgafdg");
            projectBean.create("JoseProjeto", 1L, 2L);
            projectBean.create("PauloProjeto", 5L, 2L);
            structureBean.create("ASDasd", "JoseProjeto");
            structureBean.create("ASDasds", "PauloProjeto");
            typeProductBean.create("Perfis Enformados a Frio");
            typeProductBean.create("Chapa Perfilada");
            typeProductBean.create("Laje Mista");
            typeProductBean.create("Painel Sandwich");
            familyProductBean.create("Superomega", "Perfis Enformados a Frio");
            familyProductBean.create("Madre C", "Perfis Enformados a Frio");
            familyProductBean.create("Madre Z", "Perfis Enformados a Frio");
            familyProductBean.create("P0-272-30", "Chapa Perfilada");
            productBean.create("Product", "Superomega", 3L);
            structureBean.productOnStru("Product", "ASDasd");
            System.out.println("####### A criar produtos...");

            /*

            String filename = "Section C S220 BF.xlsx";
            String filenameWithoutExt = FilenameUtils.removeExtension(filename);

            FileInputStream file = new FileInputStream(new File("Section C S220 BF.xlsx"));

            Workbook workbook = new XSSFWorkbook(file);
            XSSFSheet ResistanceSheet = (XSSFSheet) workbook.getSheet("Resistance");
            XSSFSheet SectionSheet = (XSSFSheet) workbook.getSheet("Section");
            XSSFSheet SteelSheet = (XSSFSheet) workbook.getSheet("Steel");
            XSSFSheet mcr_p_mcr_n_Sheet = (XSSFSheet) workbook.getSheet("Mcr_p e Mcr_n");

            System.out.println("TESTE");

            Double weff_p = 0.0;
            Double weff_n = 0.0;
            Double ar = 0.0;
            Double sigmaC = 0.0;
            Double mcr_p = 0.0; // idênticos para secções simétricas
            Double mcr_n = 0.0; // idênticos para secções simétricas

            for (int i = 5; i < Integer.MAX_VALUE; i++) {
                weff_p = Double.parseDouble(ResistanceSheet.getRow(i).getCell(i).getRawValue());

                // double weff_p: célula F6 em diante da folha Resistance
                // double weff_n: célula H6 em diante da folha Resistance
                // double ar: célula F6 em diante da folha Section
                // double sigmaC: célula B11 da folha Steel
                // mcr_p mcr_n: células C2 em diante da folha mcr_p&mcr_n

                if (weff_p != null) { // existe uma nova variante do produto

                    // weff_p já foi definida em cima

                    weff_n = Double.parseDouble(SteelSheet.getRow(i).getCell(i).getRawValue());

                    ar = Double.parseDouble(SectionSheet.getRow(i).getCell(i).getRawValue());

                    sigmaC = Double.parseDouble(SteelSheet.getRow(10).getCell(1).getRawValue());

                    if (mcr_p_mcr_n_Sheet != null) {

                        *//*public static Cell findCell(XSSFSheet sheet, String text) {
                            for(Row row : sheet) {
                                for(Cell cell : row) {
                                    if(text.equals(cell.getStringCellValue()))
                                        return cell;
                                }
                            }
                            return null;*//*
                        }
                    }
                }
            */

            productBean.create("Section C 220 BF", "Madre C", 3L);
            productBean.create("Section Z 220 BF", "Madre Z", 3L);
            System.out.println("####### A criar variantes...");

            // PODE LER-SE OS VALORES DOS PRODUTOS/VARIANTES DE EXCELS OU CSVs (ver excels fornecidos)
            // Exemplo básico de adição de variantes "à mão"
            varianteBean.create(1, "Section C 220 BF", "C 120/50/21 x 1.5", 13846, 13846, 375, 220000);
            varianteBean.create(2, "Section C 220 BF", "C 120/60/13 x 2.0", 18738, 18738, 500, 220000);

            //PODE LER-SE OS VALORES mcr_p E mcr_n A PARTIR DE UM EXCEL OU CSV (ver excels fornecidos para os produtos Perfil C e Z, que têm os valores mcr)
            //Exemplo básico de adição de valores mcr "à mão"
            Variante variante1 = varianteBean.findVariante(1);
            variante1.addMcr_p(3.0, 243.2123113);
            variante1.addMcr_p(4.0, 145.238784);
            variante1.addMcr_p(5.0, 99.15039028);
            variante1.addMcr_p(6.0, 73.71351699);
            variante1.addMcr_p(7.0, 58.07716688);
            variante1.addMcr_p(8.0, 47.68885195);
            variante1.addMcr_p(9.0, 40.37070843);
            variante1.addMcr_p(10.0, 34.9747033);
            variante1.addMcr_p(11.0, 30.84866055);
            variante1.addMcr_p(12.0, 27.59984422);

            //Válido para variantes simétricas, em que os mcr_p são iguais aos mcr_n
            variante1.setMcr_n((LinkedHashMap<Double, Double>) variante1.getMcr_p().clone());

            Variante variante2 = varianteBean.findVariante(2);
            variante2.addMcr_p(3.0, 393.1408237);
            variante2.addMcr_p(4.0, 241.9157907);
            variante2.addMcr_p(5.0, 169.7815504);
            variante2.addMcr_p(6.0, 129.3561949);
            variante2.addMcr_p(7.0, 104.0782202);
            variante2.addMcr_p(8.0, 86.9803928);
            variante2.addMcr_p(9.0, 74.71876195);
            variante2.addMcr_p(10.0, 65.52224563);
            variante2.addMcr_p(11.0, 58.37786338);
            variante2.addMcr_p(12.0, 52.65428332);

            //Válido para variantes de geometria simétrica, em que os mcr_p são iguais aos mcr_n
            variante2.setMcr_n((LinkedHashMap<Double, Double>) variante2.getMcr_p().clone());

            System.out.println("####### FINISHED!!");

            //EXEMPLO DA SIMULAÇÃO PARA DUAS VARIANTES DO PERFIL C, E PARA UMA ESTRUTURA DE 3 vãos (nb) de 3m cada (LVao) E SOBRECARGA 500000 (q)
            if (simulacaoBean.simulaVariante(3, 3.0, 500000, variante1) == "seguro") {
                System.out.println(variante1.getNome() + " pode ser usada.");
            } else {
                System.out.println(variante1.getNome() + " não pode ser usada.");
            }

            if (simulacaoBean.simulaVariante(3, 3.0, 500000, variante2) == "seguro") {
                System.out.println("A variante " + variante2.getNome() + " pode ser usada.");
            } else {
                System.out.println("A variante " + variante2.getNome() + " não pode ser usada.");
            }

        } catch (Exception exception){
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }
}
