import styles from "./paginationButtons.module.css";
import classNames from "classnames";

export default function PaginationButtons({
  currentPage,
  totalPages,
  setPageFunc,
}: {
  currentPage: number;
  totalPages: number;
  setPageFunc: (pageIndex: number) => void;
}) {
  const pageButtons = () => {
    const startIndex = Math.max(currentPage - 2, 0);
    const endIndex = Math.min(currentPage + 2, totalPages - 1);
    const buttons = [];
    for (let i = startIndex; i <= endIndex; i++) {
      buttons.push(
        <button
          key={i}
          onClick={() => setPageFunc(i)}
          className={
            currentPage === i
              ? classNames(styles.active)
              : classNames(styles.button)
          }
          disabled={currentPage === i}
        >
          {i + 1}
        </button>,
      );
    }
    return <>{buttons}</>;
  };

  function prevPage() {
    const newPageIndex = currentPage - 1;
    if (newPageIndex < 0) {
      setPageFunc(0);
    } else {
      setPageFunc(newPageIndex);
    }
  }

  function nextPage() {
    const newPageIndex = currentPage + 1;
    if (newPageIndex > totalPages - 1) {
      setPageFunc(totalPages - 1);
    } else {
      setPageFunc(newPageIndex);
    }
  }

  return (
    <>
      <button
        className={currentPage !== 0 ? styles.button : styles.disabled}
        onClick={() => setPageFunc(0)}
        disabled={currentPage === 0}
      >
        {"<<"}
      </button>
      <button
        className={currentPage !== 0 ? styles.button : styles.disabled}
        onClick={prevPage}
        disabled={currentPage === 0}
      >
        {"<"}
      </button>
      {pageButtons()}
      <button
        className={
          currentPage !== totalPages - 1 ? styles.button : styles.disabled
        }
        onClick={nextPage}
        disabled={currentPage === totalPages - 1}
      >
        {">"}
      </button>
      <button
        className={
          currentPage !== totalPages - 1 ? styles.button : styles.disabled
        }
        onClick={() => setPageFunc(totalPages - 1)}
        disabled={currentPage === totalPages - 1}
      >
        {">>"}
      </button>
    </>
  );
}
