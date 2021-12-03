package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day03 : AoCPuzzle(3)
{
    override fun part1(input: List<String>): Any
    {
        val mappedInput = input.map { line -> line.toCharArray().map { char -> char.digitToInt() } }

        val firstValue = mappedInput.mostCommonBits()
        val secondValue = firstValue.map { it xor 1 }

        return firstValue.joinToString("").toInt(2) * secondValue.joinToString("").toInt(2)
    }

    override fun part2(input: List<String>): Any
    {
        val mappedInput = input.map { line -> line.toCharArray().map { char -> char.digitToInt() } }

        val oxygenRating = solvePart2(mappedInput) { currentBit, mostCommonBit -> currentBit != mostCommonBit }
        val scrubberRating = solvePart2(mappedInput) { currentBit, mostCommonBit -> currentBit == mostCommonBit }

        return oxygenRating * scrubberRating
    }

    /**
     * Get a list of most common entries (int) per bit
     */
    private fun List<List<Int>>.mostCommonBits() = first().indices
        .map { idx -> count { it[idx] == 1 } }
        .map { if (it >= size - it) 1 else 0 }


    private fun solvePart2(input: List<List<Int>>, test: (Int, Int) -> Boolean): Int
    {
        val ratings = input.toMutableList()
        var bitIndex = 0
        while (ratings.size > 1)
        {
            val mostCommonAtCurrentBit = ratings.mostCommonBits()[bitIndex]

            ratings.removeIf { rating -> test(rating[bitIndex], mostCommonAtCurrentBit) }
            bitIndex++
        }
        return ratings.first().joinToString("").toInt(2)
    }
}
