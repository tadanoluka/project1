package db.migration;

import lombok.Data;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

public class R__0005_Add_freights_and_freights_groups extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        DataSource dataSource = new SingleConnectionDataSource(context.getConnection(), true);
        NamedParameterJdbcOperations jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        insertFreights(jdbcOperations, jdbcTemplate);
    }

    private void insertFreights(NamedParameterJdbcOperations jdbcOperations, JdbcTemplate jdbcTemplate) {
        List<Map<String, Object>> paramsForFreight = new ArrayList<>();
        List<RzdFreight> rzdFreights = getRzdFreights();

        for (RzdFreight rzdFreight : rzdFreights) {
            String groupName = rzdFreight.group_name;
            Integer groupId;
            try {
                groupId = jdbcTemplate.queryForObject(
                        """
                        SELECT id FROM freights_groups
                        WHERE name ILIKE '%s'
                        """.formatted(groupName), Integer.class);
            } catch (EmptyResultDataAccessException e) {
                jdbcTemplate.execute(
                        """
                        INSERT INTO freights_groups(name)
                        VALUES ('%s')
                        """.formatted(groupName));
            } finally {
                groupId = jdbcTemplate.queryForObject(
                        """
                        SELECT id FROM freights_groups
                        WHERE name ILIKE '%s'
                        """.formatted(groupName), Integer.class);
            }

            if (groupId == null) {
                throw new RuntimeException();
            }

            paramsForFreight.add(
                    new MapSqlParameterSource("etsng", rzdFreight.code)
                            .addValue("name", rzdFreight.name)
                            .addValue("shortName", rzdFreight.short_name)
                            .addValue("groupId", groupId)
                            .getValues()
            );

        }
        jdbcOperations.batchUpdate(
                """
                INSERT INTO freights(etsng, name, short_name, group_id)
                VALUES (:etsng, :name, :shortName, :groupId)
                """,
                paramsForFreight.toArray(new Map[0])
        );
    }

    public List<RzdFreight> getRzdFreights() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI("https://cargolk.rzd.ru/api/v1/nsi/freights");
            ResponseEntity<RzdResponse> responseEntity = restTemplate.getForEntity(uri, RzdResponse.class);
            return Objects.requireNonNull(responseEntity.getBody()).data;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Data
    public static class RzdFreight {
        private int code;
        private String name;
        private String short_name;
        private String group_name;
    }

    @Data
    private static class RzdResponse {
        private List<RzdFreight> data;
    }
}
