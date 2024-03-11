import styles from "./profilePopup.module.css";
import { fetcher } from "@/scripts/fetcher/fetcher";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { userURI } from "@/apiDomain/apiDomain";
import { IUser } from "@/interfaces/data/user/iUser";
import Spinner1 from "@/app/components/spinners/spinner1";

export default function ProfilePopup({
  isProfileOpen,
  handleToggleProfilePopup,
}: {
  isProfileOpen: boolean;
  handleToggleProfilePopup: () => void;
}) {
  const [userData, setUserData] = useState<IUser>();
  const { push } = useRouter();

  useEffect(() => {
    fetcher(userURI, push).then((response) => {
      if (response.status === 200) {
        response.json().then((data: IUser) => setUserData(data));
      }
    });
  }, []);

  function getUsername(): string {
    if (userData) {
      return userData.username;
    } else {
      return "";
    }
  }
  function getUserFullName(): string {
    if (userData) {
      return userData.firstname + " " + userData.lastname;
    } else {
      return "";
    }
  }

  function getUserRole(): string {
    if (userData) {
      return userData.role.name;
    } else {
      return "";
    }
  }

  if (userData) {
    return (
      <div
        style={isProfileOpen ? { display: "flex" } : { display: "none" }}
        className={styles.profileBack}
      >
        <div
          style={isProfileOpen ? { display: "flex" } : { display: "none" }}
          className={styles.profilePopup}
        >
          <div className={styles.title}>Информация</div>
          <div className={styles.info}>
            <div>Имя: {getUserFullName()}</div>
            <div>Логин: {getUsername()}</div>
            <div>Роль: {getUserRole()}</div>
          </div>
          <button className={styles.btn} onClick={handleToggleProfilePopup}>
            Закрыть
          </button>
        </div>
      </div>
    );
  } else {
    return (
      <div className={styles.profilePopup}>
        <Spinner1 />
      </div>
    );
  }
}
