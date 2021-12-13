import day13.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        val testData = readData(samples)
        assertEquals(17, solution1(testData))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(814, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        val testData = readData(samples)
        assertEquals(0, solution2(testData))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(0, solution2(testData))
    }
}
