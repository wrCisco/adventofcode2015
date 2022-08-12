import java.math.BigInteger
import java.security.MessageDigest
import java.io.File

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(
            1,
            md.digest(input.toByteArray())
        ).toString(16).padStart(32, '0')
}


fun main() {
    val text = File("data.txt").readText()
    var hash = ""
    var number = 0
    while (!hash.startsWith("00000")) {
        hash = md5(text + (++number))
    }
    println(number)
    while (!hash.startsWith("000000")) {
        hash = md5(text + (++number))
    }
    println(number)
}

main()
