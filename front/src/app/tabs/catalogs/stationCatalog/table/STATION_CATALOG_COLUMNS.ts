import { ITableColumn } from "@/interfaces/components/table/iTableColumn";

export const STATION_CATALOG_COLUMNS: ITableColumn[] = [
  {
    header: "Код",
    accessor: "esr",
    sortAccessor: "esr",
    hrefPattern: "./stations/",
    hrefTarget: "StationInfo",
  },
  {
    header: "Наименование",
    accessor: "name",
    sortAccessor: "name",
  },
  {
    header: "Краткое наименование",
    accessor: "shortName",
    sortAccessor: "shortName",
  },
];
