import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";
import { TFileInfo } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function LoadButton({
  fileInfos,
  uploadFiles,
  chosenOperationStation,
  setHighlightStationSelect,
  setHighlightFileSelect,
}: {
  fileInfos: TFileInfo[];
  uploadFiles: () => void;
  chosenOperationStation: string;
  setHighlightStationSelect: (value: boolean) => void;
  setHighlightFileSelect: (value: boolean) => void;
}) {
  function highlightRequiredFields() {
    if (!chosenOperationStation) {
      setHighlightStationSelect(true);
    } else {
      setHighlightStationSelect(false);
    }
    if (fileInfos.length === 0) {
      setHighlightFileSelect(true);
    } else {
      setHighlightFileSelect(false);
    }
  }

  function startUpload() {
    setHighlightStationSelect(false);
    uploadFiles();
  }

  return (
    <button
      className={styles.btn2}
      onClick={
        !chosenOperationStation || fileInfos.length === 0
          ? highlightRequiredFields
          : startUpload
      }
    >
      ЗАГРУЗИТЬ
    </button>
  );
}
