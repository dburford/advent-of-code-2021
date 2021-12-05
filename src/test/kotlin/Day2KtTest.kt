import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import day2.*

internal class Day2KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(150, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(2027977, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(900, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1903644897, solution2(testData))
    }
}
