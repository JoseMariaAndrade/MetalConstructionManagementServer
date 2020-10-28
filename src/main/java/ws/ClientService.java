package ws;

import dtos.ClientDTO;
import ebjs.ClientBean;
import entities.Client;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientService {

    @EJB
    ClientBean clientBean;

    private ClientDTO toDTO(Client client){
        ClientDTO clientDTO = new ClientDTO(
                client.getName(),
                client.getEmaill(),
                client.getContact(),
                client.getMorada()
        );

        return clientDTO;
    }

    private List<ClientDTO> toDTOs(List<Client> clients){
        return clients.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ClientDTO> getAllClientsWS() {
        return toDTOs(clientBean.getAll());
    }

    @POST
    @Path("/")
    public Response create(ClientDTO clientDTO)
            throws MyEntityExistsException, MyConstraintViolationException {

        clientBean.create(
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getContact(),
                clientDTO.getAddress()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{name}")
    public Response getClientDeatils(@PathParam("name") String name){

        Client client = clientBean.findClient(name);

        if (client != null) {
            return Response.status(Response.Status.OK).entity(toDTO(client)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    public Response update(ClientDTO clientDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        clientBean.update(
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getContact(),
                clientDTO.getAddress()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name)
            throws MyEntityNotFoundException {

        clientBean.delete(name);

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
