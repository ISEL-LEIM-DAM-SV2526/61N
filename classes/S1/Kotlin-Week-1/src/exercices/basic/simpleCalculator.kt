package exercices.basic

/**
 * Simple Calculator
 *
 * A console program that reads a basic arithmetic operation from the user
 * in the format: `{number operator number}` (example: `1 + 2`).
 *
 * The program parses the input, validates the numbers, and performs
 * the requested arithmetic operation.
 *
 * Supported operators:
 * - `+` Addition
 * - `-` Subtraction
 * - `*` Multiplication
 * - `/` Division
 *
 * Division by zero is handled safely by returning `null`.
 * Invalid input will result in an "Invalid operation" message.
 */

/**
 * Performs a simple arithmetic operation between two integers.
 *
 * @param operator The arithmetic operator as a string (`+`, `-`, `*`, `/`).
 * @param a The first operand.
 * @param b The second operand.
 *
 * @return The result of the calculation, or `null` if:
 * - The operator is not supported
 * - Division by zero is attempted
 */
fun calculator(operator: String, a: Int, b: Int): Int? {
    return when (operator) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if (b != 0) a / b else null
        else -> null
    }
}

/**
 * Program entry point.
 *
 * Prompts the user to input a mathematical expression in the format:
 * `number operator number` (example: `3 * 4`).
 *
 * The input is split into three parts:
 * 1. First number
 * 2. Operator
 * 3. Second number
 *
 * The program validates the input, converts the numbers safely using
 * `toIntOrNull()`, and prints the calculation result if valid.
 *
 * If the input format is incorrect or the calculation fails,
 * an error message is displayed.
 */
fun main() {
    println("Insert arithmetic operation - {number operator number} - e.g 1 + 2")

    val parts = readln().split(" ")

    if (parts.size != 3) {
        println("Invalid operation")
        return
    }

    val (aStr, operator, bStr) = parts
    val a = aStr.toIntOrNull()
    val b = bStr.toIntOrNull()

    if (a == null || b == null) {
        println("Invalid operation")
        return
    }

    val result = calculator(operator, a, b)

    if (result == null) {
        println("Invalid operation")
    } else {
        println("Result: $result")
    }
}