import day6.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        val testData = readData(samples)
        assertEquals(5934, solution1(testData))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(395627, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        val testData = readData(samples)
        assertEquals(26984457539, solution2(testData))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1767323539209, solution2(testData))
    }
}
