#!/usr/bin/env -S kotlinc -script --

import java.io.File

fun main() {
    val rules = mutableMapOf<String, MutableList<String>>()
    var molecule = ""
    File("data.txt").forEachLine {
        if (it.contains("=>")) {
                val tokens = it.split(" => ")
                rules.getOrPut(tokens[0]) { mutableListOf() }.add(tokens[1])
        } else if (it.length > 0) {
            molecule = it
        }
    }
    var newMolecules = mutableSetOf<String>()
    for ((k, v) in rules.entries) {
        var match = Regex(k).find(molecule)
        while (match != null) {
            for (rep in v)
                newMolecules.add(
                    molecule.slice(0..match.range.start - 1) + rep + molecule.slice(match.range.endInclusive + 1..molecule.length - 1)
                )
            match = match.next()
        }
    }
    println(newMolecules.size)  // first answer

    // val molArray = makeElementsArray(molecule)
    // val ars = molArray.count { it == "Ar" }
    // val ys = molArray.count { it == "Y" }
    // val oneToTwoReactions = molArray.size - (ars * 3 + ys * 2)
    // println(ars + oneToTwoReactions - 1)  // second answer
    // // Or, more concise and functional style:
    makeElementsArray(molecule).also {
        println(it.size - (it.count { it == "Ar" } * 2 + it.count { it == "Y" } * 2) - 1)  // second answer
    }
}


fun makeElementsArray(molecule: String): List<String> {
    val molArray = mutableListOf<String>()
    var i = 1
    while (i < molecule.length) {
        if (molecule[i].isUpperCase()) {
            molArray.add(molecule.slice(i-1..i-1))
            i++
        } else {
            molArray.add(molecule.slice(i-1..i))
            i += 2
        }
    }
    if (i - 1 < molecule.length && molecule[i-1].isUpperCase()) {
        molArray.add(molecule.slice(i-1..i-1))
    }
    return molArray.toList()
}

main()
