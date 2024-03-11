import styles from "@/app/components/table/filtersFields/selectInputField/selectInputFieldFilter.module.css";
import classNames from "classnames";
import { roboto } from "@/fonts/fonts";
import React, { useEffect, useState } from "react";
import { fetcher } from "@/scripts/fetcher/fetcher";
import { useRouter } from "next/navigation";

type TSelectOption = {
  id: number;
  name: string;
};

export default function SelectInputFieldFilter({
  label,
  filtersValueMap,
  filterAccessor,
  addFilterFunc,
  fetchOptionsOrigin,
}: {
  label: string;
  filtersValueMap: Map<string, string>;
  filterAccessor: string;
  addFilterFunc: (filterAccessor: string, filterValue: string) => void;
  fetchOptionsOrigin: string;
}) {
  const router = useRouter();

  const [localValue, setLocalValue] = useState(getLocalValueInitValue());

  const [inputValue, setInputValue] = useState(localValue);

  const [values, setValues] = useState<TSelectOption[]>();

  function getLocalValueInitValue(): string {
    const initValue = filtersValueMap.get(filterAccessor);
    if (initValue) {
      return initValue;
    } else {
      return "";
    }
  }

  useEffect(() => {
    if (fetchOptionsOrigin) {
      fetcher(fetchOptionsOrigin, router.push).then((res) => {
        res.json().then((data) => convertDataToOptions(data));
      });
    }
  }, []);

  useEffect(() => {
    setInputValue(localValue);
  }, [localValue]);

  function convertDataToOptions(data: any) {
    const options: TSelectOption[] = data.map((elem: TSelectOption) => {
      return {
        id: elem.id,
        name: elem.name,
      };
    });
    setValues(options);
  }

  const options = () => {
    if (!values) return;
    return values.map((obj) => {
      return (
        <div
          key={obj.id}
          className={styles.option}
          onMouseDown={() => setLocalValue(obj.name)}
        >
          {obj.name}
        </div>
      );
    });
  };

  function onChangeHandler(event: React.ChangeEvent<HTMLInputElement>) {
    setInputValue(event.target.value);
    addFilterFunc(filterAccessor, localValue);
  }

  function handleLostFocus() {
    setInputValue(localValue);
    addFilterFunc(filterAccessor, localValue);
  }

  return (
    <div className={styles.selectField}>
      <label className={styles.selectLabel}>{label}</label>
      <input
        className={classNames(`${roboto.className}`, styles.selectInput)}
        type="text"
        placeholder="Выберите значение"
        value={inputValue}
        onChange={onChangeHandler}
        onBlur={handleLostFocus}
      />
      <div className={styles.dropDownContent}>{options()}</div>
    </div>
  );
}
