import styles from "./mainTabPane.module.css";
import classNames from "classnames";

export default function MainTabPaneHeader({
  index,
  text,
  onClick,
  currentTab,
}: {
  index: number;
  text: string;
  onClick: () => void;
  currentTab: number;
}) {
  return (
    <div
      className={
        currentTab === index
          ? classNames(styles.tabLabel, styles.activeTabLabel)
          : styles.tabLabel
      }
      onClick={onClick}
    >
      {text}
    </div>
  );
}
