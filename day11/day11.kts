import java.io.File

val letters = "abcdefghijklmnopqrstuvwxyz"
val invalid = "ilo"

fun main() {
    var psw = File("test.txt").readText().toMutableList()
    psw.forEachIndexed { i, c ->
        if (c in invalid) psw[i]++
    }
    while (!isValid(psw)) {
        psw = increasePassword(psw)
    }
    println(psw.joinToString(""))  // first answer
    psw = increasePassword(psw)
    while (!isValid(psw)) {
        psw = increasePassword(psw)
    }
    println(psw.joinToString(""))  // second answer
}

fun increasePassword(word: MutableList<Char>): MutableList<Char> {
    var carriage: Int
    for (i in word.size - 1 downTo 0) {
        var index = letters.indexOf(word[i]) + 1
        if (index >= letters.length) {
            index -= letters.length
            carriage = 1
        } else {
            if (letters[index] in invalid) {
                index++
            }
            carriage = 0
        }
        word[i] = letters[index]
        if (carriage == 0) {
            break
        }
    }
    return word
}

fun isValid(word: MutableList<Char>): Boolean {
    var prevIndex = -2
    var inc_seq = 0
    var inc_straight = false
    for (c in word) {
        // if (invalid.contains(c)) {
        //     return false
        // }
        if (letters.indexOf(c) == prevIndex + 1) {
            inc_seq++
            if (inc_seq == 2) {
                inc_straight = true
                break
            }
        } else {
            inc_seq = 0
        }
        prevIndex = letters.indexOf(c)
    }
    return inc_straight && Regex("""(.)\1.*?(.)\2""").containsMatchIn(word.joinToString(""))
}

main()
