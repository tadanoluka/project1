import SecondaryTabPane from "@/app/components/tabPanes/secondaryTabPane/secondaryTabPane";
import { CatalogsTabs } from "@/app/tabs/catalogs/catalogsTabs";

export default function Catalogs() {
  return (
    <SecondaryTabPane
      TABS={CatalogsTabs}
      tabName={"Справочники"}
    ></SecondaryTabPane>
  );
}
