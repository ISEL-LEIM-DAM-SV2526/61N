package exercices.basic

class Person(var name:String, var age:Int) {}

fun scopeFunctionChoice() {
    var person = Person("",0).apply {
        name = "DAM"
        age = 30
    }

    person.also {
        println(it)
    }

    println(person.let {
        "Person name: ${it.name}, and with age: ${it.age}"
    })

    // In Chain

    val personDescription = Person("",0).apply {
        name = "DAM"
        age = 30
    }.also {
        println("Created person: ${it.name}, age ${it.age}")
    }.let {
        "Person description: ${it.name}, age ${it.age}"
    }

    println(personDescription)
}

fun main() {
    scopeFunctionChoice()
}
