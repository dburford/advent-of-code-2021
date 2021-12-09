package day8

const val inputFile = "/day8.txt"

val samples = """
    be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
    edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
    fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
    fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
    aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
    fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
    dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
    bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
    egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
    gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce  
""".trimMargin()

val trueSignals = listOf(
    "abc efg", // 0
    "  c  f ", // 1
    "a cde g", // 2
    "a cd fg", // 3
    " bcd f ", // 4
    "ab d fg", // 5
    "ab defg", // 6
    "a c  f ", // 7
    "abcdefg", // 8
    "abcd fg", // 9
)

fun readData(str: String): List<Pair<List<String>, List<String>>> =
    str
        .lines()
        .map {
            it.split("|")
                .map(String::trim)
                .map {
                    it.split(" ")
                }.let { (signals, outputs) ->
                    Pair(signals, outputs)
                }
        }

fun solution1(displayEntries: List<Pair<List<String>, List<String>>>): Int {
    // 1: 2, 7: 3, 4: 4, 8: 7
    val uniqueCodes = listOf(2, 3, 4, 7)
    return displayEntries.fold(0, { acc, (signals, outputs) ->
        acc + outputs.filter {
            it.count() in uniqueCodes
        }.count()
    })
}

fun letterFrequencies(words: List<String>): Map<Char, Int> {
    val frequencies = mutableMapOf<Char, Int>()
    words
        .map { it.filterNot { it.isWhitespace() } }
        .fold(frequencies, { f, signals ->
            signals.fold(f, { f, i ->
                frequencies.merge(i, 1, Int::plus)
                frequencies
            })
        })
    return frequencies
}

fun noDupsInverted(m: Map<Char, Int>): Map<Int, Char> {
    return m
        .filterValues {
            it in m
                .toList()
                .groupingBy { it.second }
                .eachCount()
                .filterValues { it == 1 }.keys
        }
        .entries
        .associate { (k, v) ->
            v to k
        }
}

fun solution2(displayEntries: List<Pair<List<String>, List<String>>>): Int {

    val actualFrequencies = letterFrequencies(trueSignals)

    val wordsToDigits = trueSignals
        .map { it.filterNot { it.isWhitespace() } }
        .mapIndexed() { index, s ->
            Pair(s, index)
        }
        .toMap()

    val freqMap = noDupsInverted(actualFrequencies)

    return displayEntries
        .map {

            val (signals, outputs) = it
            val testFrequencies = letterFrequencies(signals)

            // match frequencies for all except ties: a,c (8) and d,g (7)
            val matches = noDupsInverted(testFrequencies).map { (k, v) ->
                Pair(freqMap[k], v)
            }.toMap().toMutableMap()

            // find "special" words with unique lengths
            // 1 -> cf (len 2)
            // 4 -> bcdf (len 4)
            // 7 -> acf (len 3)
            // 8 -> abcdefg (len 8)
            val oneEncoded = signals.filter { it.count() == 2 }.first()
            val fourEncoded = signals.filter { it.count() == 4 }.first()

            // determine match for 'c' by using word for '1' (cf) and eliminating 'f' which we know
            matches['c'] = oneEncoded.filterNot { it == matches['f'] }.first()

            // that then breaks the tie between a, c so we can determine a
            matches['a'] = testFrequencies
                .filterValues { it == 8 }
                .filterNot { (k, v) ->
                    k == matches['c']
                }.keys.first()

            // to break tie between d, g:
            // use the word for '4' (bcdf) which contains d but not g
            matches['d'] = testFrequencies
                .filterValues { it == 7 }
                .filterKeys {
                    it in fourEncoded
                }
                .keys.first()

            matches['g'] = testFrequencies
                .filterValues { it == 7 }
                .filterKeys {
                    it != matches['d']
                }
                .keys.first()

            val invMatches = matches.entries.associate { (k, v) ->
                v to k
            }

            outputs
                .map {
                    it.map {
                        invMatches[it]!!
                    }
                }
                .map { l ->
                    wordsToDigits[l.sorted().joinToString("")]
                }
                .fold(0) { acc, i ->
                    acc * 10 + i!!
                }
        }
        .sum()
}

fun main() {
    println(solution1(readData(samples)))
    println(solution2(readData(samples)))
}
