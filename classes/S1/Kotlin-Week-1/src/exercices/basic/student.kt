package exercices.basic
data class Student(val name: String, val grade: Int, val gpa: Double) {}

fun main() {
    val students = listOf(
        Student("Alice", 10, 3.8),
        Student("Bob", 11, 3.4),
        Student("Charlie", 12, 3.9),
        Student("Diana", 10, 3.6),
        Student("Ethan", 11, 3.2)
    )

    println(students)
    println(students.filter { it.gpa > 3.5 })
    println(students.groupBy { it.gpa })
    println(students.maxOf { it.gpa })
}