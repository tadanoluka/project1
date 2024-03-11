export function getValue(obj, stringPath) {
  if (!stringPath) {
    return "No accessor";
  }
  const pathArray = stringPath.split(".");
  for (let i = 0, n = pathArray.length; i < n; ++i) {
    const key = pathArray[i];
    if (key in obj) {
      obj = obj[key];
    } else {
      return "Bad accessor";
    }
  }
  return obj;
}
