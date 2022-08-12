import java.io.File
import java.io.InputStream

fun main() {
    var lines = mutableListOf<List<String>>()
    var wires = mutableMapOf<String, Int>()
    File("data.txt").forEachLine {
        var (expr, dest) = it.split(" -> ")
        lines.add(listOf(expr, dest))
        wires[dest] = if (Regex("""\d+""").matches(expr)) expr.toInt() else -1
    }
    val a = computeWires(lines, wires.toMutableMap())
    println(a)  // first answer
    wires["b"] = a ?: -1
    println(computeWires(lines, wires))  // second answer
}

fun computeWires(lines: MutableList<List<String>>, wires: MutableMap<String, Int>): Int? {
    val mask = 0xFFFF
    out@ while (wires["a"] == -1) {
        for ((expr, dest) in lines) {
            if (wires[dest] == -1) {
                if ("NOT " in expr) {
                    val w = expr.slice(4 .. expr.length - 1)
                    if (wires[w] != -1) {
                        wires[dest] = (wires[w]?.inv() ?: -1) and mask
                        if (dest == "a") {
                            break@out
                        }
                    }
                } else if (Regex("""[a-z]+""").matches(expr)) {
                    val w = expr
                    if (wires[w] != -1) {
                        wires[dest] = wires[w] ?: -1
                        if (dest == "a") {
                            break@out
                        }
                    }
                } else {
                    val (w1, w2) = Regex(""" (?:AND|OR|RSHIFT|LSHIFT) """).split(expr)
                    if (wires[w1] != -1 && wires[w2] != -1) {
                        val v1 = w1.toIntOrNull() ?: wires[w1]
                        val v2 = w2.toIntOrNull() ?: wires[w2]
                        when {
                            "AND" in expr -> {
                                wires[dest] = v1?.and(v2!!) ?: -1
                            }
                            "OR" in expr -> {
                                wires[dest] = (v1?.or(v2!!) ?: -1) and mask
                            }
                            "RSHIFT" in expr -> {
                                wires[dest] = (wires[w1]?.shr(w2.toInt()) ?: -1) and mask
                            }
                            "LSHIFT" in expr -> {
                                wires[dest] = (wires[w1]?.shl(w2.toInt()) ?: -1) and mask
                            }
                        }
                        if (dest == "a") {
                            break@out
                        }
                    }
                }
            }
        }
    }
    return wires["a"]
}

main()
