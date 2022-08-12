import java.io.File


fun main() {
    val aunts = mutableListOf<MutableMap<String, Int>>()
    File("data.txt").forEachLine {
        val words = it.replace(":", "").replace(",", "").split(" ")
        aunts.add(mutableMapOf(words[2] to words[3].toInt(), words[4] to words[5].toInt(), words[6] to words[7].toInt()))
    }
    val analysis = mapOf<String, Int>(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )
    // println(aunts)
    for ((i, aunt) in aunts.withIndex()) {
        var candidate1 = true
        var candidate2 = true
        for ((k, v) in aunt.entries) {
            if (analysis[k] != v) {
                candidate1 = false
            }
            when (k) {
                "cats", "trees" -> {
                    if (analysis[k]!! >= v) {
                        candidate2 = false
                    }
                }
                "pomeranians", "goldfish" -> {
                    if (analysis[k]!! <= v) {
                        candidate2 = false
                    }
                }
                else -> {
                    if (analysis[k] != v) {
                        candidate2 = false
                    }
                }
            }
        }
        if (candidate1) {
            println("${i + 1}")  // first answer
        }
        if (candidate2) {
            println("${i + 1}")  // second answer
        }
    }
}

main()
