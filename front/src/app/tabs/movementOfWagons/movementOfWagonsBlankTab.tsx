export default function MovementOfWagonsBlankTab({ text }: { text: string }) {
  return (
    <div>
      <p>The requested tab does not exist. {text ? text : ""}</p>
    </div>
  );
}
