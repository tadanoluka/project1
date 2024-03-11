import styles from "@/app/tabs/movementOfWagons/movementOfWagons.module.css";
import { isApproachTabImportPopupOpen } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";
import Image from "next/image";
import { JSX } from "react";
import { MovementOfWagonsTabs } from "@/app/tabs/movementOfWagons/movementOfWagonsTabs";
import SecondaryTabPane from "@/app/components/tabPanes/secondaryTabPane/secondaryTabPane";

export default function MovementOfWagons(): JSX.Element {
  return (
    <SecondaryTabPane TABS={MovementOfWagonsTabs} tabName={"Движение вагонов"}>
      <div className={styles.buttonsBlock}>
        <div
          className={styles.button}
          onClick={() => {
            isApproachTabImportPopupOpen.value =
              !isApproachTabImportPopupOpen.value;
          }}
        >
          <div className={styles.textIcon}>ГВЦ</div>
          <div>Импорт справок</div>
        </div>
        <div className={styles.button}>
          <div className={styles.textIcon}>XLS</div>
          <div>Экспорт в Excel</div>
        </div>
        <div className={styles.outlinedButton}>
          <Image src="/printerIcon.svg" alt="" width={23} height={21} />
          <div>Печать</div>
        </div>
      </div>
    </SecondaryTabPane>
  );
}
