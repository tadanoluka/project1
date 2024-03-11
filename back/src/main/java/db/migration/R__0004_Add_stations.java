package db.migration;

import lombok.Getter;
import lombok.Setter;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class R__0004_Add_stations extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        DataSource dataSource = new SingleConnectionDataSource(context.getConnection(), true);
        NamedParameterJdbcOperations jdbcOperations = new NamedParameterJdbcTemplate(dataSource);

        insertStations(jdbcOperations);
    }

    private void insertStations(NamedParameterJdbcOperations jdbcOperations) {
        List<Map<String, Object>> paramsForStation = new ArrayList<>();
        List<RzdStation> rzdStations = getRzdStations();

        for (RzdStation rzdStation : rzdStations) {
            paramsForStation.add(
                    new MapSqlParameterSource("esr", rzdStation.code)
                            .addValue("name", rzdStation.name)
                            .addValue("shortName", rzdStation.short_name)
                            .getValues()
            );
        }

        jdbcOperations.batchUpdate(
                """
                INSERT INTO stations(esr, name, short_name)
                VALUES (:esr, :name, :shortName)
                """,
                paramsForStation.toArray(new Map[0])
        );
    }

    public List<RzdStation> getRzdStations() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI("https://cargolk.rzd.ru/api/v1/nsi/stations?russian=true");
            ResponseEntity<RzdResponse> responseEntity = restTemplate.getForEntity(uri, RzdResponse.class);
            return Objects.requireNonNull(responseEntity.getBody()).data;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Getter
    @Setter
    public static class RzdStation {
        private int code;
        private String name;
        private String short_name;
    }

    @Getter
    @Setter
    private static class RzdResponse {
        private List<RzdStation> data;
    }
}
