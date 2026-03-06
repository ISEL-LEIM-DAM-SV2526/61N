package exercices.basic

fun gradeConverter(score: Int): String {
    when (score) {
        in 90..100 -> return "A"
        in 80..89 -> return "B"
        in 70..79 -> return "C"
        in 60..69 -> return "D"
        in 60 .. 0 -> return "F"
        else -> return "Invalid Score"
    }
}

fun main() {
    println(gradeConverter(90))
    println(gradeConverter(88))
    println(gradeConverter(73))
    println(gradeConverter(60))
    println(gradeConverter(44))
    println(gradeConverter(1000))
}