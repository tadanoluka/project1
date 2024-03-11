import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";
import CancelButton from "@/app/tabs/movementOfWagons/approach/importPopup/buttons/cancelButton";
import CloseButton from "@/app/tabs/movementOfWagons/approach/importPopup/buttons/closeButton";
import LoadButton from "@/app/tabs/movementOfWagons/approach/importPopup/buttons/loadButton";
import { TFileInfo } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function ImportPopupButtons({
  isAllLoaded,
  fileInfos,
  clearFiles,
  uploadFiles,
  chosenOperationStation,
  setHighlightStationSelect,
  setHighlightFileSelect,
}: {
  isAllLoaded: boolean;
  fileInfos: TFileInfo[];
  clearFiles: () => void;
  uploadFiles: () => void;
  chosenOperationStation: string;
  setHighlightStationSelect: (value: boolean) => void;
  setHighlightFileSelect: (value: boolean) => void;
}) {
  const getCloseCancelButton = () => {
    if (!isAllLoaded && fileInfos.length !== 0) {
      return <CancelButton clearFiles={clearFiles} />;
    } else {
      return <CloseButton clearFiles={clearFiles} />;
    }
  };

  return (
    <div className={styles.btns}>
      <LoadButton
        fileInfos={fileInfos}
        uploadFiles={uploadFiles}
        chosenOperationStation={chosenOperationStation}
        setHighlightStationSelect={setHighlightStationSelect}
        setHighlightFileSelect={setHighlightFileSelect}
      />
      {getCloseCancelButton()}
    </div>
  );
}
