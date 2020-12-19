package ws;

import dtos.SimulationDTO;
import ejbs.SimulacaoBean;
import ejbs.VarianteBean;
import entities.Variante;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/simulations")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SimulationService {

    @EJB
    private SimulacaoBean simulacaoBean;

    @EJB
    private VarianteBean varianteBean;

    @POST
    @Path("/")
    public Response getSimulation(SimulationDTO simulationDTO) throws MyEntityNotFoundException {

        System.out.println(simulationDTO.getNb() + " - " + simulationDTO.getVariantCode());
        Variante variante = varianteBean.findVariante(simulationDTO.getVariantCode());

        if (variante == null) {
            throw new MyEntityNotFoundException("Variante com o nome "+variante.getDisplayName()+" não existe");
        }

        String resultadoSimulacao = simulacaoBean.simulaVariante(simulationDTO.getNb(), simulationDTO.getLVao(), simulationDTO.getQ(), variante);

        System.out.println(resultadoSimulacao);
        return Response.status(Response.Status.OK).entity(resultadoSimulacao).build();
    }

    @POST
    @Path("/getMaterials")
    public Response getMaterialsSimulation(SimulationDTO simulationDTO) throws MyEntityNotFoundException {

        List<Variante> variantes = varianteBean.getAll();

        if (variantes == null) {
            throw new MyEntityNotFoundException("Não existem variantes de produto na base de dados");
        }

        String resultadoSimulacao = "";

        for (Variante variante: variantes) {
            resultadoSimulacao += simulacaoBean.simulaVarianteGeraMateriais(simulationDTO.getNb(), simulationDTO.getLVao(), simulationDTO.getQ(), variante).getDisplayName() + " pode ser utilizado na estrutura selecionada" + System.lineSeparator() + System.lineSeparator();
        }

        return Response.status(Response.Status.OK).entity(resultadoSimulacao).build();
    }
}
