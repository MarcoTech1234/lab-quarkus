package infraestructure.resources;

import java.time.Duration;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestStreamElementType;

import api.dto.Election;
import infraestructure.rest.ElectionManagement;
import io.smallrye.mutiny.Multi;

@Path("/")
public class ResultResource {
    
    private final ElectionManagement electionManagement;

    

    public ResultResource(@RestClient ElectionManagement electionManagement) {
        this.electionManagement = electionManagement;
    }



    @GET
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<List<Election>> results() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(10))
                .onItem()
                .transformToMultiAndMerge(n -> electionManagement.getElections().toMulti());
    }
}
