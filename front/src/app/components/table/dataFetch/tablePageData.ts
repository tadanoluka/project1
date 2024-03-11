import { useTablePage } from "@/app/components/table/dataFetch/useTablePage";
import { IPageableResponse } from "@/interfaces/response/iPageableResponse";
import { TJSONValue } from "@/interfaces/utils/tJSONValue";

export class TablePageData {
  static cache = new Map<string, TJSONValue>();

  static getPage(
    uri: string,
    pageIndex: number,
    pageSize: number,
    sorts: Map<string, string>,
    filters: Map<string, string>,
  ): IPageableResponse {
    return useTablePage(uri, pageIndex, pageSize, sorts, filters, this.cache);
  }
}
