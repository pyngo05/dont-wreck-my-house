package learn.dontwreckmyhouse.data;

import learn.dontwreckmyhouse.domain.Result;
import learn.dontwreckmyhouse.models.Host;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HostFileRepository implements HostRepository {

    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String hostsFilepath;

    public HostFileRepository(String hostsFilepath) {
        this.hostsFilepath = hostsFilepath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(hostsFilepath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Result<Host> findByHostId(UUID hostId) {
        List<Host> all = findAll();
        Host host = all.stream()
                .filter(i -> i.getHostId().equals(hostId))
                .findFirst()
                .orElse(null);
        if (host == null) {
            return new Result<>("Couldn't find host.");
        }
        return new Result<>(host);
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setHostId(UUID.fromString(fields[0]));
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostalCode(fields[7]);
        result.setStandardRate(new BigDecimal(fields[8]));
        result.setWeekendRate(new BigDecimal(fields[9]));
        return result;
    }

}