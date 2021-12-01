package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day09

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 09")
internal class Day09Test
{
    private val input = """
        TODO INPUT
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(0, Day09().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(0, Day09().part2(input))
    }
}
