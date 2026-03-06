package exercices.collection

fun main() {
    val words = "The quick brown fox jumps over the lazy dog".split(" ") // Returns a list

    println("------- List -----")
    val lengthsList = words.filter {
        println("filter: $it");
        it.length > 3
    }.map {
        println("length: ${it.length}");
        it.length
    }.take(4)

    println("Final List Result")
    println("Lengths of first 4 words longer than 3 chars:")
    println(lengthsList)


    println("------- Sequence -----")
    // Сonvert the List to a Sequence
    val wordsSequence = words.asSequence()
    val lengthsSequence = wordsSequence.filter { println("filter: $it"); it.length > 3 }
        .map { println("length: ${it.length}"); it.length }
        .take(4)

    println(lengthsSequence) // prints `kotlin.sequences.TakeSequence@MEMORY_ADDR`

    println("Final Sequence Result")
    println("Lengths of first 4 words longer than 3 chars:")
    // Terminal operation: obtaining the result as a List
    println(lengthsSequence.toList()) // top code gets executed, then prints `[5, 5, 5, 4]`

}