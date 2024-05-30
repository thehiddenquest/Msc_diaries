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

// Example usage:
const numbers = [1, 2, 2, 3, 4, 4, 4, 5];
console.log(findMode(numbers)); // Output: 4