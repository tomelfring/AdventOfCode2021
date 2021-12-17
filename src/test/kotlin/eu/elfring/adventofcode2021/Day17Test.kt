package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day17

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 17")
internal class Day17Test
{
    private val input = """
        target area: x=20..30, y=-10..-5
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(45, Day17().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(112, Day17().part2(input))
    }
}
