import styles from "@/app/components/table/table.module.css";
import { ITableColumn } from "@/interfaces/components/table/iTableColumn";
import {
  IPageableResponseContentElement,
  IPageableResponseMetadata,
} from "@/interfaces/response/iPageableResponse";
import { getValue } from "@/app/components/table/getValue";

export default function TablePage({
  COLUMNS,
  content,
  metadata,
}: {
  COLUMNS: ITableColumn[];
  content: IPageableResponseContentElement[];
  metadata: IPageableResponseMetadata;
}) {
  function handleClick(url: string, target: string) {
    const options = "titlebar=no,statusbar=no,innerHeight=471,innerWidth=700";
    window.open(url, target, options);
  }

  if (!content) {
    return (
      <tr>
        {COLUMNS.map((column, index) => (
          <td key={index}>{"No content"}</td>
        ))}
      </tr>
    );
  } else {
    let counter: bigint = BigInt(
      BigInt(metadata.firstElementOrderNumber) - BigInt(1),
    );
    return content.map((item, index) => {
      counter += BigInt(1);
      return (
        <tr key={item.id ? item.id : index}>
          <td>{counter.toString()}</td>
          {COLUMNS.slice(1).map((column, index) => {
            if (column.accessor) {
              const value = getValue(item, column.accessor);
              if (!column.hrefPattern) {
                return <td key={index}>{value}</td>;
              } else {
                return (
                  <td key={index}>
                    <div
                      className={styles.link}
                      onClick={() =>
                        handleClick(
                          `${column.hrefPattern}${value}`,
                          column.hrefTarget
                            ? column.hrefTarget
                            : "DefaultTarget",
                        )
                      }
                    >
                      {value}
                    </div>
                  </td>
                );
              }
            }
          })}
        </tr>
      );
    });
  }
}
