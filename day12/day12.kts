import java.io.File
// import kotlinx.serialization.decodeFromString
// import kotlinx.serialization.json.Json

class IndexContainer(var index: Int = 0)


fun main() {
    var text = File("data.txt").readText()
    var mySum = 0
    Regex("-?[0-9]+").findAll(text).forEach { mySum += it.value.toInt() }
    println(mySum)  // first answer
    println(sumValues(IndexContainer(), text))  // second answer
}

fun sumValues(i: IndexContainer, text: String): Int
{
    var partial = 0
    var composeNum = mutableListOf<Char>()
    var inString = false
    var composeString = mutableListOf<Char>()
    while (i.index < text.length) {
        // println("Index ${i.index} - Char ${text[i.index]} - initial partial $partial")
        when(text[i.index]) {
            '{', '[' -> {
                i.index++
                partial += sumValues(i, text)
            }
            in "-0123456789" -> {
                composeNum.add(text[i.index])
            }
            '"' -> {
                if (inString) {
                    if (composeString.joinToString("") == "red" && text[i.index-5] == ':') {
                        var level = 0
                        for (j in i.index .. text.length - 1) {
                            if (text[j] == '{') {
                                level++
                            } else if (text[j] == '}') {
                                if (level == 0) {
                                    break
                                } else {
                                    level--
                                }
                            }
                            i.index++
                        }
                        return 0
                    } else {
                        composeString.clear()
                    }
                }
                inString = !inString
            }
            ',', ']', '}' -> {
                if (composeNum.size > 0) {
                    partial += composeNum.joinToString("").toInt()
                    composeNum.clear()
                }
                if (text[i.index] == ']' || text[i.index] == '}') {
                    return partial
                }
            }
            ':' -> {}
            in "abcdefghijklmnopqrstuvwxyz" -> {
                composeString.add(text[i.index])
            }
            else -> println("Found something unforeseen at index ${i.index}: ${text[i.index]}")
        }
        i.index++
        // println("Final partial $partial")
        // readLine()
    }
    return partial
}

main()
