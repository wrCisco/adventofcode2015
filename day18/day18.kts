import java.io.File

fun main() {
    var startGrid = mutableListOf<MutableList<String>>()
    File("data.txt").forEachLine {
        startGrid.add(it.split("").slice(1..it.length).toMutableList())
    }
    for (puzzle in 1..2) {
        var grid = startGrid.map { it.toMutableList() }.toMutableList()
        if (puzzle == 2) {
            grid[0][0] = "#"
            grid[0][grid[0].size - 1] = "#"
            grid[grid.size - 1][0] = "#"
            grid[grid.size - 1][grid[0].size - 1] = "#"
        }
        val steps = 100
        for (i in 1..steps) {
            var newGrid = grid.map { it.toMutableList() }.toMutableList()
            for (y in grid.indices) {
                for (x in grid[y].indices) {
                    val neighboursOn = checkNeighbours(x, y, grid)
                    if (puzzle == 2 &&
                        (y == 0 && x == 0 || y == 0 && x == grid[y].size - 1 || x == 0 && y == grid.size - 1 || y == grid.size - 1 && x == grid[y].size - 1)
                    ) {
                        newGrid[y][x] = "#"
                    } else if (grid[y][x] == "#" && listOf(2, 3).contains(neighboursOn)) {
                        newGrid[y][x] = "#"
                    } else if (grid[y][x] == "." && neighboursOn == 3) {
                        newGrid[y][x] = "#"
                    } else {
                        newGrid[y][x] = "."
                    }
                }
            }
            grid = newGrid
        }
        var lightsOn = 0
        grid.forEach { lightsOn += it.count { it == "#" } }
        println(lightsOn)
    }
}

fun printGrid(grid: List<List<String>>) {
    grid.forEach { println(it.joinToString("")) }
}

fun checkNeighbours(x: Int, y: Int, grid: List<List<String>>): Int {
    var on = 0
    if (x > 0 && y > 0) {
        if (grid[y-1][x-1] == "#") on++
    }
    if (x > 0) {
        if (grid[y][x-1] == "#") on++
    }
    if (x > 0 && y < grid.size - 1) {
        if (grid[y+1][x-1] == "#") on++
    }
    if (y > 0) {
        if (grid[y-1][x] == "#") on++
    }
    if (y < grid.size - 1) {
        if (grid[y+1][x] == "#") on++
    }
    if (y > 0 && x < grid[y].size - 1) {
        if (grid[y-1][x+1] == "#") on++
    }
    if (x < grid[y].size - 1) {
        if (grid[y][x+1] == "#") on++
    }
    if (y < grid.size - 1 && x < grid[y].size - 1) {
        if (grid[y+1][x+1] == "#") on++
    }
    return on
}

main()
