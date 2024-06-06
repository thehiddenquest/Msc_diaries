const readline = require('readline');

// Create an interface to read input from the user
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Function to find the mode
const findMode = (arr) => {
    const frequency = {};
    let maxFrequency = 0;
    let mode = null;

    arr.forEach(num => {
        frequency[num] = (frequency[num] || 0) + 1;
        if (frequency[num] > maxFrequency) {
            maxFrequency = frequency[num];
            mode = num;
        }
    });

    return mode;
};

// Function to prompt the user to enter the numbers
const promptForNumbers = () => {
    rl.question('Please enter the numbers separated by commas: ', (input) => {
        // Convert input string to an array of numbers
        const numbers = input.split(',').map(num => num.trim());
        
        // Validate the input to ensure all entries are numbers
        const areAllNumbers = numbers.every(num => !isNaN(num));
        
        if (areAllNumbers) {
            const numberArray = numbers.map(Number);
            // Find and output the mode
            console.log(`The mode is: ${findMode(numberArray)}`);
            rl.close(); // Close the readline interface
        } else {
            console.log('Invalid input. Please enter only numbers separated by commas.');
            promptForNumbers(); // Prompt the user to enter the numbers again
        }
    });
};

// Start the prompt
promptForNumbers();