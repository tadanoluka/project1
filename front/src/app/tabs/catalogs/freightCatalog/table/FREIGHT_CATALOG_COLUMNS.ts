import { ITableColumn } from "@/interfaces/components/table/iTableColumn";

export const FREIGHT_CATALOG_COLUMNS: ITableColumn[] = [
  {
    header: "Код",
    accessor: "etsng",
    sortAccessor: "etsng",
    hrefPattern: "./freights/",
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
  {
    header: "Группа",
    accessor: "groupName",
    sortAccessor: "freightsGroup_name",
  },
];
