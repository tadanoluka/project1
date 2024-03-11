import classNames from "classnames";
import styles from "@/app/components/tabPanes/secondaryTabPane/secondaryTabPaneHeader.module.css";

export default function SecondaryTabPaneHeader({
  text,
  currentMiniTab,
  index,
  onClick,
}: {
  text: string;
  currentMiniTab: number;
  index: number;
  onClick: () => void;
}) {
  return (
    <div
      className={
        currentMiniTab === index
          ? classNames(styles.miniTabLabel, styles.activeMiniTabLabel)
          : styles.miniTabLabel
      }
      onClick={onClick}
    >
      {text}
    </div>
  );
}
