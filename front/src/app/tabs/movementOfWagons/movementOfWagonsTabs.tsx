import ApproachTab from "@/app/tabs/movementOfWagons/approach/approachTab";
import MovementOfWagonsBlankTab from "@/app/tabs/movementOfWagons/movementOfWagonsBlankTab";
import { ISecondaryTabPaneTab } from "@/interfaces/components/secondaryTabPane/iSecondaryTabPaneTab";

export const MovementOfWagonsTabs: ISecondaryTabPaneTab[] = [
  {
    name: "Подход",
    tabContent: <ApproachTab />,
  },
  {
    name: "Поступление",
    tabContent: <MovementOfWagonsBlankTab text={"1"} />,
  },
  {
    name: "Уведомление о прибытии",
    tabContent: <MovementOfWagonsBlankTab text={"2"} />,
  },
  {
    name: "Парк",
    tabContent: <MovementOfWagonsBlankTab text={"3"} />,
  },
  {
    name: "Маневровые натурные листы",
    tabContent: <MovementOfWagonsBlankTab text={"4"} />,
  },
  {
    name: "Грузовые операции",
    tabContent: <MovementOfWagonsBlankTab text={"5"} />,
  },
  {
    name: "Отправление",
    tabContent: <MovementOfWagonsBlankTab text={"6"} />,
  },
];
