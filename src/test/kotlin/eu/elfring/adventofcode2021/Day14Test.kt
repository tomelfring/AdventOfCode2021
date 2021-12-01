package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day14

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 14")
internal class Day14Test
{
    private val input = """
        TODO INPUT
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(0, Day14().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(0, Day14().part2(input))
    }
}
