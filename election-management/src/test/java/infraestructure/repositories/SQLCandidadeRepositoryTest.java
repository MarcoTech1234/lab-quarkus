package infraestructure.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SQLCandidadeRepositoryTest extends CandidateRepositoryTest{

    @Inject
    SQLCandidadeRepository repository;

    @Inject
    EntityManager entityManager;

    @Override
    public CandidateRepository repository() {
        return repository;
    }

    @BeforeEach
    @TestTransaction
    void tearDown(){
        entityManager.createNativeQuery("TRUNCATE TABLE candidates").executeUpdate();
    }
}
