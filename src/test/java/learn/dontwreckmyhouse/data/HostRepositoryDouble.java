package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HostRepositoryDouble implements HostRepository {

    public final static Host HOST = new Host();
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(HOST);
    }

    @Override
    public List<Host> findAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findByHostId(UUID hostId) {
        return findAll().stream()
                .filter(i -> i.getHostId() == hostId)
                .findFirst()
                .orElse(null);
    }
}
//1010,"Ngo","ngo@gmail.com","01234567890","91 Nova Trail","New York City","NY",10292,108,135