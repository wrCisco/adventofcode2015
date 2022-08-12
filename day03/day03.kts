import java.io.File


fun main() {
	var text = File("data.txt").readText()
	var prevPositions = mutableSetOf<Pair<Int, Int>>()
	var pos = Pair(0, 0)
	for (c in text) {
		prevPositions.add(pos)
		pos = move(pos, c)
	}
	prevPositions.add(pos)
	println(prevPositions.size)  // first answer

	prevPositions.clear()
	var pos1 = Pair(0, 0)
	var pos2 = Pair(0, 0)
	for ((i, c) in text.withIndex()) {
		if (i % 2 == 0) {
			prevPositions.add(pos1)
			pos1 = move(pos1, c)
		}
		else {
			prevPositions.add(pos2)
			pos2 = move(pos2, c)
		}
	}
	prevPositions.add(pos1)
	prevPositions.add(pos2)
	println(prevPositions.size)  // second answer
}

fun move(pos: Pair<Int, Int>, c: Char): Pair<Int, Int> {
	return when (c) {
		'^' -> Pair(pos.first, pos.second - 1)
		'v' -> Pair(pos.first, pos.second + 1)
		'>' -> Pair(pos.first + 1, pos.second)
		'<' -> Pair(pos.first - 1, pos.second)
		else -> throw Exception("Can't process character $c.")
	}
}

main()
