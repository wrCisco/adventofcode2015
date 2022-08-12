import java.io.File

fun main() {
    val text = File("data.txt").readText()
    val row = Regex("""row (\d+)""").find(text)!!.groupValues[1].toInt()
    val col = Regex("""column (\d+)""").find(text)!!.groupValues[1].toInt()
    var code = 20151125L
    val n = row + col - 2
    for (i in 1 .. ((n + 1) * n / 2 + col - 1)) {
        code = code * 252533L % 33554393L
    }
    println(code)
}

main()
