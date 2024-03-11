import FreightCatalogTab from "@/app/tabs/catalogs/freightCatalog/freightCatalogTab";
import StationCatalogTab from "@/app/tabs/catalogs/stationCatalog/stationCatalogTab";
import { ISecondaryTabPaneTab } from "@/interfaces/components/secondaryTabPane/iSecondaryTabPaneTab";

export const CatalogsTabs: ISecondaryTabPaneTab[] = [
  {
    name: "Станции",
    tabContent: <StationCatalogTab />,
  },
  {
    name: "Грузы",
    tabContent: <FreightCatalogTab />,
  },
];
