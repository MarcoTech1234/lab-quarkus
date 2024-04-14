package infraestructure.schedulers;

import javax.enterprise.context.ApplicationScoped;

import domain.annotations.Principal;
import infraestructure.repositories.RedisElectionRepository;
import infraestructure.repositories.SQLElectionRepository;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped

public class Sync {
    private final SQLElectionRepository sqlRepository;
    private final RedisElectionRepository redisRepository;

    public Sync(@Principal SQLElectionRepository sqlRepository, RedisElectionRepository redisRepository) {
        this.sqlRepository = sqlRepository;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    void sync() {
        sqlRepository.findAll().forEach(election -> sqlRepository.sync(redisRepository.sync(election)));
    }

}
