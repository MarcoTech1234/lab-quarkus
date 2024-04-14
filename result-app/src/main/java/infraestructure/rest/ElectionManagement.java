package infraestructure.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import api.dto.Election;
import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey="election-management")
public interface ElectionManagement {

    @GET
    @Path("/api/elections")
    Uni<List<Election>> getElections();
    
}
