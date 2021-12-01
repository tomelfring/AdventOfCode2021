package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day01 : AoCPuzzle(1)
{
    override fun part1(input: List<String>): Any
    {
        return input.map { it.toInt() }.zipWithNext().count { it.first < it.second }
    }

    override fun part2(input: List<String>): Any
    {
        return input.map { it.toInt() }.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }
    }
}
