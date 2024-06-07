const swapKeysAndValues = (obj) =>
  Object.fromEntries(Object.entries(obj).map(([key, value]) => [value, key]));

// Example usage:
const input = { "a": 1, "b": 2, "c": 3, "c": 4 , "c": 1};
const output = swapKeysAndValues(input);
console.log(output); // Output: { 1: "a", 2: "b", 3: "c" }
