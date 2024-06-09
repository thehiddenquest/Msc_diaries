const numbers = [120, 209, 230, 340, 550];

// Task 1: Double each number in the array using an arrow function
const doubledNumbers = numbers.map(num => num * 2);

// Task 2: Log each doubled number along with its index using template literals
doubledNumbers.forEach((doubledValue, index) => {
  console.log(`The doubled value of number at index ${index} is ${doubledValue}.`);
});