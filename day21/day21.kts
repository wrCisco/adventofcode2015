#!/usr/bin/env kotlin


class Character(var hitPoints: Int, var damage: Int, var armor: Int)

val weapons = listOf<String>(
    "Dagger", "Shortsword", "Warhammer", "Longsword", "Greataxe"
)
val armors = listOf<String>(
    "Leather", "Chainmail", "Splintmail", "Bandedmail", "Platemail"
)
val rings = listOf<String>(
    "Damage +1", "Damage +2", "Damage +3", "Defense +1", "Defense +2", "Defense +3"
)
val items = mutableMapOf<String, Map<String, Int>>().apply {
    this[weapons[0]] = mapOf("Cost" to 8, "Damage" to 4, "Armor" to 0);
    this[weapons[1]] = mapOf("Cost" to 10, "Damage" to 5, "Armor" to 0);
    this[weapons[2]] = mapOf("Cost" to 25, "Damage" to 6, "Armor" to 0);
    this[weapons[3]] = mapOf("Cost" to 40, "Damage" to 7, "Armor" to 0);
    this[weapons[4]] = mapOf("Cost" to 74, "Damage" to 8, "Armor" to 0);
    this[armors[0]] = mapOf("Cost" to 13, "Damage" to 0, "Armor" to 1);
    this[armors[1]] = mapOf("Cost" to 31, "Damage" to 0, "Armor" to 2);
    this[armors[2]] = mapOf("Cost" to 53, "Damage" to 0, "Armor" to 3);
    this[armors[3]] = mapOf("Cost" to 75, "Damage" to 0, "Armor" to 4);
    this[armors[4]] = mapOf("Cost" to 102, "Damage" to 0, "Armor" to 5);
    this[rings[0]] = mapOf("Cost" to 25, "Damage" to 1, "Armor" to 0);
    this[rings[1]] = mapOf("Cost" to 50, "Damage" to 2, "Armor" to 0);
    this[rings[2]] = mapOf("Cost" to 100, "Damage" to 3, "Armor" to 0);
    this[rings[3]] = mapOf("Cost" to 25, "Damage" to 0, "Armor" to 1);
    this[rings[4]] = mapOf("Cost" to 40, "Damage" to 0, "Armor" to 2);
    this[rings[5]] = mapOf("Cost" to 80, "Damage" to 0, "Armor" to 3);
}

fun main() {
    val enemy = Character(103, 9, 2)
    val player = Character(100, 0, 0)
    val equipments: Sequence<List<String>> = sequence {
        for (weapon in weapons) {
            yield(listOf<String>(weapon))
            for (armor in armors) {
                yield(listOf<String>(weapon, armor))
                for (ring in rings) {
                    yield(listOf<String>(weapon, ring))
                    yield(listOf<String>(weapon, armor, ring))
                    for (otherRing in rings) {
                        if (otherRing == ring) continue
                        yield(listOf<String>(weapon, ring, otherRing))
                        yield(listOf<String>(weapon, armor, ring, otherRing))
                    }
                }
            }
        }
    }
    var minCost = 9999
    var maxCost = 0
    for (equipment in equipments) {
        var cost = 0
        player.armor = 0
        player.damage = 0
        for (item in equipment) {
            cost += items.getValue(item).getValue("Cost")
            player.armor += items.getValue(item).getValue("Armor")
            player.damage += items.getValue(item).getValue("Damage")
        }
        if (fight(player, enemy)) {
            if (cost < minCost) {
                minCost = cost
            }
        } else if (cost > maxCost) {
            maxCost = cost
        }
    }
    println(minCost)
    println(maxCost)
}


/**
 * Returns true if p1 wins, false otherwise.
 */
fun fight(p1: Character, p2: Character): Boolean {
    var hp1 = p1.hitPoints
    var hp2 = p2.hitPoints
    var i = 0
    while (hp1 > 0 && hp2 > 0) {
        if (i % 2 == 0) {
            hp2 -= Math.max(1, p1.damage - p2.armor)
        }
        else {
            hp1 -= Math.max(1, p2.damage - p1.armor)
        }
        i++
    }
    return hp1 > 0
}

main()
