import OutlinedTextFieldFilter from "@/app/components/table/filtersFields/outlinedTextField/outlinedTextFieldFilter";
import { ITableFilter } from "@/interfaces/components/table/ITableFilter";

export const FREIGHT_CATALOG_FILTERS: ITableFilter[] = [
  {
    label: "Код",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "etsng",
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
  {
    label: "Группа",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "freightsGroup_name",
  },
];
