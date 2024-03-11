import styles from "./miniDropUpSelectField.module.css";

export default function MiniDropUpSelectField({
  variable,
  setFunc,
  options,
}: {
  variable: number;
  setFunc: (value: number) => void;
  options: number[];
}) {
  const dropUpOptions = () => {
    const dropUpOptions = [];
    for (let i = 0; i < options.length; i++) {
      dropUpOptions.push(
        <div
          key={i}
          className={styles.option}
          onMouseDown={() => setFunc(options[i])}
        >
          {options[i]}
        </div>,
      );
    }
    return <>{dropUpOptions}</>;
  };

  return (
    <div className={styles.selectField}>
      <div className={styles.dropUpContent}>{dropUpOptions()}</div>
      <div className={styles.selectFieldLabel} tabIndex={0}>
        <div className={styles.selectLabel}>{variable}</div>
        <div className={styles.arrow}>
          <span className={styles.span1}></span>
          <span className={styles.span2}></span>
        </div>
      </div>
    </div>
  );
}
