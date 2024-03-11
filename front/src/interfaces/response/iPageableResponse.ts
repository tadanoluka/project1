import { TJSONValue } from "@/interfaces/utils/tJSONValue";
import { IWagon } from "@/interfaces/data/wagon/iWagon";
import { IFreight } from "@/interfaces/data/freight/iFreight";
import { IStation } from "@/interfaces/data/station/iStation";

export interface IPageableResponse {
  data: IPageableResponseData | undefined;
  isLoading: boolean;
  error: Error | undefined;
  isError: boolean;
}

export interface IPageableResponseData {
  content: IPageableResponseContentElement[];
  metadata: IPageableResponseMetadata;
}

export interface IPageableResponseMetadata {
  page: number;
  size: number;
  totalElements: bigint;
  totalPages: number;
  numberOfElementsInPage: number;
  firstElementOrderNumber: bigint;
  lastElementOrderNumber: bigint;
}

export type IPageableResponseContentElement = IWagon | IFreight | IStation;
