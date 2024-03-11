import styles from "./spinner1.module.css";
import { tColor } from "@/interfaces/utils/tColor";

export default function Spinner1({
  color,
  nowrap,
}: {
  color?: tColor;
  nowrap?: boolean;
}) {
  return (
    <div className={nowrap ? "" : styles.ldsRingWrapper}>
      <div className={styles.innerWrapper}>
        <div className={styles.ldsRing}>
          <div
            style={{
              borderColor: `${
                color ? color : "#5A5A5A"
              } transparent transparent transparent`,
            }}
          ></div>
          <div
            style={{
              borderColor: `${
                color ? color : "#5A5A5A"
              } transparent transparent transparent`,
            }}
          ></div>
          <div
            style={{
              borderColor: `${
                color ? color : "#5A5A5A"
              } transparent transparent transparent`,
            }}
          ></div>
          <div
            style={{
              borderColor: `${
                color ? color : "#5A5A5A"
              } transparent transparent transparent`,
            }}
          ></div>
        </div>
      </div>
    </div>
  );
}
