import styles from "@/app/components/table/table.module.css";
import TableColumnHeader from "@/app/components/table/tableColumnHeader";
import TablePage from "@/app/components/table/tablePage";
import { ITableColumn } from "@/interfaces/components/table/iTableColumn";
import { TJSONValue } from "@/interfaces/utils/tJSONValue";
import {
  IPageableResponseContentElement,
  IPageableResponseMetadata,
} from "@/interfaces/response/iPageableResponse";

export default function Table({
  COLUMNS,
  sortsValueMap,
  setSortsValueMap,
  content,
  metadata,
}: {
  COLUMNS: ITableColumn[];
  sortsValueMap: Map<string, string>;
  setSortsValueMap: (map: Map<string, string>) => void;
  content: IPageableResponseContentElement[];
  metadata: IPageableResponseMetadata;
}) {
  const tableHeaders = () => {
    if (!COLUMNS) {
      return;
    }
    let tableHeaders = [];
    for (let i = 0; i < COLUMNS.length; i++) {
      tableHeaders.push(
        <TableColumnHeader
          key={i}
          label={COLUMNS[i].header}
          sortAccessor={COLUMNS[i].sortAccessor}
          sortsValueMap={sortsValueMap}
          setSortsValueMap={setSortsValueMap}
        />,
      );
    }
    return <tr>{tableHeaders}</tr>;
  };

  return (
    <>
      <div className={styles.tableScroll}>
        <table className={styles.table}>
          <thead>{tableHeaders()}</thead>
          <tbody>
            <TablePage
              COLUMNS={COLUMNS}
              content={content}
              metadata={metadata}
            />
          </tbody>
        </table>
      </div>
    </>
  );
}
