import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";
import { isApproachTabImportPopupOpen } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function CloseButton({
  clearFiles,
}: {
  clearFiles: () => void;
}) {
  function handleClose() {
    isApproachTabImportPopupOpen.value = false;
    clearFiles();
  }

  return (
    <button className={styles.btn1} onClick={handleClose}>
      ЗАКРЫТЬ
    </button>
  );
}
