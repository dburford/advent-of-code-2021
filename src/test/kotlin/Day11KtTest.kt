import day11.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day11KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        val testData = readData(samples)
        assertEquals(1656, solution1(testData, 100))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1691, solution1(testData, 100))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        val testData = readData(samples)
        assertEquals(195, solution2(testData))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(216, solution2(testData))
    }
}
