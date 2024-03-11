import TableWithFiltersAndPagination from "@/app/components/table/tableWithFiltersAndPagination";
import { FREIGHT_CATALOG_COLUMNS } from "@/app/tabs/catalogs/freightCatalog/table/FREIGHT_CATALOG_COLUMNS";
import { FREIGHT_CATALOG_FILTERS } from "@/app/tabs/catalogs/freightCatalog/table/FREIGHT_CATALOG_FILTERS";
import { freightsPageURI } from "@/apiDomain/apiDomain";

export default function FreightCatalogTab() {
  return (
    <TableWithFiltersAndPagination
      COLUMNS={FREIGHT_CATALOG_COLUMNS}
      FILTERS={FREIGHT_CATALOG_FILTERS}
      pageURI={freightsPageURI}
    />
  );
}
