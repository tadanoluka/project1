"use client";

import styles from "@/app/tabs/mainTabPane.module.css";
import { JSX, useState } from "react";
import Image from "next/image";
import { useRouter } from "next/navigation";
import classNames from "classnames";
import { removeCredentials } from "@/scripts/auth";
import MainTabPaneHeader from "@/app/tabs/mainTabPaneHeader";
import { MainTabPaneTabs } from "@/app/tabs/mainTabPaneTabs";
import ProfilePopup from "@/app/tabs/profilePopup/profilePopup";
import ImportPopup from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function MainTabPane() {
  const [currentTab, setCurrentTab] = useState<number>(0);
  const router = useRouter();

  const [isProfileOpen, setIsProfileOpen] = useState<boolean>(false);

  function toggleTab(index: number): void {
    setCurrentTab(index);
  }

  function handleExit(): void {
    removeCredentials();
    router.push("/signin");
  }

  function tabHeaders(): JSX.Element {
    const tabHeaders: JSX.Element[] = [];
    for (let i: number = 0; i < MainTabPaneTabs.length; i++) {
      tabHeaders.push(
        <MainTabPaneHeader
          key={i}
          index={i}
          text={MainTabPaneTabs[i].name}
          onClick={() => toggleTab(i)}
          currentTab={currentTab}
        />,
      );
    }
    return <>{tabHeaders}</>;
  }

  function tabContent(): JSX.Element {
    return <>{MainTabPaneTabs[currentTab].tabContent}</>;
  }

  function handleToggleProfilePopup(): void {
    setIsProfileOpen(!isProfileOpen);
  }

  return (
    <>
      <ProfilePopup
        isProfileOpen={isProfileOpen}
        handleToggleProfilePopup={handleToggleProfilePopup}
      />
      <ImportPopup />
      <div
        className={
          isProfileOpen
            ? classNames(styles.main, styles.blurred)
            : classNames(styles.main)
        }
      >
        <div className={styles.header}>
          <div className={styles.logo}>
            <Image src="/logoBlue1.png" width={165} height={45} alt="logo" />
          </div>
          <div className={styles.navBar}>{tabHeaders()}</div>
          <div className={styles.accountBtn} tabIndex={0}>
            <Image
              src="/accountMini.svg"
              width={20}
              height={22}
              alt="account"
            />
            <div className={styles.dropDownContent}>
              <button onMouseDown={handleToggleProfilePopup}>Профиль</button>
              <button onMouseDown={handleExit}>Выйти</button>
            </div>
          </div>
        </div>
        <div className={styles.tabContent}>{tabContent()}</div>
        <div className={styles.footer}></div>
      </div>
    </>
  );
}
