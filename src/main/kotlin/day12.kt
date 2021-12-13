package day12

const val inputFile = "/day12.txt"

/*

start-A
start-b
A-c
A-b
b-d
A-end
b-end

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
 */
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

fun readData(str: String) =
    str
        .lines()
        .map(String::trim)
        .map {
            it.split("-")
        }
        .map {
            if (it[1] == "start")
                listOf(it[1], it[0])
            else
                it
        }
        .groupBy { it[0] }
        .mapValues {
            it.value.map {
                it[1]
            }
        }

var paths = mutableListOf<String>()

fun dfs(
    node: String,
    adjList: Map<String, List<String>>,
    chain: List<String>,
    visited: Set<String>,
    allowSecondVisit: Boolean,
    hadSecondVisit: Boolean
) {
    val c = chain + listOf(node)

    if (node == "end") {
        paths.add(c.reversed().joinToString())
        return
    }

    val hSV = node in visited || hadSecondVisit
    val v = if (node.first().isLowerCase()) visited + node else visited

    adjList[node]?.forEach { node->
        if (node !in visited || (allowSecondVisit && !hSV)) {
            dfs(node, adjList, c, v, allowSecondVisit, hSV)
        }
    }
}

fun solution(adjList: Map<String, List<String>>, allowSecondVisit: Boolean): Int {

    paths.clear()

    // copy entries to mutable list
    val a = mutableMapOf<String, MutableList<String>>()
    adjList.forEach {
        a[it.key] = it.value.sorted().toMutableList()
    }

    // add reverse paths between nodes
    adjList
        .filterKeys { it != "start" }
        .forEach {
            it.value
                .filterNot { s ->
                    s == "end"
                }
                .forEach { v ->
                    a.merge(v, mutableListOf(it.key)) { a, b ->
                        a.addAll(b)
                        a.sorted().toMutableList()
                    }
                }
        }

    println(a)

    dfs("start", a, listOf<String>(), setOf<String>(), allowSecondVisit, false)

//    paths.forEach { println(it) }

    return paths.count()
}

fun solution1(adjList: Map<String, List<String>>) = day12.solution(adjList, false)
fun solution2(adjList: Map<String, List<String>>) = day12.solution(adjList, true)

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
