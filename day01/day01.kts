import java.io.File

fun main() {
	val text = File("data.txt").readText()
	println(text.count { it == '(' } - text.count { it == ')' } )  // first answer
	var floor = 0
	for (i in text.indices) {
		when (text[i]) {
			'(' -> ++floor
			')' -> --floor
		}
		if (floor == -1) {
			println(i + 1)  // second answer
			break
		}
	}
}

main()
