package day14

import mapToFrequenciesLong

const val inputFile = "/day14.txt"

val samples = """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C
""".trimMargin()

fun readData(str: String): Pair<String, Map<String, String>> {
    val (template, mappingStr) = str
        .split("\n\n")

    val mappings = mappingStr
        .lines()
        .map { it.split("->") }
        .map {
            it.map(String::trim)
        }
        .associate { (k, v) -> Pair(k, v) }

    return Pair(template, mappings)
}

fun MutableMap<String, Long>.mergeCounts(first: Map<String, Long>): MutableMap<String, Long> {
    first.forEach {
        this[it.key] = this.getOrDefault(it.key, 0) + it.value
    }
    return this
}

var cache: MutableMap<String, Map<String, Long>> = mutableMapOf()

fun putCache(code: String, depth: Int, counts: Map<String, Long>) {
    cache["$code$depth"] = counts
}

fun getCache(code: String, depth: Int) =
    cache.getOrDefault("$code$depth", null)

fun dfs(
    code: String,
    depth: Int,
    mappings: Map<String, String>
): Map<String, Long> {

    getCache(code, depth)?.let { return it }

    if (depth == 0) return mapOf()

    val ch = mappings.getOrElse(code) {
        throw Exception("No mapping found!")
    }

    val counts = mutableMapOf(ch to 1L)
        .mergeCounts(dfs("${code[0]}$ch", depth - 1, mappings))
        .mergeCounts(dfs("$ch${code[1]}", depth - 1, mappings))
    putCache(code, depth, counts)

    return counts
}

fun solution(input: Pair<String, Map<String, String>>, depth: Int): Long {
    val (template, mappings) = input

    var counts = mutableMapOf<String, Long>()
    template.forEach {
        counts["$it"] = counts.getOrDefault("$it", 0) + 1
    }

    template
        .windowed(2)
        .forEach { counts.mergeCounts(dfs(it, depth, mappings)) }

    return counts
        .map { it.value }
        .sorted()
        .let { it.last() - it.first() }
}

fun solution1(input: Pair<String, Map<String, String>>) = solution(input, 10)
fun solution2(input: Pair<String, Map<String, String>>) = solution(input, 40)

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
