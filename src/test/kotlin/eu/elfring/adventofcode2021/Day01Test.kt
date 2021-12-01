package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day01

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 01")
internal class Day01Test
{
    private val input = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(7, Day01().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(5, Day01().part2(input))
    }
}
