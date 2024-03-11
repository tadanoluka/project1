export const apiURI = "http://localhost:8080/api/v1";

export const wagonsPageURI = apiURI + "/wagons";

export const stationsPageURI = apiURI + "/stations";

export const freightsPageURI = apiURI + "/freights";

export const getPageableSortableFilterableParams = (
  index: number,
  size: number,
  sorts: Map<string, string>,
  filters: Map<string, string>,
) => {
  const mainParams = `?page=${index}&size=${size}`;

  const sortParamsArr: string[] = [];
  sorts.forEach((value, key) => sortParamsArr.push(`&sort=${key},${value}`));
  const sortParams = sortParamsArr ? sortParamsArr.join("") : "";

  const filterParamsArr: string[] = [];
  filters.forEach((value, key) => {
    filterParamsArr.push(`&filter=${key},${value}`);
  });
  const filterParams = filterParamsArr ? filterParamsArr.join("") : "";

  return mainParams + sortParams + filterParams;
};

export const wagonStatusesAllURI = apiURI + "/wagonStatuses/all";
export const stationURI = (stationId: number) => {
  return stationsPageURI + `/${stationId}`;
};
export const wagonURI = (wagonId: number) => {
  return wagonsPageURI + `/${wagonId}`;
};
export const freightURI = (freightId: number) => {
  return freightsPageURI + `/${freightId}`;
};
export const stationsAllURI = stationsPageURI + "/all";
export const stationsAllForUserURI = stationsAllURI + "/forUser";

export const userURI = apiURI + "/user";

export const signinURI = apiURI + "/auth/signin";
