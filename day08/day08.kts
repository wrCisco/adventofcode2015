import java.io.File


fun main() {
    var result1 = 0
    var result2 = 0
    File("data.txt").forEachLine {
        val sliced = it.slice(1 .. it.length - 2)
        val unescaped = Regex("""\\(?:x[0-9a-f]{2}|\\|")""").replace(sliced, "_")
        result1 += it.length - unescaped.length
        result2 += it.count {it == '"'} + it.count {it == '\\'} + 2
    }
    println(result1)
    println(result2)
}

main()
