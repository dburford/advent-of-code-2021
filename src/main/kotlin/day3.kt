package day3

val inputFile = "/day3.txt"

val samples = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent()

fun readData(str: String): List<List<Int>> {
    return str
        .lines()
        .filterNot { it.isBlank() }
        .map(String::trim)
        .map(String::toList)
        .map {
            it.map(Character::getNumericValue)
        }
}

fun List<Int>.element_add(v: List<Int>) =
    this.zip(v) { a, b -> a + b }

fun List<Int>.toBinaryVal() =
    this.fold(0, { acc, i -> (acc shl 1) + i })

fun List<Int>.flipBinary() =
    this.map { if (it == 1) 0 else 1 }

fun List<Int>.print() {
    println(this.joinToString(""))
}

fun mostCommon(bitvals: List<List<Int>>) =
    bitvals
        .reduce { acc, l ->
            acc.element_add(l)
        }
        .map {
            if (it * 2 >= bitvals.count()) 1 else 0
        }

fun leastCommon(bitvals: List<List<Int>>) =
    mostCommon(bitvals).flipBinary()

fun solution1(bitvals: List<List<Int>>): Int {
    return mostCommon(bitvals).let {
        it.toBinaryVal() * it.flipBinary().toBinaryVal()
    }
}

fun filterByMask(bitvals: List<List<Int>>, fn: (List<List<Int>>) -> List<Int>): List<Int> {
    var bit = 0
    var temp = bitvals
    while (temp.count() > 1) {
        val mask = fn(temp)
        temp = temp.filter {
            it[bit] == mask[bit]
        }
        bit += 1
    }
    return temp.first()
}

fun solution2(bitvals: List<List<Int>>): Int {
    val v1 = filterByMask(bitvals, ::mostCommon)
    val v2 = filterByMask(bitvals, ::leastCommon)
    return v1.toBinaryVal() * v2.toBinaryVal()
}
