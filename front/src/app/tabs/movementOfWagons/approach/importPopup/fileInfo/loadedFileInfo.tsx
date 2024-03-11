import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";
import Image from "next/image";
import Spinner1 from "@/app/components/spinners/spinner1";
import { FILE_STATUS } from "@/app/tabs/movementOfWagons/approach/importPopup/FILE_STATUS";
import { TFileInfo } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function LoadedFileInfo({
  fileInfoObject,
}: {
  fileInfoObject: TFileInfo;
}) {
  function getIcon() {
    switch (fileInfoObject.status) {
      case FILE_STATUS.LOADED:
        return <Spinner1 nowrap={true} />;
      case FILE_STATUS.NOT_VALID:
        return (
          <Image
            src="./unsuccessIcon.svg"
            alt="unsuccess"
            width={50}
            height={50}
          />
        );
      case FILE_STATUS.SUCCESSFUL:
        return (
          <Image src="./successIcon.svg" alt="success" width={50} height={50} />
        );
    }
  }

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
        <div
          className={styles.fileSize}
        >{`${fileInfoObject.file.size.toLocaleString()} Б`}</div>
      </div>
      {getIcon()}
    </div>
  );
}
