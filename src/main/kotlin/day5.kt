package day5

import Point
import kotlin.math.*

val inputFile = "/day5.txt"

val samples = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent()



data class LineSegment(val p1: Point, val p2: Point) {

    fun walkTheLine(): Sequence<Point> =
        sequence() {
            val xrange = (p2.x - p1.x)
            val yrange = (p2.y - p1.y)
            val range = max(abs(xrange), abs(yrange))
            val dx = xrange / range
            val dy = yrange / range

            (0..range).map { step ->
                yield(Point(p1.x + step * dx, p1.y + step * dy))
            }
        }

    fun print() {
        p1.print(); print(" -> "); p2.print()
        println()
    }
}

fun readData(str: String) =
    str
        .lines()
        .map(String::trim)
        .map { line ->
            line
                .split("->".toRegex())
                .map { point ->
                    point
                        .split(",")
                        .map(String::trim)
                        .map(String::toInt)
                }
                .map { (x, y) ->
                    Point(x, y)
                }
        }
        .map { (p1, p2) ->
            LineSegment(p1, p2)
        }

fun solution(segments: List<LineSegment>): Int {
    val overlapCount = mutableMapOf<Point, Int>()
    val overlappingPoints = mutableSetOf<Point>()

    segments
        .forEach { segment ->
            segment
                .walkTheLine()
                .forEach { point ->
                    if (overlapCount.merge(point, 1, Int::plus) == 2) {
                        overlappingPoints.add(point)
                    }
                }
        }

    return overlappingPoints.count()
}

fun solution1(segments: List<LineSegment>): Int {
    return solution(
        segments
            .filter { it.p1.x == it.p2.x || it.p1.y == it.p2.y }
    )
}

fun solution2(segments: List<LineSegment>): Int {
    return solution(segments)
}
