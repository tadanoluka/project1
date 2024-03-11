import classNames from "classnames";
import styles from "@/app/components/inputs/searchableSelectField/searchableSelectField.module.css";
import { roboto } from "@/fonts/fonts";
import React, { useEffect, useRef, useState } from "react";

export type TSearchableSelectFieldOption = {
  id: number;
  name: string;
};

export default function SearchableSelectField({
  label,
  value,
  setValue,
  options,
  isHighlighted,
  setIsHighlighted,
}: {
  label: string;
  value: string;
  setValue: (value: string) => void;
  options: TSearchableSelectFieldOption[];
  isHighlighted: boolean;
  setIsHighlighted: (value: boolean) => void;
}) {
  const inputRef = useRef<HTMLInputElement>(null);

  const [localValue, setLocalValue] = useState("");
  const [selectedValueName, setSelectedValueName] = useState("");
  const [filteredOptions, setFilteredOptions] = useState(options);

  function getFiltered() {
    if (options) {
      if (Array.isArray(options)) {
        const filteredOptions = options.filter((element) =>
          element.name.toLowerCase().includes(localValue.toLowerCase()),
        );
        setFilteredOptions(filteredOptions);
      }
    }
  }

  function onChangeHandler(e: React.ChangeEvent<HTMLInputElement>) {
    setLocalValue(e.target.value);
  }

  useEffect(() => {
    getFiltered();
  }, [options]);

  useEffect(() => {
    getFiltered();
  }, [localValue]);

  useEffect(() => {
    setLocalValue(selectedValueName);
  }, [selectedValueName]);

  useEffect(() => {
    if (!value) {
      setLocalValue("");
      setSelectedValueName("");
    }
  }, [value]);

  function setSelected(label: string, value: string) {
    setValue(value);
    setSelectedValueName(label);
    setIsHighlighted(false);
    if (inputRef.current) {
      inputRef.current.blur();
    }
  }

  function onInputBlurHandler() {
    setLocalValue(selectedValueName);
  }

  const dropDownOptions = () => {
    if (filteredOptions) {
      return filteredOptions.map((obj) => {
        return (
          <div
            key={obj.id}
            className={styles.option}
            onMouseDown={() => setSelected(obj.name, obj.id.toString())}
          >
            {obj.name.toUpperCase()}
          </div>
        );
      });
    }
  };

  function onArrowToggle() {
    if (inputRef.current) {
      if (document.activeElement !== inputRef.current) {
        inputRef.current.focus();
      } else {
        inputRef.current.blur();
      }
    }
  }

  return (
    <fieldset
      className={
        isHighlighted
          ? classNames(styles.fieldset, styles.highlighted)
          : styles.fieldset
      }
    >
      <legend className={styles.legend}>{label ? label : "Label"}</legend>
      <div className={styles.input}>
        <input
          className={classNames(`${roboto.className}`, styles.textInput)}
          type="text"
          placeholder="Введите значение"
          value={localValue}
          onChange={onChangeHandler}
          ref={inputRef}
          onBlur={onInputBlurHandler}
        />
        <div
          className={styles.arrowWrapper}
          onMouseDown={(event) => event.preventDefault()}
          onClick={onArrowToggle}
        >
          <div className={styles.arrow}>
            <span className={styles.span1}></span>
            <span className={styles.span2}></span>
          </div>
        </div>
      </div>
      <div className={styles.dropDownContent}>
        <div className={styles.optionWrapper}>{dropDownOptions()}</div>
      </div>
    </fieldset>
  );
}
