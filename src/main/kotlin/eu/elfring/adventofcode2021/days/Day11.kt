package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day11 : AoCPuzzle(11)
{
    override fun part1(input: List<String>): Any
    {
        val splitInput = input.map { it.toCharArray().map{ char -> char.digitToInt() to false }.toMutableList() }.toMutableList()

        var sum = 0
        repeat(100) {
            sum += doTick(splitInput)
        }

        return sum
    }

    override fun part2(input: List<String>): Any
    {
        val splitInput = input.map { it.toCharArray().map{ char -> char.digitToInt() to false }.toMutableList() }.toMutableList()

        val octoCount = splitInput.size * splitInput.first().size

        var count = 1
        while (doTick(splitInput) != octoCount)
        {
            count++
        }
        return count
    }

    private fun doTick(input: MutableList<MutableList<Pair<Int, Boolean>>>): Int
    {
        // Add one to every value
        input.forEachIndexed { y, pairs ->
            pairs.forEachIndexed{ x, value ->
                input[y][x] = value.first+1 to value.second
            }
        }

        // Do flashes
        doFlashes(input)

        // Count flashes & reset to 0 if > 9
        var flashes = 0
        input.forEachIndexed { y, pairs ->
            pairs.forEachIndexed { x, pair ->
                if (pair.first > 9 && pair.second)
                {
                    flashes++
                    input[y][x] = 0 to false
                }
            }
        }

        return flashes
    }

    private fun doFlashes(input: MutableList<MutableList<Pair<Int, Boolean>>>)
    {
        var hasAnyFlashed = true
        while (hasAnyFlashed)
        {
            hasAnyFlashed = false
            input.forEachIndexed { y, pairs ->
                pairs.forEachIndexed { x, pair ->
                    if (pair.first > 9 && !pair.second)
                    {
                        hasAnyFlashed = true
                        input[y][x] = pair.first to true

                        if (y > 0)
                        {
                            // y-1 x-1
                            if (x > 0)
                            {
                                input[y-1][x-1] = input[y-1][x-1].first+1 to input[y-1][x-1].second
                            }
                            // y-1 x
                            input[y-1][x] = input[y-1][x].first+1 to input[y-1][x].second
                            // y-1 x+1
                            if (x < pairs.size-1)
                            {
                                input[y-1][x+1] = input[y-1][x+1].first+1 to input[y-1][x+1].second
                            }
                        }

                        // y x-1
                        if (x > 0)
                        {
                            input[y][x-1] = input[y][x-1].first+1 to input[y][x-1].second
                        }
                        // y x+1
                        if (x < pairs.size-1)
                        {
                            input[y][x+1] = input[y][x+1].first+1 to input[y][x+1].second
                        }

                        if (y < input.size-1)
                        {
                            // y+1 x-1
                            if (x > 0)
                            {
                                input[y+1][x-1] = input[y+1][x-1].first+1 to input[y+1][x-1].second
                            }
                            // y+1 x
                            input[y+1][x] = input[y+1][x].first+1 to input[y+1][x].second
                            // y+1 x+1
                            if (x < pairs.size-1)
                            {
                                input[y+1][x+1] = input[y+1][x+1].first+1 to input[y+1][x+1].second
                            }
                        }
                    }
                }
            }
        }
    }
}
