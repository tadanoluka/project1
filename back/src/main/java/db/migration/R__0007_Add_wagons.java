package db.migration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.sql.DataSource;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class R__0007_Add_wagons extends BaseJavaMigration {

    private final Random random = new Random();

    @Override
    public void migrate(Context context) throws Exception {
        DataSource dataSource = new SingleConnectionDataSource(context.getConnection(), true);
        NamedParameterJdbcOperations jdbcOperations = new NamedParameterJdbcTemplate(dataSource);

        insertWagons(jdbcOperations);
    }

    private void insertWagons(NamedParameterJdbcOperations jdbcOperations) {
        int wagonCount = 10_000;
        long startId = 50_624_956L;
        List<Map<String, Object>> paramsForWagons = new ArrayList<>(wagonCount);

        List<Integer> stationsIDList = jdbcOperations.queryForList(
            """
            SELECT id FROM stations;
            """,
            new MapSqlParameterSource(),
            Integer.class
        );
        List<Integer> freightsIDList = jdbcOperations.queryForList(
            """
            SELECT id FROM freights;
            """,
            new MapSqlParameterSource(),
            Integer.class
        );
        List<Integer> consigneesIDList = jdbcOperations.queryForList(
            """
            SELECT id FROM consignees;
            """,
            new MapSqlParameterSource(),
            Integer.class
        );
        List<Integer> wagonStatusesIDList = jdbcOperations.queryForList(
            """
            SELECT id FROM wagon_statuses;
            """,
            new MapSqlParameterSource(),
            Integer.class
        );

        for (long id = startId; id <= wagonCount + startId; id++) {
            int destStationId = stationsIDList.get(random.nextInt(0, stationsIDList.size()));
            int operStationId = stationsIDList.get(random.nextInt(0, stationsIDList.size()));
            int freightId = freightsIDList.get(random.nextInt(0, freightsIDList.size()));
            int consigneeId = consigneesIDList.get(random.nextInt(0, consigneesIDList.size()));
            int weight = random.nextInt(0, 100);
            int wagonStatusId = wagonStatusesIDList.get(random.nextInt(0, wagonStatusesIDList.size()));

            paramsForWagons.add(
                new MapSqlParameterSource("id", id)
                    .addValue("destinationStationId", destStationId)
                    .addValue("operationStationId", operStationId)
                    .addValue("freightId", freightId)
                    .addValue("consigneeId", consigneeId)
                    .addValue("weight", weight)
                    .addValue("wagonStatusId", wagonStatusId)
                    .addValue("updatedByUserId", 1)
                    .getValues()
            );
        }
        jdbcOperations.batchUpdate(
            """
            INSERT INTO wagons (id, destination_station_id, operation_station_id, freight_id, consignee_id, weight, wagon_status_id, updated_by_user_id)
            VALUES (:id, :destinationStationId, :operationStationId, :freightId, :consigneeId, :weight, :wagonStatusId, :updatedByUserId)
            """,
            paramsForWagons.toArray(new Map[0])
        );
    }
}
