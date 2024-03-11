import styles from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup.module.css";

export default function CancelButton({
  clearFiles,
}: {
  clearFiles: () => void;
}) {
  function handleCancel() {
    clearFiles();
  }

  return (
    <button className={styles.btn1} onClick={handleCancel}>
      OТМЕНА
    </button>
  );
}
