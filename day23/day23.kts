import java.io.File


class Instruction(
    val name: String,
    val register: String,
    val offset: Int = 1
)

class Computer() {
    val registers = mutableMapOf<String, Int>("a" to 0, "b" to 0)
    var index = 0
    val instructions = mutableMapOf<String, (String, Int) -> Unit>(
        "hlf" to fun(r: String, _: Int) { registers[r] = registers[r]!! / 2; index++ },
        "tpl" to fun(r: String, _: Int) { registers[r] = registers[r]!! * 3; index++ },
        "inc" to fun(r: String, _: Int) { registers[r] = registers[r]!! + 1; index++ },
        "jmp" to fun(_: String, offset: Int) { index += offset },
        "jie" to fun(r: String, offset: Int) { if (registers[r]!! % 2 == 0) { index += offset } else { index++ } },
        "jio" to fun(r: String, offset: Int) { if (registers[r]!! == 1) { index += offset } else { index++ } }
    )
}

fun main() {
    val instructions = mutableListOf<Instruction>()
    File("data.txt").forEachLine {
        val line = it.replace(",", "").split(" ")
        val name = line[0]
        when (name) {
            "hlf", "tpl", "inc" -> {
                instructions.add(Instruction(name, line[1]))
            }
            "jmp" -> {
                instructions.add(Instruction(name, "", line[1].toInt()))
            }
            "jie", "jio" -> {
                instructions.add(Instruction(name, line[1], line[2].toInt()))
            }
        }
    }

    val computer = Computer()
    while (computer.index >= 0 && computer.index < instructions.size) {
        val instr = instructions[computer.index]
        computer.instructions[instr.name]!!(instr.register, instr.offset)
    }
    println(computer.registers["b"])

    computer.registers["a"] = 1
    computer.registers["b"] = 0
    computer.index = 0
    while (computer.index >= 0 && computer.index < instructions.size) {
        val instr = instructions[computer.index]
        computer.instructions[instr.name]!!(instr.register, instr.offset)
    }
    println(computer.registers["b"])
}

main()
