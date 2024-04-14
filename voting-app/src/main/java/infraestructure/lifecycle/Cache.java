package infraestructure.lifecycle;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import infraestructure.repository.RedisElectionRepository;
import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class Cache {

    private static final Logger LOGGER = Logger.getLogger(Cache.class);
    
    public Cache(RedisElectionRepository repository) {
        LOGGER.info("Startup: Cache");
        repository.findAll();
    }
}
