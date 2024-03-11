import OutlinedTextFieldFilter from "@/app/components/table/filtersFields/outlinedTextField/outlinedTextFieldFilter";
import { ITableFilter } from "@/interfaces/components/table/ITableFilter";

export const STATION_CATALOG_FILTERS: ITableFilter[] = [
  {
    label: "Код",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "esr",
  },
  {
    label: "Наименование",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "name",
  },
  {
    label: "Краткое наименование",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "shortName",
  },
];
