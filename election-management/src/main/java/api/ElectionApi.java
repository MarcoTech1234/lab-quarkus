package api;

import java.util.List;

import api.dto.out.Election;
import domain.ElectionService;

public class ElectionApi {

    private final ElectionService service;

    public ElectionApi(ElectionService service) {
        this.service = service;
    }

    public void submit() {
        service.submit();
    }

    public List<Election> findAll() {
        return service.findAll()
                .stream()
                .map(Election::fromDomain)
                .toList();
    }
}
