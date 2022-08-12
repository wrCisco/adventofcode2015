import java.io.File

class Box(val l: Int, val w: Int, val h: Int) {
	val area = 2 * l * w + 2 * l * h + 2 * w * h
	val smallestSideArea = Math.min(Math.min(l * w, l * h), w * h)
	val neededPaper = area + smallestSideArea

	fun neededRibbon(): Int
	{
		val sizes = listOf(l, w, h).sorted()
		return sizes[0] * 2 + sizes[1] * 2 + l * w * h
	}

}

fun main() {
	var paper = 0
	var ribbon = 0
	File("data.txt").forEachLine {
		val sizes: List<Int> = it.split("x").map { it.toInt() }
		val box = Box(sizes[0], sizes[1], sizes[2])
		paper += box.neededPaper
		ribbon += box.neededRibbon()
	}
	println(paper)  // first answer
	println(ribbon)  // second answer
}

main()