package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day07

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 07")
internal class Day07Test
{
    private val input = """
        16,1,2,0,4,2,7,1,2,14
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(37, Day07().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(168, Day07().part2(input))
    }
}
