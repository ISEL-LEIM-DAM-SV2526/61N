package exercices.basic

fun celsiusToFahrenheit(c: Double): Double = (c * (9 / 5)) + 32.0

fun main() {
    println("${celsiusToFahrenheit(70.0)} Fº" )
    println("${celsiusToFahrenheit(100.0)} Fº" )
    println("${celsiusToFahrenheit(180.0)} Fº" )
    println("${celsiusToFahrenheit(200.0)} Fº" )
}