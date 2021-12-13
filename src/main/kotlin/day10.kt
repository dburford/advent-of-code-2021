package day10

import median

const val inputFile = "/day10.txt"

val samples = """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
""".trimMargin()

fun readData(str: String) =
    str
        .lines()
        .map(String::trim)
        .map {
            it.toList()
        }

val bracketMap = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

fun solution1(input: List<List<Char>>) =
    input.map { line ->
        var score = 0
        val stack = mutableListOf<Char>()
        val corruptPoints = mutableMapOf<Char, Int>(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

        for (c in line.indices) {
            val ch = line[c]
            if (ch in bracketMap.values) {
                if (ch != bracketMap[stack.removeLast()]) {
                    score += corruptPoints[ch]!!
                    break
                }
            } else {
                stack.add(ch)
            }
        }
        score
    }.sum()


fun solution2(input: List<List<Char>>) =
    input
        .map { line ->
            val stack = mutableListOf<Char>()
            val autoCompletePoints = mutableMapOf<Char, Long>('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)

            for (c in line.indices) {
                val ch = line[c]
                if (ch in bracketMap.values) {
                    if (ch != bracketMap[stack.last()]) {
                        stack.clear()
                        break
                    }
                    stack.removeLast()
                } else {
                    stack.add(ch)
                }
            }
            stack.foldRight(0L) { ch, acc ->
                acc * 5L + autoCompletePoints[ch]!!
            }
        }
        .filter { it > 0 }
        .median()

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
