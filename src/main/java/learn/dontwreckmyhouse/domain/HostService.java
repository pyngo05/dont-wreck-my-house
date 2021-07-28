package learn.dontwreckmyhouse.domain;

import learn.dontwreckmyhouse.data.HostRepository;
import learn.dontwreckmyhouse.models.Host;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findByHostId(UUID hostId) {
        return repository.findAll().stream()
                .filter(i -> i.getHostId() == hostId)
                .collect(Collectors.toList());
    }

}
