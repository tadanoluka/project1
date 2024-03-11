import styles from "@/app/components/table/filtersFields/outlinedTextField/outlinedTextFieldFilter.module.css";
import { ChangeEvent, useState } from "react";
import classNames from "classnames";
import { roboto } from "@/fonts/fonts";

export default function OutlinedTextFieldFilter({
  label,
  filtersValueMap,
  filterAccessor,
  addFilterFunc,
}: {
  label: string;
  filtersValueMap: Map<string, string>;
  filterAccessor: string;
  addFilterFunc: (filterAccessor: string, value: string) => void;
}) {
  const [localValue, setLocalValue] = useState<string>(
    getLocalValueInitValue(),
  );

  function getLocalValueInitValue(): string {
    const initValue = filtersValueMap.get(filterAccessor);
    if (initValue) {
      return initValue;
    } else {
      return "";
    }
  }

  function onChangeHandler(event: ChangeEvent<HTMLInputElement>): void {
    addFilterFunc(filterAccessor, event.target.value);
    setLocalValue(event.target.value);
  }

  return (
    <div className={styles.textField}>
      <label className={styles.textLabel}>{label}</label>
      <input
        className={classNames(`${roboto.className}`, styles.textInput)}
        type="text"
        placeholder="Введите значение"
        value={localValue}
        onChange={onChangeHandler}
      />
    </div>
  );
}
