package day4

val inputFile = "/day4.txt"

val samples = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
        
        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7
    """.trimIndent()

data class Board(val rows: List<List<Int>>, val boardTotal: Int) {
    var rowCounts = MutableList(rows.count()) { 0 }
    var colCounts = MutableList(rows[0].count()) { 0 }
    var hitCount = 0

    fun hit(row: Int, col: Int): Boolean {
        rowCounts[row] += 1
        colCounts[col] += 1
        hitCount += rows[row][col]
        return (rowCounts[row] == rows.count() || colCounts[col] == rows[0].count())
    }

    fun print() {
        rows.forEach {
            it.forEach { print("$it ") }
            println()
        }
        println()
    }
}

data class IndexRecord(val b: Int, val r: Int, val c: Int) {
    fun print() {
        print("[ $b $r $c ] ")
    }
}

fun readData(str: String): Triple<List<Int>, List<Board>, MutableMap<Int, MutableList<IndexRecord>>> {

    // read numbers
    val lines = str.lines().map(String::trim)
    val numbers = lines.first().split(",").map(String::toInt)

    var lookup = mutableMapOf<Int, MutableList<IndexRecord>>()

    val boards = lines
        .drop(1)
        .windowed(6, step = 6)
        .mapIndexed { b, lines ->
            var boardTotal = 0
            lines
                .drop(1)
                .mapIndexed { r, line ->
                    line
                        .split("\\s+".toRegex())
                        .map(String::toInt)
                        // side effect: add to index, calc total
                        .also {
                            it.forEachIndexed { c, v ->
                                lookup
                                    .getOrPut(v) { mutableListOf<IndexRecord>() }
                                    .add(IndexRecord(b, r, c))
                                boardTotal += v
                            }
                        }
                }
                .let {
                    Board(it, boardTotal)
                }
        }

    return Triple(numbers, boards, lookup)
}

fun winningBoards(
    numbers: List<Int>,
    boards: List<Board>,
    lookup: MutableMap<Int, MutableList<IndexRecord>>
): Sequence<Pair<Board, Int>> =
    sequence() {

        var boardsPending = MutableList(boards.count()) { it }

        var n = 0;
        while (n < numbers.count() && !boardsPending.isEmpty()) {

            val records = lookup.getOrDefault(numbers[n], listOf());

            var r = 0
            while (r < records.count() && !boardsPending.isEmpty()) {
                val rec = records[r]
                val board = boards[rec.b]
                val completed = board.hit(rec.r, rec.c)

                if (completed) {
                    boardsPending.remove(rec.b)
                    yield(Pair(board, numbers[n]))
                }
                r += 1
            }
            n += 1
        }
    }


fun solution1(
    numbers: List<Int>,
    boards: List<Board>,
    lookup: MutableMap<Int, MutableList<IndexRecord>>
): Int =
    winningBoards(numbers, boards, lookup)
        .first()
        .let { (board, lastNumber) ->
            (board.boardTotal - board.hitCount) * lastNumber
        }


fun solution2(
    numbers: List<Int>,
    boards: List<Board>,
    lookup: MutableMap<Int, MutableList<IndexRecord>>
): Int =
    winningBoards(numbers, boards, lookup)
        .last()
        .let { (board, lastNumber) ->
            (board.boardTotal - board.hitCount) * lastNumber
        }
