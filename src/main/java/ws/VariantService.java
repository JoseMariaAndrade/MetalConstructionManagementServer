package ws;

import dtos.VariantDTO;
import ejbs.VarianteBean;
import entities.Variante;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/variants")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class VariantService {

    @EJB
    private VarianteBean varianteBean;

    @GET
    @Path("/")
    public List<VariantDTO> getAllVariantsWS(){
        return toDTOs(varianteBean.getAll());
    }

    private List<VariantDTO> toDTOs(List<Variante> variantes) {
        return variantes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private VariantDTO toDTO(Variante variante) {
        return new VariantDTO(variante.getCodigo(), variante.getDisplayName());
    }
}
