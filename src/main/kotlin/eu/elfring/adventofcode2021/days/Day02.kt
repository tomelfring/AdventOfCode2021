package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day02 : AoCPuzzle(2)
{
    override fun part1(input: List<String>): Any
    {
        val values = input.map(::toDirs).reduce { a, b -> a.first + b.first to a.second + b.second }
        return values.first * values.second
    }

    override fun part2(input: List<String>): Any
    {
        val values = input.map(::toDirs).fold(Part2(0, 0, 0)) { acc, value ->
            Part2(acc.horizontalPosition + value.first,
                  acc.depth + value.first * acc.aim,
                  acc.aim + value.second)
        }

        return values.horizontalPosition * values.depth
    }

    private fun toDirs(input: String): Pair<Int, Int>
    {
        val steps = input.substringAfter(" ").toInt()
        return when (input.substringBefore(" "))
        {
            "forward" -> steps to 0
            "down"    -> 0 to steps
            "up"      -> 0 to -steps
            else      -> throw IllegalArgumentException("Unknown statement")
        }
    }

    data class Part2(val horizontalPosition: Int, val depth: Int, val aim: Int)
}
