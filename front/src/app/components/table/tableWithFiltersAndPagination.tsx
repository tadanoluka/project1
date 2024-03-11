import styles from "@/app/components/table/tableWithFiltersAndPagination.module.css";
import { JSX, useEffect, useState } from "react";
import Spinner1 from "@/app/components/spinners/spinner1";
import Table from "@/app/components/table/table";
import TableWithFiltersAndPaginationFilters from "@/app/components/table/tableWithFiltersAndPaginationFilters";
import TableWithFiltersAndPaginationFooter from "@/app/components/table/tableWithFiltersAndPaginationFooter";
import { TablePageData } from "@/app/components/table/dataFetch/tablePageData";
import { ITableColumn } from "@/interfaces/components/table/iTableColumn";
import { ITableFilter } from "@/interfaces/components/table/ITableFilter";
import {
  IPageableResponseMetadata,
  IPageableResponse,
  IPageableResponseContentElement,
} from "@/interfaces/response/iPageableResponse";

export default function TableWithFiltersAndPagination({
  COLUMNS,
  FILTERS,
  pageURI,
  defaultRowsForPage,
  rowsForPageSelectOptionsValues,
}: {
  COLUMNS: ITableColumn[];
  FILTERS: ITableFilter[];
  pageURI: string;
  defaultRowsForPage?: number;
  rowsForPageSelectOptionsValues?: number[];
}): JSX.Element {
  if (!defaultRowsForPage) {
    defaultRowsForPage = 25;
  }
  if (!rowsForPageSelectOptionsValues) {
    rowsForPageSelectOptionsValues = [5, 10, 15, 25, 50, 100];
  }

  const [currentPage, setCurrentPage] = useState<number>(0);
  const [rowsForPage, setRowsForPage] = useState<number>(defaultRowsForPage);

  const [sortsValueMap, setSortsValueMap] = useState<Map<string, string>>(
    new Map(),
  );
  const [filtersValueMap, setFiltersValueMap] = useState<Map<string, string>>(
    new Map(),
  );

  const [isOrderNumColumnAdded, setIsOrderNumColumnAdded] =
    useState<boolean>(false);

  if (!isOrderNumColumnAdded && COLUMNS && COLUMNS[0] && COLUMNS[0].header) {
    if (COLUMNS[0].header !== "№ п.п.") {
      COLUMNS.unshift({ header: "№ п.п." });
    }
    setIsOrderNumColumnAdded(true);
  }

  const pageableResponse: IPageableResponse = TablePageData.getPage(
    pageURI,
    currentPage,
    rowsForPage,
    sortsValueMap,
    filtersValueMap,
  );

  const [content, setContent] = useState<IPageableResponseContentElement[]>();
  const [metadata, setMetadata] = useState<IPageableResponseMetadata>();

  useEffect(() => {
    if (pageableResponse.data) {
      setContent(pageableResponse.data.content);
      setMetadata(pageableResponse.data.metadata);
    }
  }, [pageableResponse.data]);

  function handleSetFilters(filters: Map<string, string>): void {
    const copy = new Map(filters);
    setFiltersValueMap(copy);
    setCurrentPage(0);
  }

  function handleRemoveFilters(): void {
    setSortsValueMap(new Map());
    setFiltersValueMap(new Map());
  }

  function changeTableSizeForPage(newSize: number): void {
    const newPageIndex = Math.floor((rowsForPage * currentPage) / newSize);
    setCurrentPage(newPageIndex);
    setRowsForPage(newSize);
  }

  function changeCurrentPageTo(index: number): void {
    if (metadata) {
      if (index <= metadata.totalPages - 1 && index >= 0) {
        setCurrentPage(index);
      } else if (index >= metadata.totalPages - 1) {
        setCurrentPage(metadata.totalPages - 1);
      } else {
        setCurrentPage(0);
      }
    }
  }

  if (pageableResponse.isLoading) {
    return <Spinner1 />;
  }

  if (pageableResponse.error) {
    return <div>{pageableResponse.error.name}</div>;
  }

  if (pageableResponse.isError) {
    return <div>Unexpected Error</div>;
  }

  if (!metadata) {
    return <div>No metadata</div>;
  }

  if (!content) {
    return <div>No content</div>;
  }

  return (
    <>
      <TableWithFiltersAndPaginationFilters
        FILTERS={FILTERS}
        filtersValueMap={filtersValueMap}
        handleSetFilters={handleSetFilters}
        handleRemoveFilters={handleRemoveFilters}
      />
      <div className={styles.tableWrapper}>
        <Table
          COLUMNS={COLUMNS}
          content={content}
          metadata={metadata}
          sortsValueMap={sortsValueMap}
          setSortsValueMap={setSortsValueMap}
        />
      </div>
      <TableWithFiltersAndPaginationFooter
        rowsForPageSelectOptionsValues={rowsForPageSelectOptionsValues}
        rowsForPage={rowsForPage}
        changeTableSizeForPage={changeTableSizeForPage}
        currentPage={currentPage}
        setCurrentPage={changeCurrentPageTo}
        metadata={metadata}
      />
    </>
  );
}
