import { IStation } from "@/interfaces/data/station/iStation";
import { IWagonStatus } from "@/interfaces/data/wagonStatus/iWagonStatus";
import { IFreight } from "@/interfaces/data/freight/iFreight";

export interface IWagon {
  id: bigint;
  destinationStation: IStation;
  operationStation: IStation;
  freight: IFreight;
  consignee: number;
  weight: number;
  wagonStatus: IWagonStatus;
}
