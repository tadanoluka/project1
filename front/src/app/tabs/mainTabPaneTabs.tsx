import MainBlankTab from "@/app/tabs/mainBlankTab";
import MovementOfWagons from "@/app/tabs/movementOfWagons/movementOfWagons";
import Catalogs from "@/app/tabs/catalogs/catalogs";
import { IMainTabPaneTab } from "@/interfaces/components/mainTabPane/iMainTabPaneTab";

export const MainTabPaneTabs: IMainTabPaneTab[] = [
  {
    name: "Движение вагонов",
    tabContent: <MovementOfWagons />,
  },
  {
    name: "Товарный кассир",
    tabContent: <MainBlankTab text={"1"} />,
  },
  {
    name: "Перевеска",
    tabContent: <MainBlankTab text={"2"} />,
  },
  {
    name: "Состояние и дислокация вагонов",
    tabContent: <MainBlankTab text={"3"} />,
  },
  {
    name: "Оборот ЗПУ",
    tabContent: <MainBlankTab text={"4"} />,
  },
  {
    name: "Справочники",
    tabContent: <Catalogs />,
  },
  {
    name: "Отчеты",
    tabContent: <MainBlankTab text={"6"} />,
  },
];
