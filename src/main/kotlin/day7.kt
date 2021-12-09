package day7

import kotlin.math.abs

const val inputFile = "/day7.txt"

const val samples = "16,1,2,0,4,2,7,1,2,14"

fun readData(str: String) =
    str
        .split(",")
        .map(String::trim)
        .map(String::toInt)

fun median(positions: List<Int>): Int {
    val n = positions.count()
    val sorted = positions.sorted()
    return when (n % 2) {
        1 -> sorted[n / 2]
        0 -> (sorted[n / 2] + sorted[(n / 2) - 1]) / 2
        else -> 0
    }
}

fun cost1(x1: Int, x2: Int) = abs(x1 - x2)

fun cost2(x1: Int, x2: Int) =
    abs(x1 - x2).let { (it * it + it) / 2 }

fun totalCost(positions: List<Int>, m: Int, cost: (Int, Int) -> Int) =
    positions.fold(0) { acc, x ->
        acc + cost(m, x)
    }

fun solution1(positions: List<Int>) = totalCost(positions, median(positions), ::cost1)

fun solution2(positions: List<Int>): Int {

    // ---------C-N------------
    // ---------m-m+1----------

    // ---------N-C------------
    // ---------m-m------------

    // initial guess is median, thereafter look for minimum cost
    var delta = 1
    var m = median(positions)
    var currentCost = totalCost(positions, m, ::cost2)
    var nextCost = totalCost(positions, m + delta, ::cost2)

    // cost increasing in +x direction, so switch direction
    if (nextCost > currentCost) {
        m += delta
        nextCost = currentCost
        delta = -1
    }

    do {
        m += delta
        currentCost = nextCost
        nextCost = totalCost(positions, m + delta, ::cost2)
    } while (nextCost < currentCost)

    return currentCost
}

fun main() {
    solution1(readData(samples))
    solution2(readData(samples))
}
