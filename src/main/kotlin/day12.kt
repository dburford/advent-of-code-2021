package day12

const val inputFile = "/day12.txt"

val samples = """
dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc
""".trimMargin()

fun readData(str: String): MutableMap<String, List<String>> {
    val adjList = mutableMapOf<String, List<String>>()
    str
        .lines()
        .map { it.split("-") }
        .map { (src, dest) ->
            if (dest == "start") listOf(dest, src) else listOf(src, dest)
        }
        .forEach { (src, dest) ->
            adjList[src] = adjList[src].orEmpty() + listOf(dest)
            if (src != "start")
                adjList[dest] = adjList[dest].orEmpty() + listOf(src)
        }
    return adjList
}

fun dfs(
    node: String,
    adjList: Map<String, List<String>>,
    visited: Set<String>,
    allowSecondVisit: Boolean,
    hadSecondVisit: Boolean
): Int {
    if (node == "end")
        return 1

    val hSV = node in visited || hadSecondVisit
    val v = if (node.first().isLowerCase()) visited + node else visited

    var count = 0
    adjList[node]?.forEach { node ->
        if (node !in visited || (allowSecondVisit && !hSV)) {
            count += dfs(node, adjList, v, allowSecondVisit, hSV)
        }
    }
    return count
}

fun solution(adjList: Map<String, List<String>>, allowSecondVisit: Boolean): Int {
    return dfs("start", adjList, setOf<String>(), allowSecondVisit, false)
}

fun solution1(adjList: Map<String, List<String>>) = day12.solution(adjList, false)
fun solution2(adjList: Map<String, List<String>>) = day12.solution(adjList, true)

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
