package day6

const val inputFile = "/day6.txt"

const val samples = "3,4,3,1,2"

fun readData(str: String) =
    str
        .split(",")
        .map(String::trim)
        .map(String::toInt)

fun solution(initialState: List<Int>, daysToRun: Int): Long {

    var ageCounts = MutableList<Long>(9) { 0L }
    initialState.forEach { ageOfFish ->
        ageCounts[ageOfFish] += 1L
    }

    var dayZero = 0
    repeat(daysToRun) {
        val numReproducingFish = ageCounts[dayZero]

        // As the clock ticks over, numReproducingFish will move to position 8 (the "new" fish)
        // so we add the same number (the original fish) to position 6
        val daySix = (dayZero + 7) % 9
        ageCounts[daySix] += numReproducingFish

        // tick forward by 1 day on the 9 day clock
        dayZero = (dayZero + 1) % 9
    }

    // return the total number of fish in the population
    return ageCounts.sum()
}

fun solution1(initialState: List<Int>) = solution(initialState, 80)

fun solution2(initialState: List<Int>) = solution(initialState, 256)
