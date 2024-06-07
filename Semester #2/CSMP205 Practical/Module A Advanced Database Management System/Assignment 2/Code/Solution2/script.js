const swapKeysAndValues = obj => Object.fromEntries(Object.entries(obj).map(([key, value]) => [value, key]));

// Function to read input from the text area, swap keys and values, and display the result
const swapKeysAndValuesFromInput = () => {
    const input = document.getElementById('objectInput').value.trim();
    
    if (!input) {
        document.getElementById('result').value = 'Input cannot be empty';
        return;
    }

    try {
        // Parse the input into key-value pairs
        const pairs = input.split(',').map(pair => pair.trim().split(':').map(item => item.trim()));

        // Create an object from the pairs
        const obj = Object.fromEntries(pairs);

        // Swap keys and values
        const swapped = swapKeysAndValues(obj);

        // Convert the result to a string and display it
        const resultString = Object.entries(swapped).map(([key, value]) => `${key}:${value}`).join(', ');
        document.getElementById('result').value = resultString;
    } catch (error) {
        // Display an error message if the input is not valid
        document.getElementById('result').value = 'Invalid input. Please enter key-value pairs in the format "key:value, key:value"';
    }
};
