package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day02

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 02")
internal class Day02Test
{
    private val input = """
        TODO INPUT
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(0, Day02().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(0, Day02().part2(input))
    }
}
