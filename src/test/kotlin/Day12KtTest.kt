import day12.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day12KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        val testData = readData(samples)
        assertEquals(19, solution1(testData))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(4885, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        val testData = readData(samples)
        assertEquals(103, solution2(testData))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(117095, solution2(testData))
    }
}
