package exercices.basic

fun wordCount(sentences: List<String>): List<Int> {
    return sentences.flatMap { it.split(' ').map {it} }.map { it.count() }
}

fun main() {
    val sentences = listOf(
        "Kotlin is a modern programming language.",
        "It runs on the JVM.",
        "Kotlin is fully interoperable with Java.",
        "Null safety is a key feature of Kotlin.",
        "Coroutines simplify asynchronous programming.",
        "Kotlin is officially supported for Android development.",
        "Extension functions add powerful capabilities.",
        "Data classes reduce boilerplate code.",
        "Kotlin supports functional programming.",
        "Learning Kotlin can improve code readability."
    )
    println(wordCount(sentences))
}