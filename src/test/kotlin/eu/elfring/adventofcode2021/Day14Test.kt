package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day14

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 14")
internal class Day14Test
{
    private val input = """
        NNCB
        
        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(1588L, Day14().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(2188189693529, Day14().part2(input))
    }
}
