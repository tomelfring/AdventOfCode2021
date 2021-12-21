package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day21

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 21")
internal class Day21Test
{
    private val input = """
        Player 1 starting position: 4
        Player 2 starting position: 8
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(739785, Day21().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(444356092776315, Day21().part2(input))
    }
}
