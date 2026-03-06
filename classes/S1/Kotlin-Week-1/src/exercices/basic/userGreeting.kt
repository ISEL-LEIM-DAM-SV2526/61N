package exercices.basic

fun greetUser(name: String?) = "Hello ${name ?: "Guest"}"

fun main() {
    println(greetUser("John"))
    println(greetUser(null))
}