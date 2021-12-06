package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day06

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 06")
internal class Day06Test
{
    private val input = """
        3,4,3,1,2
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(5934, Day06().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(26984457539, Day06().part2(input))
    }
}
