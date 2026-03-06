package exercices.basic

fun description(obj: Any): String {
    when (obj) {
        is String -> return "obj is a string: $obj"
        is Int ->
            when (obj) {
                in 0..10 -> return "obj is a integer and is between 0 and 10"
                in 11..100 -> return "obj is a integer and is between 11 and 100"
                else -> return "obj is Large integer"
            }

        else -> return "obj is another type"
    }
}

fun main() {
    println(description("DAM"))
    println(description(488))
    println(description(12))
    println(description(4))
    println(description(9.0))
    println(description('1'))
}