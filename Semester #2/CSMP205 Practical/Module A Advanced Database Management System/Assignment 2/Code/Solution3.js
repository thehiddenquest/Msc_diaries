const multiply = (callback, num1, num2) => {
    const result = num1 * num2;
    callback(result);
  };
  
  // Example usage:
  const printResult = (result) => {
    console.log("The result is:", result);
  };
  
  multiply(printResult, 3, 4); 