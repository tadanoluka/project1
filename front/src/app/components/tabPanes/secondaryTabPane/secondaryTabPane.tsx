import { JSX, useState } from "react";
import styles from "@/app/components/tabPanes/secondaryTabPane/secondaryTabPane.module.css";
import SecondaryTabPaneHeader from "@/app/components/tabPanes/secondaryTabPane/secondaryTabPaneHeader";
import { ISecondaryTabPaneTab } from "@/interfaces/components/secondaryTabPane/iSecondaryTabPaneTab";

export default function SecondaryTabPane({
  children,
  TABS,
  tabName,
}: {
  children?: JSX.Element;
  TABS: ISecondaryTabPaneTab[];
  tabName: string;
}): JSX.Element {
  const [currentTab, setCurrentTab] = useState<number>(0);

  function toggleTab(index: number): void {
    setCurrentTab(index);
  }

  function tabHeaders(): JSX.Element {
    const tabHeaders: JSX.Element[] = [];
    for (let i = 0; i < TABS.length; i++) {
      tabHeaders.push(
        <SecondaryTabPaneHeader
          key={i}
          index={i}
          text={TABS[i].name}
          onClick={() => toggleTab(i)}
          currentMiniTab={currentTab}
        />,
      );
    }
    return <>{tabHeaders}</>;
  }

  function tabContent(): JSX.Element {
    return <>{TABS[currentTab].tabContent}</>;
  }

  return (
    <>
      <div className={styles.header}>
        <div className={styles.tabName}>{tabName}</div>
        {children}
      </div>
      <div className={styles.navBar}>{tabHeaders()}</div>
      <div className={styles.tabContent}>{tabContent()}</div>
      <div className={styles.footer}></div>
    </>
  );
}
