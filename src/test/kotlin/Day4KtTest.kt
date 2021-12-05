import day4.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day4KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        val (numbers, boards, lookup) = readData(samples)
        assertEquals(4512, solution1(numbers, boards, lookup))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val (numbers, boards, lookup) = readData(loadFromResource(inputFile))
        assertEquals(64084, solution1(numbers, boards, lookup))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        val (numbers, boards, lookup) = readData(samples)
        assertEquals(1924, solution2(numbers, boards, lookup))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val (numbers, boards, lookup) = readData(loadFromResource(inputFile))
        assertEquals(12833, solution2(numbers, boards, lookup))
    }
}
