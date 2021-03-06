package day13

const val inputFile = "/day13.txt"

val samples = """
6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5
""".trimMargin()

fun readData(str: String): Pair<List<Pair<Int, Int>>, List<Pair<Char, Int>>> {
    val code = str
        .lines()
        .filterNot { it.startsWith("fold") }
        .filter { it.isNotEmpty() }
        .map {
            it
                .split(",")
                .map {
                    it.toInt()
                }
        }
        .map { (x, y) ->
            Pair(x, y)
        }

    val folds = str
        .lines()
        .filter { it.startsWith("fold") }
        .map {
            val (c, v) = """fold along (.)=(\d+)""".toRegex().find(it)!!.destructured
            Pair<Char, Int>(c[0], v.toInt())
        }

    return Pair(code, folds)
}


fun foldX(code: List<Pair<Int, Int>>, value: Int): List<Pair<Int, Int>> {
    val base = code
        .filter { (x, y) -> x < value }

    val folded = code
        .filter { (x, y) -> x > value }
        .map { (x, y) ->
            Pair(value - (x - value), y)
        }

    val s = base.toMutableSet().apply { addAll(folded) }
    return s.toList()
}

fun printGrid(grid: List<List<Char>>) {
    grid.forEach {
        it.forEach {
            print("$it")
        }
        println()
    }
}

fun printCode(code: List<Pair<Int, Int>>, fold: Int) {
    val maxX = code.maxOf { (x, _) -> x }
    val maxY = code.maxOf { (_, y) -> y }

    val grid = MutableList(maxY + 1) { MutableList(maxX + 1) { '.' } }
    code.forEach { (x, y) -> grid[y][x] = '#' }

    if (fold > 0) {
        grid[fold].indices.forEach {
            grid[fold][it] = '-'
        }
    }
    printGrid(grid)
}

fun transform(code: List<Pair<Int, Int>>) = code.map { (x, y) -> Pair(y, x) }

fun solution1(input: Pair<List<Pair<Int, Int>>, List<Pair<Char, Int>>>): Int {
    val (code, folds) = input

    val (axis, value) = folds[0]
    val s = when (axis) {
        'x' -> foldX(code, value)
        'y' -> transform(foldX(transform(code), value))
        else -> throw Exception("Unexpected input")
    }

    return s.count()
}

fun solution2(input: Pair<List<Pair<Int, Int>>, List<Pair<Char, Int>>>): Int {
    val (code, folds) = input

    var s = code
    folds.forEach { (axis, value) ->
        s = when (axis) {
            'x' -> foldX(s, value)
            'y' -> transform(foldX(transform(s), value))
            else -> throw Exception("Unexpected input")
        }
    }

    printCode(s, -1)
    return 0
}

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
