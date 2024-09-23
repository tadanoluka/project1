"use client";

import styles from "./miniInputField.module.css";
import React, { useEffect, useRef, useState } from "react";
import classNames from "classnames";
import { roboto } from "@/fonts/fonts";

export default function MiniInputField({
  variable,
  setFunc,
}: {
  variable: string;
  setFunc: (value: string) => void;
}) {
  const [value, setValue] = useState<string>(
    (parseInt(variable) + 1).toString(),
  );

  const inputField = useRef<HTMLInputElement>(null);

  useEffect(() => {
    setValue((parseInt(variable) + 1).toString());
    if (inputField.current) {
      const length = variable.length;
      inputField.current.size = length > 0 ? length : 1;
    }
  }, [variable]);

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value);
  }

  function handleLostFocus(event: React.FocusEvent<HTMLInputElement>) {
    if (event.target.value === "") {
      setValue((parseInt(variable) + 1).toString());
    } else {
      setFunc((parseInt(event.target.value) - 1).toString());
    }
  }

  function handleClick() {
    setValue("");
  }

  return (
    <div className={styles.selectField}>
      <input
        className={classNames(`${roboto.className}`, styles.pageInput)}
        ref={inputField}
        type="text"
        value={value}
        onClick={handleClick}
        onKeyDown={(event) => {
          if (event.key === "Enter") {
            event.currentTarget.blur();
            return;
          }
          if (!/[0-9]/.test(event.key)) {
            event.preventDefault();
          }
        }}
        onChange={handleChange}
        onBlur={handleLostFocus}
      />
    </div>
  );
}
