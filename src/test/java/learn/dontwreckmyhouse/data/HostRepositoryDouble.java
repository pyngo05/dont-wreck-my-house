package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Host;
import learn.dontwreckmyhouse.models.Reservation;
import learn.dontwreckmyhouse.models.Guest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

    public class HostRepositoryDouble implements HostRepository {

        private final ArrayList<Host> hosts = new ArrayList<>();

        public HostRepositoryDouble() {
            Host host = new Host();
            host.setHostId(UUID.fromString("3edda6bc-ab95-49a8-8962-d50b53f84b15"));
            host.setLastName("Yearnes");
            host.setEmail("eyearnes0@sfgate.com");
            host.setPhone("(806) 1783815");
            host.setAddress("3 Nova Trail,Amarillo");
            host.setCity("TX");
            host.setPostalCode("79182");
            host.setStandardRate(BigDecimal.valueOf(340));
            host.setWeekendRate(BigDecimal.valueOf(425));
            hosts.add(host);
        }

        @Override
        public List<Host> findAll() {
            return new ArrayList<>(hosts);
        }

        @Override
        public Result<Host> findByHostId(UUID hostId) {
            Host host = findAll().stream()
                    .filter(i -> i.getHostId() == hostId)
                    .findFirst()
                    .orElse(null);
            return new Result<>(host);
        }

}