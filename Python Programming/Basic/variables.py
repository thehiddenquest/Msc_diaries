integer = 10
print('This is integer value', integer)

string = 'STRING VALUE'
print('This is string value', string)

number = 3.14159
print('This is float format without format function', "%.2f" % number)
print('This is float format with format function', "{:.2f}".format(number))
print(f"This is float format with f string {number:.2f}")

# Type of a variable
print(type(string))
print(type(integer))
print(type(number))

# Values of a string
print(string[0])  # First value
print(string[-1])  # Last value
print(string[0:2])  # From 0 upto 2

# Update the value of String
new = 'NEW ' + string[7:]
print(new)

# Length of a string
print("{} {}".format(len(new), len(string)))

# Operations of int and float
# Division
division_result = 10 / 2  # result will be 5.0

# Integer Division
integer_division_result = 10 // 3  # result will be 3

# Modulo
modulo_result = 10 % 3  # result will be 1

# Exponentiation
exponentiation_result = 2 ** 3  # result will be 8

# Comparison Operators
greater_than = 5 > 3  # True
less_than_or_equal = 5 <= 3  # False
equal_to = 5 == 5  # True

# Printing results
print(f"Addition: {5 + 3}, Subtraction: {7 - 2}, Multiplication: {4 * 6}, Division: {division_result:.2f}, Integer Division: {integer_division_result}, Modulo: {modulo_result}, Exponentiation: {exponentiation_result}, Greater Than (5 > 3): {greater_than}, Less Than or Equal (5 <= 3): {less_than_or_equal}, Equal To (5 == 5): {equal_to}")

# Operations of String
# Concatenation
string1 = "Hello"
string2 = "World"
result_concatenation = string1 + " " + string2

# Repetition
string_repetition = "Python" * 3

# Indexing
first_character = "Python"[0]
third_character = "Python"[2]

# Slicing
substring = "Hello World"[6:]

# Length
length = len("Python")

# Conversion
number = 42
string_conversion = f"The number is {str(number)}"

# Membership
string = "Hello world"
container = "world" in string  # True
print(container)
contains = f"World" in "Hello World"

# Formatting
name = "Alice"
age = 30
formatted_string = f"My name is {name} and I am {age} years old."

# Printing all results using a single f-string statement
print(f"Concatenation: {result_concatenation}, Repetition: {string_repetition}, Indexing (First Character): {first_character}, Indexing (Third Character): {third_character}, Slicing: {substring}, Length: {length}, Conversion: {string_conversion}, Membership: {contains}, Formatting: {formatted_string}")


#Operations of string case change
string = "Hello World"

# Convert to uppercase
uppercase_string = string.upper()
print("Uppercase:", uppercase_string)  # Output: HELLO WORLD

# Convert to lowercase
lowercase_string = string.lower()
print("Lowercase:", lowercase_string)  # Output: hello world

# Capitalize the first character
capitalized_string = string.capitalize()
print("Capitalized:", capitalized_string)  # Output: Hello world

# Swap case
swapped_case_string = string.swapcase()
print("Swapped Case:", swapped_case_string)  # Output: hELLO wORLD

# Check if the string starts with a certain substring
starts_with_hello = string.startswith("Hello")
print("Starts with 'Hello':", starts_with_hello)  # Output: True

# Check if the string ends with a certain substring
ends_with_world = string.endswith("World")
print("Ends with 'World':", ends_with_world)  # Output: True

# Find the index of a substring
index_of_world = string.find("World")
print("Index of 'World':", index_of_world)  # Output: 6

# Replace a substring with another substring
replaced_string = string.replace("World", "Universe")
print("Replaced:", replaced_string)  # Output: Hello Universe

# Split the string into a list of substrings
split_string = string.split()
print("Split:", split_string)  # Output: ['Hello', 'World']

# Join a list of substrings into a single string
joined_string = " ".join(split_string)
print("Joined:", joined_string)  # Output: Hello World