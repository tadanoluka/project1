import styles from "@/app/components/table/tableWithFiltersAndPaginationFilters.module.css";
import React, { useState } from "react";
import Image from "next/image";
import { ITableFilter } from "@/interfaces/components/table/ITableFilter";

export default function TableWithFiltersAndPaginationFilters({
  FILTERS,
  filtersValueMap,
  handleSetFilters,
  handleRemoveFilters,
}: {
  FILTERS: ITableFilter[];
  filtersValueMap: Map<string, string>;
  handleSetFilters: (filters: Map<string, string>) => void;
  handleRemoveFilters: () => void;
}) {
  const [localFiltersValueMap, setLocalFiltersValueMap] = useState(
    new Map(filtersValueMap),
  );

  function addFilter(filterAccessor: string, filterValue: string) {
    const copy = new Map(localFiltersValueMap);
    copy.set(filterAccessor, filterValue);
    setLocalFiltersValueMap(copy);
  }

  function clearFilters() {
    setLocalFiltersValueMap(new Map());
    handleRemoveFilters();
  }

  const filtersComponents = () => {
    return FILTERS.map((filter, index) => {
      return React.cloneElement(filter.type, {
        key: index,
        label: filter.label,
        filtersValueMap: filtersValueMap,
        filterAccessor: filter.filterAccessor,
        addFilterFunc: addFilter,
        fetchOptionsOrigin: filter.fetchOptionsOrigin,
      });
    });
  };

  return (
    <div className={styles.filtersControls}>
      <div className={styles.filters}>{filtersComponents()}</div>
      <div className={styles.filterBtns}>
        <div
          className={styles.filterBtn}
          onClick={() => handleSetFilters(localFiltersValueMap)}
        >
          Фильтр
        </div>
        <div className={styles.removeFilterBtn} onClick={clearFilters}>
          <Image
            src="/xMarkIcon.svg"
            className={styles.image}
            width={16}
            height={16}
            alt="X"
          />
        </div>
      </div>
    </div>
  );
}
