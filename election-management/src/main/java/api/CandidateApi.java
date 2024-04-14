package api;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;

import domain.CandidateService;

@ApplicationScoped
public class CandidateApi {

    @Inject
    private final CandidateService service;

    public CandidateApi(CandidateService service){
        this.service = service;
    }

    public void create(api.dto.in.CreateCandidate dto){
        service.save(dto.toDomain());
    }

    public api.dto.out.Candidate update(String id, api.dto.in.UpdateCandidate dto){
        service.save(dto.toDomain(id));
        return api.dto.out.Candidate.fromDomain(service.findById(id));
    }

    public List<api.dto.out.Candidate> list(){
        return service.findAll().stream().map(api.dto.out.Candidate::fromDomain).toList();
    }
}
