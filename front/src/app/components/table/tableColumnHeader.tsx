import classNames from "classnames";
import styles from "@/app/components/table/table.module.css";
import Image from "next/image";

type TOrder = "asc" | "desc";

export default function TableColumnHeader({
  label,
  sortsValueMap,
  setSortsValueMap,
  sortAccessor,
}: {
  label: string;
  sortsValueMap: Map<string, string>;
  setSortsValueMap: (map: Map<string, string>) => void;
  sortAccessor?: string;
}) {
  const descIcon = "/descSortIcon.svg";
  const ascIcon = "/ascSortIcon.svg";

  function addSort(sortAccessor: string, order: TOrder) {
    const copy = new Map(sortsValueMap);
    copy.set(sortAccessor, order);
    setSortsValueMap(copy);
  }

  function deleteSort(sortAccessor: string) {
    const copy = new Map(sortsValueMap);
    copy.delete(sortAccessor);
    setSortsValueMap(copy);
  }

  function handleClick(sortAccessor: string) {
    const sortOrder = sortsValueMap.get(sortAccessor);
    switch (sortOrder) {
      case "desc":
        addSort(sortAccessor, "asc");
        break;
      case "asc":
        deleteSort(sortAccessor);
        break;
      default:
        addSort(sortAccessor, "desc");
        break;
    }
  }

  const sortButton = () => {
    if (sortAccessor && sortsValueMap) {
      return (
        <div
          className={
            !sortsValueMap.get(sortAccessor)
              ? classNames(styles.sortIcon)
              : classNames(styles.activeSortIcon)
          }
          onClick={() => handleClick(sortAccessor)}
        >
          <Image
            src={sortsValueMap.get(sortAccessor) === "asc" ? ascIcon : descIcon}
            alt="sort icon"
            width={16}
            height={14}
          />
        </div>
      );
    }
  };

  return (
    <th>
      <div className={styles.columnHeader}>
        <div className={styles.label}>{label}</div>
        {sortButton()}
      </div>
    </th>
  );
}
