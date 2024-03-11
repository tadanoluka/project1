import styles from "@/app/components/table/tableWithFiltersAndPaginationFooter.module.css";
import MiniDropUpSelectField from "@/app/components/inputs/miniDropUpSelectField/miniDropUpSelectField";
import MiniInputField from "@/app/components/inputs/miniInputField/miniInputField";
import PaginationButtons from "@/app/components/table/paginationButtons";
import { IPageableResponseMetadata } from "@/interfaces/response/iPageableResponse";

export default function TableWithFiltersAndPaginationFooter({
  rowsForPageSelectOptionsValues,
  rowsForPage,
  changeTableSizeForPage,
  currentPage,
  setCurrentPage,
  metadata,
}: {
  rowsForPageSelectOptionsValues: number[];
  rowsForPage: number;
  changeTableSizeForPage: (newSize: number) => void;
  currentPage: number;
  setCurrentPage: (pageIndex: number) => void;
  metadata: IPageableResponseMetadata;
}) {
  if (!metadata) {
    return <div className={styles.tableFooter}>No metadata</div>;
  }

  return (
    <div className={styles.tableFooter}>
      <div className={styles.leftSide}>
        <div className={styles.text}>Строк на страницу</div>
        <MiniDropUpSelectField
          options={rowsForPageSelectOptionsValues}
          setFunc={changeTableSizeForPage}
          variable={rowsForPage}
        />
      </div>
      <div className={styles.rightSide}>
        <div className={styles.info}>
          {`${metadata.firstElementOrderNumber.toLocaleString()} - 
          ${metadata.lastElementOrderNumber.toLocaleString()} 
          из ${metadata.totalElements.toLocaleString()}`}
        </div>
        <div className={styles.goToPage}>
          <div>Страница</div>
          <MiniInputField variable={currentPage} setFunc={setCurrentPage} />
        </div>
        <div className={styles.buttons}>
          <PaginationButtons
            currentPage={currentPage}
            totalPages={metadata.totalPages}
            setPageFunc={setCurrentPage}
          />
        </div>
      </div>
    </div>
  );
}
