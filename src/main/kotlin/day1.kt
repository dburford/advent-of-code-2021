package day1

val inputFile = "/day1.txt"
val samples = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

fun readData(str: String): List<Int> {
    return str
        .lines()
        .filterNot { it.isBlank() }
        .map { it.toInt() }
}

// day 1 #1
fun solution1(measurements: List<Int>) =
    measurements
        .zipWithNext { a, b -> b - a }
        .filter { it > 0 }
        .count()

// day 1 #2
fun solution2(measurements: List<Int>) =
    measurements
        .windowed(3) { it.sum() }
        .let {
            solution1(it)
        }
