"use client";

import React, { useEffect, useState } from "react";
import styles from "./signin.module.css";
import { useRouter } from "next/navigation";
import { getCredentials, setCredentials } from "@/scripts/auth";
import Spinner1 from "@/app/components/spinners/spinner1";
import { apiURI, signinURI } from "@/apiDomain/apiDomain";
import OutlinedTextField from "@/app/components/inputs/textFields/outlined/outlinedTextField";

export default function Page() {
  const [isCheckingAuth, setIsCheckingAuth] = useState(true);

  const { push } = useRouter();

  useEffect(() => {
    async function checkingIfAuthorized() {
      const credentials = getCredentials();
      if (credentials) {
        const res = await fetch(apiURI, {
          headers: {
            Authorization: `Basic ${credentials}`,
          },
        });
        if (res.status !== 401) {
          push("/");
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    checkingIfAuthorized().then((bool) => setIsCheckingAuth(bool));
  }, []);

  type TLoginForm = {
    username: string;
    password: string;
    [index: string]: string;
  };

  const initState: TLoginForm = {
    username: "",
    password: "",
  };

  const [state, setState] = useState<TLoginForm>(initState);

  function fill(event: React.ChangeEvent<HTMLInputElement>) {
    const copy = { ...state };
    copy[event.target.name] = event.target.value;
    setState(copy);
  }

  async function handle() {
    const res = await fetch(signinURI, {
      method: "POST",
      body: JSON.stringify(state),
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (res.ok) {
      setCredentials(state.username, state.password);
      push("/");
    } else if (res.status === 403) {
      alert("Неправильный логин или пароль");
      setState(initState);
    }
  }

  if (isCheckingAuth) {
    return (
      <div className={styles.main}>
        <Spinner1 color={"#FFFFFF"} />
      </div>
    );
  } else {
    return (
      <div className={styles.main}>
        <div className={styles.signInForm}>
          <div className={styles.companyLabel}>СРПВ</div>
          <div className={styles.greeting}>
            <h2>Добро пожаловать</h2>
            <p>Войдите в свой аккаунт</p>
          </div>
          <OutlinedTextField
            className={styles.loginField}
            label={"Логин"}
            type="text"
            prompt={"Введите логин"}
            name="username"
            value={state.username}
            onChange={fill}
          />
          <OutlinedTextField
            className={styles.passwordField}
            label={"Пароль"}
            type={"password"}
            prompt={"Введите пароль"}
            name="password"
            value={state.password}
            onChange={fill}
          />
          <button className={styles.signInBtn} type="submit" onClick={handle}>
            Войти
          </button>
        </div>
      </div>
    );
  }
}
