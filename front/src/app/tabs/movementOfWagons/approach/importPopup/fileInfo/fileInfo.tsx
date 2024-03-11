import SelectedFileInfo from "@/app/tabs/movementOfWagons/approach/importPopup/fileInfo/selectedFileInfo";
import LoadingFileInfo from "@/app/tabs/movementOfWagons/approach/importPopup/fileInfo/loadingFileInfo";
import LoadedFileInfo from "@/app/tabs/movementOfWagons/approach/importPopup/fileInfo/loadedFileInfo";
import { FILE_STATUS } from "@/app/tabs/movementOfWagons/approach/importPopup/FILE_STATUS";
import { TFileInfo } from "@/app/tabs/movementOfWagons/approach/importPopup/importPopup";

export default function FileInfo({
  fileInfoObject,
}: {
  fileInfoObject: TFileInfo;
}) {
  switch (fileInfoObject.status) {
    case FILE_STATUS.SELECTED:
      return <SelectedFileInfo fileInfoObject={fileInfoObject} />;
    case FILE_STATUS.LOADING:
      return <LoadingFileInfo fileInfoObject={fileInfoObject} />;
    case FILE_STATUS.LOADED:
    case FILE_STATUS.NOT_VALID:
    case FILE_STATUS.SUCCESSFUL:
      return <LoadedFileInfo fileInfoObject={fileInfoObject} />;
    default:
      return <div>{fileInfoObject.file.name}</div>;
  }
}
