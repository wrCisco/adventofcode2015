import java.io.File


fun main() {
    val ingredients = mutableListOf<List<Int>>()
    File("data.txt").forEachLine {
        ingredients.add(Regex("""-?[0-9]+""").findAll(it).map { it.value.toInt() }.toList())
    }
    val values = List(100) { it + 1 }
    val combinations: Sequence<List<Int>> = sequence {
        for (a in values) {
            for (b in values) {
                if (a + b > 100) break
                for (c in values) {
                    if (a + b + c > 100) break
                    yield(listOf(a, b, c, 100 - (a + b + c)))
                }
            }
        }
    }

    val i = ingredients
    var maxScore = 0
    var maxScore2 = 0
    for (l in combinations) {
        val score = Math.max((l[0] * i[0][0] + l[1] * i[1][0] + l[2] * i[2][0] + l[3] * i[3][0]), 0) *
                    Math.max((l[0] * i[0][1] + l[1] * i[1][1] + l[2] * i[2][1] + l[3] * i[3][1]), 0) *
                    Math.max((l[0] * i[0][2] + l[1] * i[1][2] + l[2] * i[2][2] + l[3] * i[3][2]), 0) *
                    Math.max((l[0] * i[0][3] + l[1] * i[1][3] + l[2] * i[2][3] + l[3] * i[3][3]), 0)
        if (score > maxScore) {
            maxScore = score
        }
        val calories = l[0] * i[0][4] + l[1] * i[1][4] + l[2] * i[2][4] + l[3] * i[3][4]
        if (calories == 500 && score > maxScore2) {
            maxScore2 = score
        }
    }
    println(maxScore)
    println(maxScore2)

}

main()
