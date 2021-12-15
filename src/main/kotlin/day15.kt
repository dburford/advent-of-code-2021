package day15

import neighborOffsets
import kotlin.math.abs
import java.util.*
import Point
import printCharGrid
import kotlin.reflect.KFunction3

const val inputFile = "/day15.txt"

val samples = """
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581
""".trimMargin()

fun readData(str: String): Array<IntArray> =
    str
        .lines()
        .map { it.toList() }
        .map {
            it.map { it.toString().toInt() }.toIntArray()
        }
        .toTypedArray()


fun printPoints(points: List<Point>, base: Array<IntArray>) {
    val maxX = points.maxOf { (x, _) -> x }
    val maxY = points.maxOf { (_, y) -> y }

    val grid = MutableList(maxY + 1) { MutableList(maxX + 1) { '.' } }
    grid.forEachIndexed { r, row ->
        row.forEachIndexed { c, v ->
            grid[r][c] = base.getScaledBy5(c, r)!!.toString()[0]
        }
    }
    points.forEach { (x, y) -> grid[y][x] = '.' }
    printCharGrid(grid)
}

fun hscore(p1: Point, p2: Point): Int {
    val xdiff = p1.x - p2.x
    val ydiff = p1.y - p2.y
    return abs(xdiff) + abs(ydiff)
}

fun returnPath(endPoint: Point, previousNode: Map<Point, Point>): List<Point> {
    val path = mutableListOf<Point>(endPoint)
    var current = previousNode[endPoint]
    while (current != null) {
        path += current
        current = previousNode[current]
    }
    return path.reversed()
}

fun astar(
    startPoint: Point,
    endPoint: Point,
    grid: Array<IntArray>,
    mapfn: KFunction3<Array<IntArray>, Int, Int, Int?>
): Int {

    data class Node(val p: Point, val fscore: Int)

    val openSet = PriorityQueue<Node>() { a, b -> a.fscore - b.fscore }
    val previousNode = mutableMapOf<Point, Point>()
    val gscores = mutableMapOf<Point, Int>()

    // put start onto PQ
    openSet.add(Node(startPoint, 0))
    gscores[startPoint] = 0

    while (openSet.isNotEmpty()) {

        val current = openSet.poll()

        if (current.p == endPoint) {
            printPoints(returnPath(endPoint, previousNode), grid)
            return gscores.getOrDefault(endPoint, -1)
        }

        // find neighbors using neighbor offsets
        neighborOffsets
            .mapNotNull { (dx, dy) ->
                mapfn(grid, current.p.x + dx, current.p.y + dy)
                    ?.let { risk ->
                        val point = Point(current.p.x + dx, current.p.y + dy)
                        val gscore = gscores.getOrDefault(current.p, 0) + risk

                        if (gscore < gscores.getOrDefault(point, Int.MAX_VALUE)) {
                            gscores[point] = gscore
                            previousNode[point] = current.p
                            openSet.add(Node(point, gscore + hscore(point, endPoint)))
                        }
                    }
            }

    }

    return gscores.getOrDefault(endPoint, -1)
}

fun Array<IntArray>.getScaledBy5(x: Int, y: Int) =
    Pair(this[0].count(), this.count()).let { (sizeX, sizeY) ->
        if (x < 0 || y < 0 || x >= sizeX * 5 || y >= sizeY * 5)
            null
        else
            (this[y % sizeY][x % sizeX] + ((x / sizeX) + (y / sizeY))).let { v ->
                if (v > 9) v - 9 else v
            }
    }

fun Array<IntArray>.getOrNull(x: Int, y: Int) =
    this.getOrNull(y)?.getOrNull(x)

fun solution1(grid: Array<IntArray>) =
    astar(Point(0, 0), Point(grid.count() - 1, grid[0].count() - 1), grid, Array<IntArray>::getOrNull)

fun solution2(grid: Array<IntArray>) =
    astar(Point(0, 0), Point(grid[0].count() * 5 - 1, grid.count() * 5 - 1), grid, Array<IntArray>::getScaledBy5)

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
