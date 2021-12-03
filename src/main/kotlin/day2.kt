package day2

val inputFile = "/day2.txt"
val samples = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2        
    """.trimIndent()


data class Delta( val dx: Int, val dy: Int  )

fun readData(str: String) : List<Delta> {
    return str
        .lines()
        .filterNot { it.isBlank() }
        .map {
            it.split(" ")
        }
        .map { (dir, num) ->
            val n = num.toInt()
            when (dir) {
                "forward"   -> Delta(n, 0)
                "up"        -> Delta(0, -n)
                "down"      -> Delta(0, n)
                else        -> throw Exception("Invalid input")
            }
        }
}

fun solution1(deltas: List<Delta>) =
    deltas
        .reduce { (h, d), (dx, dy) ->
            Delta(
                h + dx,
                d + dy
            )
        }
        .let {it.dx * it.dy}


data class DOF(val h: Int, val d: Int, val aim: Int)

fun solution2(deltas: List<Delta>) =
    deltas
        .fold( DOF(0,0,0), { (h, d, aim), (dx, da) ->
                DOF(
                    h + dx,
                    d + dx * aim,
                    aim + da
                )
            }
        )
        .let {it.h * it.d}








