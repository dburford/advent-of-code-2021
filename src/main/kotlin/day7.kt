package day7

import kotlin.math.abs
import median

const val inputFile = "/day7.txt"

const val samples = "16,1,2,0,4,2,7,1,2,14"

fun readData(str: String) =
    str
        .split(",")
        .map(String::trim)
        .map(String::toLong)

fun cost1(x1: Long, x2: Long) = abs(x1 - x2)

fun cost2(x1: Long, x2: Long) =
    abs(x1 - x2).let { (it * it + it) / 2 }

fun totalCost(positions: List<Long>, m: Long, cost: (Long, Long) -> Long) =
    positions.fold(0L) { acc, x ->
        acc + cost(m, x)
    }

fun solution1(positions: List<Long>) = totalCost(positions, positions.median(), ::cost1)

fun solution2(positions: List<Long>): Long {

    // ---------C-N------------
    // ---------m-m+1----------

    // ---------N-C------------
    // ---------m-m------------

    // initial guess is median, thereafter look for minimum cost
    var delta = 1
    var m = positions.median()
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
