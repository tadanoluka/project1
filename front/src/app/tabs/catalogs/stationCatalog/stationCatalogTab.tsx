import TableWithFiltersAndPagination from "@/app/components/table/tableWithFiltersAndPagination";
import { STATION_CATALOG_COLUMNS } from "@/app/tabs/catalogs/stationCatalog/table/STATION_CATALOG_COLUMNS";
import { STATION_CATALOG_FILTERS } from "@/app/tabs/catalogs/stationCatalog/table/STATION_CATALOG_FILTERS";
import { stationsPageURI } from "@/apiDomain/apiDomain";

export default function StationCatalogTab() {
  return (
    <TableWithFiltersAndPagination
      COLUMNS={STATION_CATALOG_COLUMNS}
      FILTERS={STATION_CATALOG_FILTERS}
      pageURI={stationsPageURI}
    />
  );
}
