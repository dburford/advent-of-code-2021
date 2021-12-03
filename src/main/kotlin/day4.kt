package day4

val inputFile = "/day4.txt"

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

fun readData(str: String) : List<List<Int> > {
    return str
        .lines()
        .filterNot { it.isBlank() }
        .map(String::trim)
        .map(String::toList)
        .map {
            it.map(Character::getNumericValue)
        }
}

fun solution1(bitvals: List<List<Int>>) : Int {
    return 0
}

fun solution2(bitvals: List<List<Int>>) : Int {
    return 0
}









