export type TJSONValue =
  | string
  | number
  | boolean
  | bigint
  | { [x: string]: string | number | boolean | bigint }
  | Array<TJSONValue>;
