package exercices.basic

import kotlin.random.Random

fun List<Int>.secondLargest(): Int? {
    return this.sortedDescending().distinct().getOrNull(1)
}

fun List<Int>.secondLargestAnotherSolution(): Int? {
    val max = this.maxOrNull()
    return max?.let {this.filter { it < max }}?.maxOrNull()
}

fun main() {
    val secondLargest = List(10) { Random.nextInt(1, 10) }.also { println(it) }
        .secondLargest()
    println(secondLargest)

    val secondLargestAnotherSolution = List(10) { Random.nextInt(1, 10) }.also { println(it) }.secondLargestAnotherSolution()
    println(secondLargestAnotherSolution)

    val secondLargestNull = emptyList<Int>().secondLargestAnotherSolution()
    println(secondLargestNull)
}