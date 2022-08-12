import java.io.File

fun main() {
    val graph = mutableMapOf<String, MutableMap<String, Int>>()
    File("data.txt").forEachLine {
        var words = it.split(" ")
        val name1 = words[0]
        var qty = words[3].toInt()
        qty = if (words[2] == "lose") -qty else qty
        val name2 = words[10].slice(0..words[10].length-2)
        graph.getOrPut(name1) { mutableMapOf() }.put(name2, qty)
    }
    val start = graph.keys.first()
    val seated = mutableMapOf<String, Boolean>()
    graph.keys.forEach { seated[it] = false }
    seated[start] = true
    println(visitNode(start, start, graph, seated, 0, 0))  // first answer

    graph.keys.forEach { graph[it]!!.put("me", 0) }
    graph["me"] = seated.keys.zip(List(seated.keys.size) { 0 }).toMap(mutableMapOf())
    graph.keys.forEach { seated[it] = false}
    seated[start] = true
    println(visitNode(start, start, graph, seated, 0, 0))  // second answer
}

fun visitNode(
    curNode: String,
    startNode: String,
    graph: MutableMap<String, MutableMap<String, Int>>,
    visited: MutableMap<String, Boolean>,
    happiness: Int,
    maxHappiness: Int
): Int {
    var local_happiness = happiness
    var local_maxHappiness = maxHappiness
    if (visited.values.any { it == false }) {
        for (neighbour in graph.getValue(curNode).keys) {
            if (!visited.getValue(neighbour)) {
                val newNode = neighbour
                visited[neighbour] = true
                local_maxHappiness = visitNode(
                    newNode,
                    startNode,
                    graph,
                    visited,
                    happiness + graph[curNode]!![newNode]!! + graph[newNode]!![curNode]!!,
                    local_maxHappiness
                )
                visited[neighbour] = false
            }
        }
    } else {
        local_happiness += graph[curNode]!![startNode]!! + graph[startNode]!![curNode]!!
        if (local_happiness > local_maxHappiness) {
            local_maxHappiness = local_happiness
        }
    }
    return local_maxHappiness
}

main()
