package exercices.basic
import kotlin.random.Random

fun collectionPipeline(): Int? {
    return List(10) { Random.nextInt(1, 10) }.also { println(it) }
        .filter { number -> (number%2) == 1 && number > 5 }
        .map { it * 2 }
        .firstOrNull()
}

fun main() {
    println(collectionPipeline() ?: "Dont have any number")
}
