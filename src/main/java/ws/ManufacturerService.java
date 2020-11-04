package ws;

import dtos.ManufacturerDTO;
import ebjs.ManufacturerBean;
import entities.Manufacturer;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/manufacturers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ManufacturerService {

    @EJB
    ManufacturerBean manufacturerBean;

    private ManufacturerDTO toDTO(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getName(),
                manufacturer.getEmaill()
        );
    }

    private List<ManufacturerDTO> toDTOs(List<Manufacturer> manufacturers) {
        return manufacturers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ManufacturerDTO> getAllManufacturersWS() {
        return toDTOs(manufacturerBean.getAll());
    }

    @GET
    @Path("{name}")
    public Response getManufacturerDetails(@PathParam("name") String name) {

        Manufacturer manufacturer = manufacturerBean.findManufacturer(name);

        if (manufacturer != null)
            return Response.status(Response.Status.OK).entity(toDTO(manufacturer)).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR").build();
    }

    @POST
    @Path("/")
    public Response create(ManufacturerDTO manufacturerDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyIllegalArgumentException {

        manufacturerBean.create(
                manufacturerDTO.getName(),
                manufacturerDTO.getEmail()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(ManufacturerDTO manufacturerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        manufacturerBean.update(
                manufacturerDTO.getName(),
                manufacturerDTO.getEmail()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {
        manufacturerBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
