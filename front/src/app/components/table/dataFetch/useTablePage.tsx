import { useRouter } from "next/navigation";
import { getPageableSortableFilterableParams } from "@/apiDomain/apiDomain";
import { useEffect, useState } from "react";
import {
  IPageableResponse,
  IPageableResponseData,
} from "@/interfaces/response/iPageableResponse";
import { TJSONValue } from "@/interfaces/utils/tJSONValue";
import { getCredentials } from "@/scripts/auth";

export function useTablePage(
  uri: string,
  index: number,
  size: number,
  sorts: Map<string, string>,
  filters: Map<string, string>,
  cache: Map<string, TJSONValue>,
): IPageableResponse {
  const router = useRouter();
  const [data, setData] = useState<IPageableResponseData>();
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error>();
  const [isError, setIsError] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    async function getData() {
      const params = getPageableSortableFilterableParams(
        index,
        size,
        sorts,
        filters,
      );

      if (cache.has(params)) {
        return cache.get(params);
      }
      const res = await fetch(uri + params, {
        headers: {
          Authorization: `Basic ${getCredentials()}`,
        },
      });

      if (res.status !== 200) {
        throw new Error(res.status.toString());
      }

      const fetchedData = await res.json();

      if (!fetchedData) {
        if (fetchedData.errors) throw new Error(fetchedData.errors);
        throw new Error("No data");
      }

      // Caching
      // cache.set(params, fetchedData);
      return fetchedData;
    }

    getData()
      .then((data) => {
        setData(data);
        setIsLoading(false);
      })
      .catch((reason: Error) => {
        setIsError(true);
        setError(reason);
        setIsLoading(false);
      });
  }, [index, size, sorts, filters]);

  return {
    data: data,
    isLoading: isLoading,
    error: error,
    isError: isError,
  };
}
