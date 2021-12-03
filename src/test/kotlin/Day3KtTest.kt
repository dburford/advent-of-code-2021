import day3.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day3KtTest {

    @Test
    fun `should convert string to binary`() {
        assertEquals(25, listOf(1,1,0,0,1).toBinaryVal())
    }

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals( 198, solution1( readData(samples) ))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData( loadFromResource(inputFile) )
        assertEquals( 1025636, solution1( testData ))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals( 230, solution2( readData(samples) ))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData( loadFromResource(inputFile) )
        assertEquals( 793873, solution2( testData ))
    }
}