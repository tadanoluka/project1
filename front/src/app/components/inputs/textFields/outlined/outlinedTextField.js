import styles from "@/app/components/inputs/textFields/outlined/outlinedTextField.module.css";
import classNames from "classnames";
import { roboto } from "@/fonts/fonts";

export default function OutlinedTextField({
  className,
  label,
  type,
  prompt,
  name,
  value,
  onChange,
}) {
  return (
    <fieldset className={classNames(styles.fieldset, className)}>
      <legend className={styles.legend}>{label}</legend>

      <input
        className={classNames(`${roboto.className}`, styles.textInput)}
        type={type ? type : "text"}
        placeholder={prompt ? prompt : "Введите значение"}
        name={name}
        value={value}
        onChange={onChange}
      />
    </fieldset>
  );
}
