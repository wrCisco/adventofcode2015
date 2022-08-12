#!/usr/bin/env -S JAVA_OPTS="-Xmx8g" kotlinc -script --

data class Character(var hitPoints: Int, var damage: Int, var armor: Int, var mana: Int = 0)

class Battle(
    val p1: Character,
    val p2: Character,
    var cost: Int = 0,
    val activeEffects: MutableMap<String, Int> = mutableMapOf<String, Int>()
) {
    fun copy(): Battle {
        return Battle(p1.copy(), p2.copy(), cost, activeEffects.toMutableMap())
    }
}

class Spell(
    val cost: Int,
    val effect: (Character, Character) -> Unit,
    val timer: Int?
)

val spells = mutableMapOf<String, Spell>().apply {
    this["Magic Missile"] = Spell(53, fun(_: Character, p2: Character) { p2.hitPoints -= 4 }, null);
    this["Drain"] = Spell(73, fun(p1: Character, p2: Character) { p1.hitPoints += 2; p2.hitPoints -= 2 }, null);
    this["Shield"] = Spell(113, fun(p1: Character, _: Character) { p1.armor = 7 }, 6);
    this["Poison"] = Spell(173, fun(_: Character, p2: Character) { p2.hitPoints -= 3}, 6);
    this["Recharge"] = Spell(229, fun(p1: Character, _: Character) { p1.mana += 101 }, 5);
}


fun main() {
    val enemy = Character(58, 9, 0)
    val player = Character(50, 0, 0, 500)
    println(fight(player, enemy))
    println(fight(player, enemy, true))
}

fun fight(p1: Character, p2: Character, hardDifficulty: Boolean = false): Int {
    val activeBattles = mutableSetOf<Battle>(Battle(p1.copy(), p2.copy()))
    var minCost = 5000
    var i = 0
    while (activeBattles.size > 0) {
        val toRemove = mutableSetOf<Battle>()
        for (battle in activeBattles) {
            if (hardDifficulty) {
                battle.p1.hitPoints -= 1
            }
            if (battle.p1.hitPoints < 1 || battle.cost > minCost) {
                toRemove.add(battle)
            }
        }
        activeBattles -= toRemove
        toRemove.clear()
        for (battle in activeBattles) {
            /* execute effects */
            battle.p1.armor = 0
            val endedEffects = mutableSetOf<String>()
            for (effect in battle.activeEffects.keys) {
                spells[effect]!!.effect(battle.p1, battle.p2)
                if (battle.p2.hitPoints < 1) {
                    toRemove.add(battle)
                    if (battle.cost < minCost) {
                        minCost = battle.cost
                    }
                }
                battle.activeEffects[effect] = battle.activeEffects[effect]!! - 1
                if (battle.activeEffects[effect] == 0) {
                    endedEffects.add(effect)
                }
            }
            battle.activeEffects -= endedEffects
        }
        activeBattles -= toRemove
        toRemove.clear()
        /* enemy's round */
        if (i % 2 != 0) {
            for (battle in activeBattles) {
                battle.p1.hitPoints -= battle.p2.damage - battle.p1.armor
                if (battle.p1.hitPoints < 1) {
                    toRemove.add(battle)
                }
            }
            activeBattles -= toRemove
        }
        /* player's round */
        else {
            val toAdd = mutableSetOf<Battle>()
            for (spell in spells.keys) {
                for (battle in activeBattles) {
                    toRemove.add(battle)
                    if (spell !in battle.activeEffects.keys && battle.p1.mana >= spells.getValue(spell).cost) {
                        val battleCopy = battle.copy()
                        toAdd.add(battleCopy)
                        battleCopy.cost += spells.getValue(spell).cost
                        battleCopy.p1.mana -= spells.getValue(spell).cost
                        val timer = spells.getValue(spell).timer
                        if (timer == null) {
                            spells.getValue(spell).effect(battleCopy.p1, battleCopy.p2)
                            if (battleCopy.p2.hitPoints < 1) {
                                toAdd.remove(battleCopy)
                                if (battleCopy.cost < minCost) {
                                    minCost = battleCopy.cost
                                }
                            }
                        } else {
                            battleCopy.activeEffects.put(spell, timer)
                        }
                    }
                }
            }
            activeBattles += toAdd
            activeBattles -= toRemove
        }
        i++
    }
    return minCost
}

main()
