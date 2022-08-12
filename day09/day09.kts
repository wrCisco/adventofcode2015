import java.io.File


fun main() {
    val graph = mutableMapOf<String, MutableMap<String, Int>>()
    File("data.txt").forEachLine {
        val (loc1, _, loc2, _, distance) = it.split(" ")
        graph.getOrPut(loc1) { mutableMapOf() }.put(loc2, distance.toInt())
        graph.getOrPut(loc2) { mutableMapOf() }.put(loc1, distance.toInt())
    }
    var results = mutableListOf(9999, 0)  // shortest, longest
        for (node in graph.keys) {
            val visited = mutableMapOf<String, Boolean>()
            graph.keys.forEach { visited[it] = false }
            visited[node] = true
            results = visitNode(graph, visited, node, 0, results)
        }
    println(results)
}

fun visitNode(
    graph: MutableMap<String, MutableMap<String, Int>>,
    visited: MutableMap<String, Boolean>,
    node: String,
    length: Int,
    extremeLengths: MutableList<Int>,
): MutableList<Int> {
    var localExtremeLengths = extremeLengths
    if (visited.values.any { it == false }) {
        for (newNode in graph.keys) {
            if (visited[newNode] != true) {
                visited[newNode] = true
                localExtremeLengths = visitNode(
                    graph, visited, newNode, length + graph[node]!![newNode]!!, localExtremeLengths
                )
                visited[newNode] = false
            }
        }
    } else {
        if (length < localExtremeLengths[0]) {
            localExtremeLengths[0] = length
        }
        if (length > localExtremeLengths[1]) {
            localExtremeLengths[1] = length
        }
    }
    return localExtremeLengths
}

main()
