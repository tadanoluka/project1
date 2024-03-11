import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";
import Image from "next/image";
import { TFileInfo } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function LoadingFileInfo({
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
          <div
            className={styles.uploadPercent}
          >{`${fileInfoObject.uploadPercent}%`}</div>
        </div>
        <div className={styles.progress}>
          <div
            className={styles.progressBar}
            style={{ width: `${Math.floor(fileInfoObject.uploadPercent)}%` }}
          ></div>
        </div>
      </div>
    </div>
  );
}
