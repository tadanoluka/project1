import { ITableColumn } from "@/interfaces/components/table/iTableColumn";

export const APPROACH_TABLE_COLUMNS: ITableColumn[] = [
  {
    header: "Номер вагона",
    accessor: "id",
    sortAccessor: "id",
    hrefPattern: "./wagons/",
    hrefTarget: "WagonInfo",
  },
  {
    header: "Станция назначения",
    accessor: "destinationStation.name",
    sortAccessor: "destinationStation_name",
  },
  {
    header: "Станция операции",
    accessor: "operationStation.name",
    sortAccessor: "operationStation_name",
  },
  {
    header: "Груз",
    accessor: "freight.name",
    sortAccessor: "freight_name",
  },
  {
    header: "Грузополучатель",
    accessor: "consignee",
    sortAccessor: "consignee_id",
  },
  {
    header: "Вес",
    accessor: "weight",
    sortAccessor: "weight",
  },
  {
    header: "Состояние",
    accessor: "wagonStatus.name",
    sortAccessor: "wagonStatus_name",
  },
];
