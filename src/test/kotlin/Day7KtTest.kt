import day7.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day7KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        val testData = readData(samples)
        assertEquals(37, solution1(testData))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(347449, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        val testData = readData(samples)
        assertEquals(168, solution2(testData))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(98039527, solution2(testData))
    }
}
