package day9

import neighborOffsets
import java.util.*

const val inputFile = "/day9.txt"

val samples = """
    2199943210
    3987894921
    9856789892
    8767896789
    9899965678
""".trimMargin()

fun readData(str: String) =
    str
        .lines()
        .map(String::trim)
        .map {
            it.toList().map { it.toString().toInt() }.toIntArray()
        }.toTypedArray()

fun solution1(grid: Array<IntArray>) =
    grid.foldIndexed(0) { r, acc, row ->
        acc + row.filterIndexed { c, x ->
            neighborOffsets
                .mapNotNull { (dr, dc) ->
                    grid.getOrNull(r + dr)?.getOrNull(c + dc)
                }
                .all { it > x }
        }.map { it + 1 }.sum()
    }

private fun visit(
    r: Int, c: Int,
    grid: Array<IntArray>,
    visited: MutableSet<Pair<Int, Int>>,
): Int {
    val cell = Pair(r, c)
    if (grid[r][c] == 9 || cell in visited)
        return 0

    visited += cell

    return 1 + neighborOffsets
        .mapNotNull { (dr, dc) ->
            grid.getOrNull(r + dr)?.getOrNull(c + dc)?.let {
                visit(r + dr, c + dc, grid, visited)
            }
        }
        .sum()
}

fun solution2(grid: Array<IntArray>): Int {

    val maxAreas = PriorityQueue<Int>() { a, b -> b - a }
    val visited = mutableSetOf<Pair<Int, Int>>()

    for (r in grid.indices) {
        for (c in grid[r].indices) {
            maxAreas.add(visit(r, c, grid, visited))
        }
    }

    return (1..3).fold(1) { acc, i ->
        acc * maxAreas.poll()
    }
}

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
