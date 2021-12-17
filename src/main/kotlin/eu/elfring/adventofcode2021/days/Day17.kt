package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day17 : AoCPuzzle(17)
{
    override fun part1(input: List<String>): Any
    {
        val targetArea = parseTargetArea(input.first())

        calculateHeightIfTargetHit(targetArea, 0 to 0)

        var maxHeight = 0
        for (x in 0..targetArea.first.last)
        {
            for (y in 0..1000)
            {
                val calculatedHeight = calculateHeightIfTargetHit(targetArea, x to y)
                if (calculatedHeight != null && calculatedHeight > maxHeight)
                {
                    maxHeight = calculatedHeight
                }
            }
        }

        return maxHeight
    }

    override fun part2(input: List<String>): Any
    {
        val targetArea = parseTargetArea(input.first())

        calculateHeightIfTargetHit(targetArea, 0 to 0)

        var hits = 0
        for (x in 0..targetArea.first.last)
        {
            for (y in -1000..1000)
            {
                val calculatedHeight = calculateHeightIfTargetHit(targetArea, x to y)
                if (calculatedHeight != null)
                {
                    hits++
                }
            }
        }

        return hits
    }

    private fun parseTargetArea(input: String): Pair<IntRange, IntRange>
    {
        val regex = """target area: x=(\d+)..(\d+), y=(-\d+)..(-\d+)""".toRegex()
        val (minX, maxX, minY, maxY) = regex.matchEntire(input)!!.destructured.toList().map { it.toInt() }
        return minX..maxX to minY..maxY
    }

    private fun calculateHeightIfTargetHit(target: Pair<IntRange, IntRange>, initialHeading: Pair<Int, Int>): Int?
    {
        var position = 0 to 0
        var heading = initialHeading
        var maxHeight = 0
        var targetHit = false

        while (position.first < target.first.last && position.second > target.second.first)
        {
            position = position.first + heading.first to position.second + heading.second

            val headingX = when
            {
                heading.first > 0 -> heading.first - 1
                heading.first < 0 -> heading.first + 1
                else              -> 0
            }

            heading = headingX to heading.second - 1

            if (position.second > maxHeight)
            {
                maxHeight = position.second
            }
            if (position.first in target.first && position.second in target.second)
            {
                targetHit = true
            }
        }

        return if (targetHit) maxHeight else null
    }
}
