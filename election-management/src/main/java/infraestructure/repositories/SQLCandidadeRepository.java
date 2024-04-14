package infraestructure.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class SQLCandidadeRepository implements CandidateRepository{

    private final EntityManager entityManager;

    public SQLCandidadeRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
        candidates.stream()
                .map(infraestructure.repositories.entity.Candidate::fromDomain)
                .forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(infraestructure.repositories.entity.Candidate.class);
        var root = cq.from(infraestructure.repositories.entity.Candidate.class);

        cq.select(root).where(conditions(query, cb, root));

        return entityManager.createQuery(cq)
                .getResultStream()
                .map(infraestructure.repositories.entity.Candidate::toDomain)
                .toList();
    }
    
    private Predicate[] conditions(CandidateQuery query, 
                                CriteriaBuilder cb, 
                                Root<infraestructure.repositories.entity.Candidate> root) {
            return Stream.of(query.ids().map(id -> cb.in(root.get("id")).value(id)),
                            query.name().map(name -> cb.or(cb.like(cb.lower(root.get("familyName")), name.toLowerCase() + "%"),
                                cb.like(cb.lower(root.get("givenName")), name.toLowerCase() + "%"))))
                                .flatMap(Optional::stream)
                                .toArray(Predicate[]::new);
    }
}
