package ws;

import dtos.VariantDTO;
import ejbs.VarianteBean;
import entities.Variante;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
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

    @DELETE
    @Path("/{codigo}")
    public void deleteVariant(@PathParam("codigo") int codigo) {

        varianteBean.remove(codigo);
    }

    @GET
    @Path("/")
    public List<VariantDTO> getAllVariantsWS(){
        return toDTOs(varianteBean.getAll());
    }

    @POST
    @Path("/")
    public Response create(VariantDTO variantDTO) throws MyEntityNotFoundException, MyEntityExistsException {

        varianteBean.create(variantDTO.getCodigo(), variantDTO.getProduto(),variantDTO.getNome(), variantDTO.getWeff_p(), variantDTO.getWeff_n(), variantDTO.getAr(), variantDTO.getSigmaC());

        return Response.status(Response.Status.CREATED).build();
    }

    private List<VariantDTO> toDTOs(List<Variante> variantes) {
        return variantes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private VariantDTO toDTO(Variante variante) {
        return new VariantDTO(variante.getCodigo(), variante.getDisplayName());
    }
}
