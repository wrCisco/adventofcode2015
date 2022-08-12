import java.io.File


fun main() {
    val containers = mutableListOf<Int>()
    File("data.txt").forEachLine { 
        containers.add(it.toInt())
    }
    // println(containers.sorted())
    val combinations = mutableSetOf<List<Int>>()
    addContainer(0, listOf<Int>(), combinations, containers)
    var rep = containers.filter { val n = it; containers.count { it == n } >= 2 }.toSet()
    var minLength = 99
    for (c in combinations) {
        if (c.size < minLength) {
            minLength = c.size
        }
    }
    var result1 = 0
    var result2 = 0
    for (c in combinations) {
        var exp = 0
        for (n in rep) {
            if (c.count { it == n } == 1) {
                exp++
            }
        }
        val increment = Math.pow(2.0, exp.toDouble()).toInt()
        result1 += increment
        if (c.size == minLength) {
            result2 += increment
        }
    }
    println(result1)
    println(result2)
}

fun addContainer(
    partial_amount: Int,
    partial_combination: List<Int>,
    combinations: MutableSet<List<Int>>,
    containers: List<Int>
) {
    for (container in containers) {
        if (partial_amount + container == 150) {
            combinations.add((partial_combination + container).sorted())
        }
        if (partial_amount + container >= 150) {
            continue
        } else {
            addContainer(partial_amount + container, partial_combination + container, combinations, containers - container)
        }
    }
}

main()
