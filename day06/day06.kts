import java.io.File
import java.io.InputStream


fun main() {
    var lights = mutableSetOf<List<Int>>()
    var brightness = mutableMapOf<List<Int>, Int>()
    var totalBrightness = 0
    File("data.txt").inputStream().bufferedReader().forEachLine {
        var matches = Regex("""(turn on |turn off |toggle )(\d+,\d+) through (\d+,\d+)""").find(it)
        if (matches != null) {
            val action = matches.groupValues[1]
            val start = matches.groupValues[2].split(",").map { it.toInt() }
            val end = matches.groupValues[3].split(",").map {it.toInt() }
            val region: Sequence<List<Int>> = sequence {
                for (y in start[1]..end[1]) {
                    for (x in start[0]..end[0]) {
                        yield(listOf(x, y))
                    }
                }
            }
            when (action) {
                "turn on " -> {
                    region.forEach {
                        lights.add(it)
                        brightness[it] = brightness.getOrDefault(it, 0) + 1
                        totalBrightness++
                    }
                }
                "turn off " -> {
                    region.forEach {
                        lights.remove(it)
                        val prev = brightness.getOrDefault(it, 0)
                        if (prev > 0) {
                            brightness[it] = prev - 1
                            totalBrightness--
                        }
                    }
                }
                "toggle " -> {
                    region.forEach {
                        if (it in lights) lights.remove(it)
                        else lights.add(it)
                        brightness[it] = brightness.getOrDefault(it, 0) + 2
                        totalBrightness += 2
                    }
                }
            }
        }
    }
    println(lights.size)  // first answer
    println(totalBrightness)  // second answer
}

main()
