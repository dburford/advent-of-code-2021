package day11

import allNeighborOffsets
import printGrid

const val inputFile = "/day11.txt"

val samples = """
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526
""".trimMargin()

fun readData(str: String): List<MutableList<Int>> =
    str
        .lines()
        .map(String::trim)
        .map {
            it.toList()
                .map {
                    it.toString().toInt()
                }
                .toMutableList()
        }


fun incrementAll(grid: List<MutableList<Int>>): MutableList<Pair<Int, Int>> {

    val flashing = mutableListOf<Pair<Int, Int>>()
    for (r in grid.indices) {
        for (c in grid[r].indices) {
            grid[r][c] = (grid[r][c] + 1) % 10
            if (grid[r][c] == 0)
                flashing.add(Pair(r, c))
        }
    }
    return flashing
}

private fun visit(r: Int, c: Int, grid: List<MutableList<Int>>): Int {

    if (grid[r][c] > 0)
        grid[r][c] = (grid[r][c] + 1) % 10

    if (grid[r][c] > 0)
        return 0

    return 1 + allNeighborOffsets
        .mapNotNull { (dr, dc) ->
            grid.getOrNull(r + dr)?.getOrNull(c + dc)?.let {
                if (it > 0)
                    visit(r + dr, c + dc, grid)
                else
                    0
            }
        }
        .sum()
}

fun runGeneration(grid: List<MutableList<Int>>): Int {
    val flashing = incrementAll(grid)
    var count = 0
    for ((r, c) in flashing) {
        count += visit(r, c, grid)
    }
    return count
}

fun solution1(grid: List<MutableList<Int>>, generations: Int): Int {
    var count = 0
    repeat(generations) {
        count += runGeneration(grid)
    }
    return count
}

fun solution2(grid: List<MutableList<Int>>): Int {
    var count = 0
    var generation = 0
    val area = grid.count() * grid[0].count()
    while (count < area) {
        generation += 1
        count = runGeneration(grid)
    }
    return generation
}

fun main() {
    println(solution1(readData(samples), 100))
    println(solution2(readData(samples)))
}
