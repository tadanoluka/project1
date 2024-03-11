import TableWithFiltersAndPagination from "@/app/components/table/tableWithFiltersAndPagination";
import { APPROACH_TABLE_COLUMNS } from "@/app/tabs/movementOfWagons/approach/table/APPROACH_TABLE_COLUMNS";
import { APPROACH_TABLE_FILTERS } from "@/app/tabs/movementOfWagons/approach/table/APPROACH_TABLE_FILTERS";
import { wagonsPageURI } from "@/apiDomain/apiDomain";

export default function ApproachTab() {
  return (
    <TableWithFiltersAndPagination
      COLUMNS={APPROACH_TABLE_COLUMNS}
      FILTERS={APPROACH_TABLE_FILTERS}
      pageURI={wagonsPageURI}
    />
  );
}
