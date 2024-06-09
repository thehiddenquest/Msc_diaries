const swapKeysAndValues = (obj) =>
  Object.fromEntries(Object.entries(obj).map(([key, value]) => [value, key]));

// Example usage:
const input = { "N": "M", "w": "This is String", "c": 4};
const output = swapKeysAndValues(input);
console.log(output); 
