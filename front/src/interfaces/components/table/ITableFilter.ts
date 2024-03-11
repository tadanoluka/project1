import { JSX } from "react";

export interface ITableFilter {
  label: string;
  type: JSX.Element;
  filterAccessor: string;
  fetchOptionsOrigin?: string;
}
