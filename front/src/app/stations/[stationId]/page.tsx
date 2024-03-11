"use client";

import styles from "./page.module.css";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { fetcher } from "@/scripts/fetcher/fetcher";
import { stationURI } from "@/apiDomain/apiDomain";
import Spinner1 from "../../components/spinners/spinner1";
import { IStation } from "@/interfaces/data/station/iStation";

export default function Page({ params }: { params: { stationId: string } }) {
  const stationId: number = Number(params.stationId);

  const [station, setStation] = useState<IStation>();
  const router = useRouter();
  const [isEditing, setIsEditing] = useState(false);
  useEffect(() => {
    fetcher(stationURI(stationId), router.push).then((res) => {
      res.json().then((data) => setStation(data));
    });
  }, [params.stationId]);

  function handleAllowEditing() {
    setIsEditing(true);
  }

  function handleCancelEditing() {
    setIsEditing(false);
  }

  if (!station) {
    return (
      <div className={styles.wrapper}>
        <Spinner1 />
      </div>
    );
  }

  if (!isEditing) {
    return (
      <div className={styles.stationInfo}>
        <div className={styles.title}>Информация о станции</div>
        <div className={styles.content}>
          <div>ЕСР номер</div>
          <div>{station.esr}</div>
          <div>Название</div>
          <div>{station.name}</div>
          <div>Короткое название</div>
          <div>{station.shortName}</div>
        </div>
        <div className={styles.btns}>
          <button className={styles.btnStyle1} disabled={!isEditing}>
            Применить
          </button>
          <button className={styles.btnStyle1} onClick={handleAllowEditing}>
            Изменить
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.stationInfo}>
      <div className={styles.title}>Информация о станции</div>
      <div className={styles.content}>
        <div>ЕСР номер</div>
        <div>{station.esr}</div>
        <div>Название</div>
        <input value={station.name.toString()} />
        <div>Короткое название</div>
        <input value={station.shortName.toString()} />
      </div>
      <div className={styles.btns}>
        <button className={styles.btnStyle1} disabled={!isEditing}>
          Применить
        </button>
        <button className={styles.btnStyle2} onClick={handleCancelEditing}>
          Отмена
        </button>
      </div>
    </div>
  );
}
