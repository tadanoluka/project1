import OutlinedTextFieldFilter from "@/app/components/table/filtersFields/outlinedTextField/outlinedTextFieldFilter";
import SelectInputFieldFilter from "@/app/components/table/filtersFields/selectInputField/selectInputFieldFilter";
import { wagonStatusesAllURI } from "@/apiDomain/apiDomain";
import { ITableFilter } from "@/interfaces/components/table/ITableFilter";

export const APPROACH_TABLE_FILTERS: ITableFilter[] = [
  {
    label: "Номер вагона",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "id",
  },
  {
    label: "Станция назначения",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "destinationStation_name",
  },
  {
    label: "Станция операции",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "operationStation_name",
  },
  {
    label: "Груз",
    // @ts-ignore
    type: <OutlinedTextFieldFilter />,
    filterAccessor: "freight_name",
  },
  {
    label: "Состояние",
    // @ts-ignore
    type: <SelectInputFieldFilter />,
    filterAccessor: "wagonStatus_name",
    fetchOptionsOrigin: wagonStatusesAllURI,
  },
];
