"use client";

import styles from "./importPopup.module.css";
import { signal } from "@preact/signals-core";
import { useSignalState } from "@/scripts/useSignalState";
import React, { useEffect, useReducer, useRef, useState } from "react";
import { getCredentials } from "@/scripts/auth";
import { stationsAllForUserURI, wagonsPageURI } from "@/apiDomain/apiDomain";
import FileInfo from "@/app/tabs/movementOfWagons/approach/importPopup/fileInfo/fileInfo";
import { fetcher } from "@/scripts/fetcher/fetcher";
import { useRouter } from "next/navigation";
import SearchableSelectField, {
  TSearchableSelectFieldOption,
} from "@/app/components/inputs/searchableSelectField/searchableSelectField";
import ImportPopupButtons from "@/app/tabs/movementOfWagons/approach/importPopup/buttons/importPopupButtons";
import { FILE_STATUS } from "@/app/tabs/movementOfWagons/approach/importPopup/FILE_STATUS";
import { IStation } from "@/interfaces/data/station/iStation";
import Spinner1 from "@/app/components/spinners/spinner1";

export const isApproachTabImportPopupOpen = signal(false);

export type TFileInfo = {
  file: File;
  status: FILE_STATUS;
  uploadPercent: number;
};

export enum FILE_INFO_ACTION_TYPE {
  REMOVE_ALL,
  ADD_FILE_INFO,
  SET_STATUS,
  UPDATE_LOAD_PERCENT,
}

export type TFileInfoAction =
  | {
      type: FILE_INFO_ACTION_TYPE.REMOVE_ALL;
    }
  | {
      type: FILE_INFO_ACTION_TYPE.ADD_FILE_INFO;
      payload: TAddFileInfoPayload;
    }
  | {
      type: FILE_INFO_ACTION_TYPE.SET_STATUS;
      payload: TSetStatusPayload;
    }
  | {
      type: FILE_INFO_ACTION_TYPE.UPDATE_LOAD_PERCENT;
      payload: TUpdateLoadPercentPayload;
    };

type TAddFileInfoPayload = {
  file: File;
};

type TSetStatusPayload = {
  file: File;
  status: FILE_STATUS;
};

type TUpdateLoadPercentPayload = {
  file: File;
  uploadPercent: number;
};

function reducer(fileInfos: TFileInfo[], action: TFileInfoAction) {
  switch (action.type) {
    case FILE_INFO_ACTION_TYPE.REMOVE_ALL: {
      return [];
    }
    case FILE_INFO_ACTION_TYPE.ADD_FILE_INFO:
      if (action.payload.file) {
        return [...fileInfos, newFileInfoObject(action.payload.file)];
      }
      return fileInfos;
    case FILE_INFO_ACTION_TYPE.SET_STATUS:
      if (action.payload.file && action.payload.status) {
        return fileInfos.map((fileInfo) => {
          if (fileInfo.file === action.payload.file) {
            return { ...fileInfo, status: action.payload.status };
          }
          return fileInfo;
        });
      }
      return fileInfos;
    case FILE_INFO_ACTION_TYPE.UPDATE_LOAD_PERCENT:
      if (action.payload.file && action.payload.uploadPercent) {
        return fileInfos.map((fileInfo) => {
          if (fileInfo.file === action.payload.file) {
            return { ...fileInfo, uploadPercent: action.payload.uploadPercent };
          }
          return fileInfo;
        });
      }
      return fileInfos;
    default:
      throw new Error("No action type");
  }
}

function newFileInfoObject(file: File) {
  return {
    file: file,
    status: FILE_STATUS.SELECTED,
    uploadPercent: 0,
  };
}

export default function ImportPopup() {
  const importPopUpRef = useRef<HTMLDivElement>(null);
  const importContentRef = useRef<HTMLDivElement>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);
  const formRef = useRef<HTMLFormElement>(null);
  const isOpen = useSignalState(isApproachTabImportPopupOpen);
  const [isDrag, setIsDrag] = useState(false);

  const [isAllLoaded, setIsAllLoaded] = useState(false);
  const [chosenOperationStation, setChosenOperationStation] =
    useState<string>("");
  const [highlightStationSelect, setHighlightStationSelect] = useState(false);
  const [highlightFileSelect, setHighlightFileSelect] = useState(false);

  const [fileInfos, dispatch] = useReducer(reducer, []);

  const [options, setOptions] = useState<TSearchableSelectFieldOption[]>();
  const router = useRouter();
  useEffect(() => {
    fetcher(stationsAllForUserURI, router.push).then((response) => {
      if (response.status === 200) {
        response
          .json()
          .then((data: IStation[]) => convertStationToOption(data));
      }
    });
  }, []);

  function convertStationToOption(data: IStation[]) {
    const options: TSearchableSelectFieldOption[] = data.map(
      (station): TSearchableSelectFieldOption => {
        return {
          id: station.esr,
          name: station.name,
        };
      },
    );
    setOptions(options);
  }

  function addFile(file: File) {
    if (!!file) {
      dispatch({
        type: FILE_INFO_ACTION_TYPE.ADD_FILE_INFO,
        payload: {
          file: file,
        },
      });
    }
  }

  function clearFiles() {
    setChosenOperationStation("");
    setHighlightStationSelect(false);
    setHighlightFileSelect(false);
    dispatch({
      type: FILE_INFO_ACTION_TYPE.REMOVE_ALL,
    });
  }

  function handleClick() {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  }

  function handleDrop(event: React.DragEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsDrag(false);

    if (event.dataTransfer.items) {
      // Use DataTransferItemList interface to access the file(s)
      Array.from(event.dataTransfer.items).forEach((item) => {
        if (item.kind === "file") {
          const file = item.getAsFile();
          if (file) {
            addFile(file);
          }
        }
      });
    } else {
      // Use DataTransfer interface to access the file(s)
      Array.from(event.dataTransfer.files).forEach((file) => {
        addFile(file);
      });
    }
  }

  function handleDragOver(event: React.DragEvent) {
    event.preventDefault();
    setIsDrag(true);
  }

  function handleDragLeave(event: React.DragEvent) {
    event.preventDefault();
    setIsDrag(false);
  }

  function handleFileSelect(event: React.ChangeEvent<HTMLInputElement>) {
    if (event.target.files) {
      Array.from(event.target.files).forEach((file) => addFile(file));
    }
  }

  function setFileLoading(fileInfo: TFileInfo) {
    dispatch({
      type: FILE_INFO_ACTION_TYPE.SET_STATUS,
      payload: {
        file: fileInfo.file,
        status: FILE_STATUS.LOADING,
      },
    });
  }

  function setFileLoadingPercent(fileInfo: TFileInfo, value: number) {
    dispatch({
      type: FILE_INFO_ACTION_TYPE.UPDATE_LOAD_PERCENT,
      payload: {
        file: fileInfo.file,
        uploadPercent: value,
      },
    });
  }

  function setFileLoaded(fileInfo: TFileInfo) {
    dispatch({
      type: FILE_INFO_ACTION_TYPE.SET_STATUS,
      payload: {
        file: fileInfo.file,
        status: FILE_STATUS.LOADED,
      },
    });
  }

  function setFileSuccessful(fileInfo: TFileInfo) {
    dispatch({
      type: FILE_INFO_ACTION_TYPE.SET_STATUS,
      payload: {
        file: fileInfo.file,
        status: FILE_STATUS.SUCCESSFUL,
      },
    });
  }

  function setFileNotValid(fileInfo: TFileInfo) {
    dispatch({
      type: FILE_INFO_ACTION_TYPE.SET_STATUS,
      payload: {
        file: fileInfo.file,
        status: FILE_STATUS.NOT_VALID,
      },
    });
  }

  function uploadFiles() {
    if (!chosenOperationStation) {
      throw Error("NO OPERATION STATION");
    }
    for (let fileInfo of fileInfos) {
      const formData = new FormData();
      formData.append("file", fileInfo.file);
      formData.append("operationStationEsr", chosenOperationStation);
      console.log(chosenOperationStation);
      const xmlHttpRequest = new XMLHttpRequest();
      xmlHttpRequest.onreadystatechange = function () {
        if (xmlHttpRequest.readyState === XMLHttpRequest.DONE) {
          console.log(xmlHttpRequest.status);
          switch (xmlHttpRequest.status) {
            case 200:
              setFileSuccessful(fileInfo);
              return;
            case 204:
              // TODO Файл пустой
              return;
            case 400:
              setFileNotValid(fileInfo);
              return;
            case 413:
              // TODO Файл слишком большой
              return;
          }
        }
      };
      xmlHttpRequest.upload.addEventListener("loadstart", () =>
        setFileLoading(fileInfo),
      );
      xmlHttpRequest.upload.addEventListener("loadend", () =>
        setFileLoaded(fileInfo),
      );
      xmlHttpRequest.upload.addEventListener("progress", (event) =>
        setFileLoadingPercent(
          fileInfo,
          Math.round((event.loaded / event.total) * 1000) / 10,
        ),
      );
      xmlHttpRequest.open("PATCH", wagonsPageURI);
      xmlHttpRequest.setRequestHeader(
        "Authorization",
        `Basic ${getCredentials()}`,
      );
      xmlHttpRequest.send(formData);
    }
  }

  const fileInfoComponents = () => {
    if (fileInfos) {
      return fileInfos.map((filesInfo, i) => {
        if (filesInfo) {
          return <FileInfo key={i} fileInfoObject={filesInfo} />;
        }
      });
    }
  };

  function getContent() {
    if (!options) {
      return <Spinner1 />;
    } else {
      return (
        <>
          <div className={styles.title}>
            {isAllLoaded
              ? "Файлы успешно загружен"
              : "Выбор файлов для справки"}
          </div>
          <div className={styles.selectStationBlock}>
            <span>Выберите станцию операции</span>
            <span>
              <SearchableSelectField
                label={"Станция операции"}
                options={options}
                isHighlighted={highlightStationSelect}
                setIsHighlighted={setHighlightStationSelect}
                value={chosenOperationStation}
                setValue={setChosenOperationStation}
              />
            </span>
          </div>

          <div className={styles.content} ref={importContentRef}>
            <form
              style={{
                borderWidth: isDrag ? "3px" : "1px",
                borderStyle: isDrag ? "dashed" : "solid",
                borderColor: highlightFileSelect ? "red" : "#C4C4C4",
              }}
              className={
                fileInfos.length !== 0 ? styles.dragFormHidden : styles.dragForm
              }
              onClick={handleClick}
              onDrop={handleDrop}
              onDragOver={handleDragOver}
              onDragStart={handleDragOver}
              onDragLeave={handleDragLeave}
              encType="multipart/form-data"
              method="post"
              ref={formRef}
            >
              {isDrag ? (
                <>Отпустите файл в данной области</>
              ) : (
                <>
                  Нажмите на данную область, чтобы выбрать файл <br />
                  или перетащите файл в данную область <br />и нажмите кнопку
                  загрузить.
                </>
              )}
              <input
                type="file"
                name="file"
                multiple
                // accept=".txt"
                ref={fileInputRef}
                hidden={true}
                onChange={handleFileSelect}
              />
            </form>
            {fileInfoComponents()}
          </div>
          <ImportPopupButtons
            isAllLoaded={isAllLoaded}
            fileInfos={fileInfos}
            clearFiles={clearFiles}
            uploadFiles={uploadFiles}
            chosenOperationStation={chosenOperationStation}
            setHighlightStationSelect={setHighlightStationSelect}
            setHighlightFileSelect={setHighlightFileSelect}
          />
        </>
      );
    }
  }

  return (
    <div
      style={isOpen ? { display: "flex" } : { display: "none" }}
      className={styles.importBack}
    >
      <div
        style={isOpen ? { display: "flex" } : { display: "none" }}
        className={styles.importPopup}
        ref={importPopUpRef}
      >
        {getContent()}
      </div>
    </div>
  );
}
