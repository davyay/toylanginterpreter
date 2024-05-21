# Toy Language Interpreter

This project is a simple interpreter for a custom programming language. The language supports basic arithmetic operations and variable assignments, with integer values only. The interpreter is implemented in Java and includes a tokenizer, parser, and interpreter for executing the code.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Error Handling](#error-handling)

## Features

- Supports variable assignments and basic arithmetic operations (`+`, `-`, `*`, `/`).
- Detects syntax errors and reports uninitialized variables.
- Prints the values of all variables after successful execution.
- Ensures numbers with leading zeros (except for zero itself) are rejected.

## Project Structure

- **Token Class**: 
- **Tokenizer Class**: Breaks down the input text into tokens, handling the initial text analysis. Includes inner token class that represents the smallest unit of meaningful text like literals, identifiers, symbols (e.g., `+`, `*`, `;`, `=`).
- **Parser Class**: Analyzes the tokens provided by the Tokenizer to build an abstract syntax tree (AST) that represents the program structure according to the grammar rules.
- **Expr Interface and its Subclasses**:
    - `Binary`: Represents expressions with two operands and an operator (e.g., `a + b`).
    - `Unary`: Represents expressions with a single operand and an operator (e.g., `-a`).
    - `Literal`: Represents literal values (e.g., numbers).
    - `Variable`: Represents identifiers used for variable names.
    - `Grouping`: Represents expressions enclosed in parentheses.
- **Stmt Interface and its Subclasses**:
    - `Expression`: Represents a statement that evaluates an expression.
    - `Assignment`: Represents variable assignment statements.
- **Interpreter Class**: Executes the AST built by the Parser, storing variable values and evaluating expressions. It checks for uninitialized variables and handles other runtime errors.
- **SymbolTable Class**: Manages a symbol table to keep track of variable identifiers and their values throughout the execution of the program.
- **Main Class**: The entry point of the application, which initiates the parsing and interpretation of the input code.

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/davyay/toylanginterpreter
   cd toylanginterpreter
   ```

2. **Compile the project:**

   ```bash
   javac -d bin src/com/github/davyay/toylanginterpreter/*.java
   ```

3. **Run the interpreter:**

   ```bash
   java -cp bin com.github.davyay.toylanginterpreter.Main
   ```

## Usage

After running the interpreter, you can enter your code line by line. End the input with an empty line. Type `exit` to quit the interpreter.

### Example Session

```plaintext
Enter your code (end with an empty line, type 'exit' to quit):
x = 1;
y = 2;
z = x + y;

x = 1
y = 2
z = 3
Enter your code (end with an empty line, type 'exit' to quit):
exit
```

## Examples

### Valid Input

```plaintext
x = 1;
y = 2;
z = x * y + (x - y);

Output:
x = 1
y = 2
z = 1
```

### Invalid Input

```plaintext
x = 001;

Output:
Error: Invalid number format: 001
```

## Error Handling

- **Syntax Errors**: The interpreter checks for syntax errors and reports them with a message indicating the nature of the error.
- **Uninitialized Variables**: The interpreter detects and reports the use of uninitialized variables.
- **Invalid Number Format**: Numbers with leading zeros (except for `0` itself) are considered invalid and will trigger an error.
