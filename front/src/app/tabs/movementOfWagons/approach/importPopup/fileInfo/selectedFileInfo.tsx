import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";
import Image from "next/image";
import { TFileInfo } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function SelectedFileInfo({
  fileInfoObject,
}: {
  fileInfoObject: TFileInfo;
}) {
  return (
    <div className={styles.chosenFile}>
      <Image
        className={styles.fileIcon}
        src="./fileIcon.svg"
        width={60}
        height={60}
        alt="fileIcon"
      />
      <div className={styles.fileLoadInfo}>
        <div className={styles.fileInfoRow}>
          <div className={styles.fileName}>{fileInfoObject.file.name}</div>
        </div>
      </div>
    </div>
  );
}
