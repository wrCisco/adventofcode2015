import java.io.File

class Reindeer(val name: String, val speed: Int, val duration: Int, val rest: Int)

fun main() {
    val reindeers = mutableMapOf<Reindeer, Int>()
    File("data.txt").forEachLine {
        val words = it.split(" ")
        val name = words[0]
        val speed = words[3].toInt()
        val duration = words[6].toInt()
        val rest = words[13].toInt()
        reindeers.put(Reindeer(name, speed, duration, rest), 0)
    }
    val total_duration = 2503
    for (r in reindeers.keys) {
        var distance_traveled = (total_duration / (r.duration + r.rest)).toInt() * r.speed * r.duration + Math.min(r.duration, (total_duration % (r.duration + r.rest))) * r.speed
        reindeers[r] = distance_traveled
    }
    println(reindeers.values.max())  // first answer
    // reindeers.forEach {
    //     println("${it.key.name} -> ${it.value}")
    // }
    reindeers.keys.forEach { reindeers[it] = 0 }
    val points = mutableMapOf<Reindeer, Int>()
    reindeers.keys.forEach { points[it] = 0 }
    for (i in 0..total_duration-1) {
        for (r in reindeers.keys) {
            if (i % (r.duration + r.rest) < r.duration) {
                reindeers[r] = reindeers[r]!! + r.speed
            }
        }
        val max = reindeers.values.max()
        reindeers.forEach {
            if (it.value == max) points[it.key] = points[it.key]!! + 1
        }
    }
    println(points.values.max())  // second answer
}

main()