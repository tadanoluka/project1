"use client";

import styles from "@/app/freights/[freightId]/page.module.css";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { fetcher } from "@/scripts/fetcher/fetcher";
import { freightURI } from "@/apiDomain/apiDomain";
import { IFreight } from "@/interfaces/data/freight/iFreight";
import Spinner1 from "@/app/components/spinners/spinner1";

export default function Page({ params }: { params: { freightId: string } }) {
  const freightEtsng: number = Number(params.freightId);

  const [freight, setFreight] = useState<IFreight>();
  const router = useRouter();

  useEffect(() => {
    fetcher(freightURI(freightEtsng), router.push).then((res) => {
      res.json().then((data) => setFreight(data));
    });
  }, []);

  if (!freight) {
    return (
      <div className={styles.wrapper}>
        <Spinner1 />
      </div>
    );
  }

  return (
    <div className={styles.freightInfo}>
      <div className={styles.title}>Информация о грузе</div>
      <div className={styles.content}>
        <div>ЕТСНГ</div>
        <div>{freight.etsng}</div>
        <div>Наименование</div>
        <div>{freight.name}</div>
        <div>Краткое Наименование</div>
        <div>{freight.shortName}</div>
        <div>Группа</div>
        <div>{freight.groupName}</div>
      </div>
    </div>
  );
}
