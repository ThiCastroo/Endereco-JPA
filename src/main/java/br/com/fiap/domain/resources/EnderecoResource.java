package br.com.fiap.domain.resources;

import br.com.fiap.Main;
import br.com.fiap.domain.dto.EnderecoDTO;
import br.com.fiap.domain.entity.Endereco;
import br.com.fiap.domain.service.EnderecoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Objects;

@Path("/endereco")
public class EnderecoResource {

    @Context
    UriInfo uriInfo;
    private EnderecoService service = EnderecoService.build(Main.PERSISTENCE_UNIT);
    @GET
    @Path("/{cep}")
    public Response findByCep(@PathParam("cep") String cep) {
        Endereco endereco = service.findByCep(cep);
        if(Objects.isNull(endereco)) return Response.status(404).build();
        return Response.ok(endereco).build();
    }

    @POST
    public Response persist(EnderecoDTO dto) {
        Endereco endereco = service.persiste( dto );
        if (Objects.isNull( endereco )) return Response.status( 400 ).build();
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path( String.valueOf( endereco.getCep() ) ).build();
        return Response.created( uri ).entity( EnderecoDTO.of( endereco ) ).build();
    }

}
