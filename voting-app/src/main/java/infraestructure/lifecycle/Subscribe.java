package infraestructure.lifecycle;

import org.jboss.logging.Logger;

import domain.Election;

import javax.enterprise.context.ApplicationScoped;

import infraestructure.repository.RedisElectionRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;

@Startup
@ApplicationScoped
public class Subscribe {
    
    private static final Logger LOGGER = Logger.getLogger(Subscribe.class);

    public Subscribe(ReactiveRedisDataSource ReactiveRedisDataSource,
                    RedisElectionRepository repository) {

        LOGGER.info("Startup: Subscribe");

        Multi<String> sub = ReactiveRedisDataSource.pubsub(String.class)
        .subscribe("elections");
        
        sub.emitOn(Infrastructure.getDefaultWorkerPool())
            .subscribe()
            .with(id -> {
                LOGGER.info("Election " + id + " received from subscription");
                Election election = repository.findById(id);
                LOGGER.info("Election " + election.id() + " starting");
            });
    }
}
