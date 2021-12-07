package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import kotlin.math.absoluteValue

class Day07 : AoCPuzzle(7)
{
    override fun part1(input: List<String>): Any
    {
        return calculateLeastFuelUsage(input.first()) { it }
    }

    override fun part2(input: List<String>): Any
    {
        return calculateLeastFuelUsage(input.first()) { ((it / 2.0) * (it + 1)).toInt() }
    }

    private fun calculateLeastFuelUsage(input: String, fuelCalculation: (Int) -> Int): Int
    {
        val mapped = input.split(",").map { it.toInt() }
        val min = mapped.minOf { it }
        val max = mapped.maxOf { it }

        return (min..max).map { horPos -> mapped.sumOf { crabPos -> fuelCalculation((crabPos - horPos).absoluteValue) } }.minOf { it }
    }
}
