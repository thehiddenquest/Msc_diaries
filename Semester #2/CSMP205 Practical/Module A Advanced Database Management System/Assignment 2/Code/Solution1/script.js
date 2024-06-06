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

// Function to read input from the text box and find the mode
const findModeFromInput = () => {
    const input = document.getElementById('numbersInput').value;
    const numbers = input.split(',').map(num => num.trim());
    
    // Validate the input to ensure all entries are numbers
    const areAllNumbers = numbers.every(num => !isNaN(num) && num !== '');
    
    if (areAllNumbers) {
        const numberArray = numbers.map(Number);
        const mode = findMode(numberArray);
        document.getElementById('result').value = `The mode is: ${mode}`;
    } else {
        document.getElementById('result').value = 'Invalid input. Please enter only numbers separated by commas.';
    }
};