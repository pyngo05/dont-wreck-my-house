package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Host;

import java.util.List;
import java.util.UUID;

public interface HostRepository {

    List<Host> findAll();

    Result<Host> findByHostId(UUID hostId);

}
