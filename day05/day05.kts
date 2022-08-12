import java.io.File
import java.io.InputStream


fun main() {
	val vowels = listOf('a', 'e', 'i', 'o', 'u')
	val naughtySeqs = listOf("ab", "cd", "pq", "xy")
	var nice1 = 0
	var nice2 = 0
	File("data.txt").forEachLine {
		val line = it
		if (it.filter { it in vowels }.toList().size >= 3 &&
			"""(.)\1""".toRegex().containsMatchIn(it) &&
			naughtySeqs.find { it in line } == null  // find is an alias di firstOrNull
		) {
			++nice1
		}
		if ("""(..).*?\1""".toRegex().containsMatchIn(it) &&
			"""(.).\1""".toRegex().containsMatchIn(it)
		) {
			++nice2
		}
	}
	println(nice1)
	println(nice2)
}

main()
