import java.io.File


val minPresents = File("data.txt").readText().toInt()

var house = 0
var presents = 0
while (presents * 10 < minPresents) {
    house += 36
    presents = house
    for (i in 1 .. house / 2) {
        if (house % i == 0) {
            presents += i
        }
    }
}
println("House $house: ${presents * 10} presents")

house = 0
presents = 0
while (presents * 11 < minPresents) {
    house += 36
    presents = house
    for (i in Math.ceil(house / 50.0).toInt() .. house / 2) {
        if (house % i == 0) {
            presents += i
        }
    }
}
println("House $house: ${presents * 11} presents.")
