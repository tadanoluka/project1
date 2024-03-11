"use client";

import styles from "@/app/wagons/[wagonId]/page.module.css";
import { useEffect, useRef, useState } from "react";
import { IStation } from "@/interfaces/data/station/iStation";
import { useRouter } from "next/navigation";
import { fetcher } from "@/scripts/fetcher/fetcher";
import { stationURI, wagonURI } from "@/apiDomain/apiDomain";
import Spinner1 from "@/app/components/spinners/spinner1";
import { IWagon } from "@/interfaces/data/wagon/iWagon";
import classNames from "classnames";

export default function Page({ params }: { params: { wagonId: string } }) {
  const wagonId: number = Number(params.wagonId);

  const [wagon, setWagon] = useState<IWagon>();
  const router = useRouter();

  useEffect(() => {
    fetcher(wagonURI(wagonId), router.push).then((res) => {
      res.json().then((data) => setWagon(data));
    });
  }, []);

  function handleOpenStationClick(stationEsr: number) {
    const options = "titlebar=no,statusbar=no,innerHeight=471,innerWidth=700";
    window.open(`../stations/${stationEsr}`, "StationInfo", options);
  }

  function handleOpenFreightClick(freightEtsng: number) {
    const options = "titlebar=no,statusbar=no,innerHeight=471,innerWidth=700";
    window.open(`../freights/${freightEtsng}`, "FreightInfo", options);
  }

  if (!wagon) {
    return (
      <div className={styles.wrapper}>
        <Spinner1 />
      </div>
    );
  }

  return (
    <div className={styles.wagonInfo}>
      <div className={styles.title}>Информация о вагоне</div>
      <div className={styles.content}>
        <div>Номер вагона</div>
        <div>{wagon.id.toString()}</div>
        <div>Станция назначения</div>
        <div
          className={styles.link}
          onClick={() => handleOpenStationClick(wagon.destinationStation.esr)}
        >
          {wagon.destinationStation.name}
          <div className={classNames(styles.tooltip)}>
            <div>ЕСР: {wagon.destinationStation.esr}</div>
          </div>
        </div>
        <div>Станция операции</div>
        <div
          className={styles.link}
          onClick={() => handleOpenStationClick(wagon.operationStation.esr)}
        >
          {wagon.operationStation.name}
          <div className={classNames(styles.tooltip)}>
            <div>ЕСР: {wagon.operationStation.esr}</div>
          </div>
        </div>
        <div>Груз</div>
        <div
          className={styles.link}
          onClick={() => handleOpenFreightClick(wagon.freight.etsng)}
        >
          {wagon.freight.name}
        </div>
        <div>Грузополучатель</div>
        <div>{wagon.consignee}</div>
        <div>Вес</div>
        <div>{wagon.weight}</div>
        <div>Состояние</div>
        <div>{wagon.wagonStatus.name}</div>
      </div>
    </div>
  );
}
