import java.io.File


fun main() {
    var chars = File("data.txt").readText().toList()

    for (i in 1..50) {
        var prev = '_'
        var count = 0
        var newText = mutableListOf<Char>()
        for (c in chars) {
            if (c == prev) {
                count++
            } else {
                if (prev != '_') {
                    newText.add(count.digitToChar())
                    newText.add(prev)
                }
                count = 1
                prev = c
            }
        }
        newText.add(count.digitToChar())
        newText.add(prev)
        chars = newText
        if (i == 40) {
            println(chars.size)
        }
    }
    println(chars.size)
}

main()
