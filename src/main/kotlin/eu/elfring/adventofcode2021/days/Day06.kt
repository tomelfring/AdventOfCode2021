package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day06 : AoCPuzzle(6)
{
    override fun part1(input: List<String>): Any
    {
        var fishMap = parseInput(input.first())

        (1..80).forEach { _ ->
            fishMap = tick(fishMap, 6, 8)
        }

        return fishMap.values.sum()
    }

    override fun part2(input: List<String>): Any
    {
        var fishMap = parseInput(input.first())

        (1..256).forEach { _ ->
            fishMap = tick(fishMap, 6, 8)
        }

        return fishMap.values.sumOf { it }
    }

    private fun tick(fishMap: Map<Int, Long>, oldFish: Int, newFish: Int): Map<Int, Long>
    {
        val newFishMap = mutableMapOf<Int, Long>()
        (0 until newFish).forEach {
            newFishMap[it] = fishMap.getOrDefault(it + 1, 0)
        }

        val newFishCount = fishMap.getOrDefault(0, 0)
        newFishMap[oldFish] = newFishMap.getOrDefault(oldFish, 0) + newFishCount
        newFishMap[newFish] = newFishCount

        return newFishMap
    }

    private fun parseInput(input: String): Map<Int, Long>
    {
        return input.split(",").map { it.toInt() }.groupBy { it }.mapValues { it.value.count().toLong() }
    }
}
