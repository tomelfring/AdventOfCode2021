package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day09 : AoCPuzzle(9)
{
    override fun part1(input: List<String>): Any
    {
        var sum = 0
        val map = input.map { it.toCharArray().map(Char::digitToInt) }
        (map.indices).forEach { y ->
            (map[y].indices).forEach { x ->
                if (getNeighbours(map, x, y).all { it == null || it > map[y][x] })
                {
                    sum+= map[y][x]+1
                }
            }
        }
        return sum
    }

    override fun part2(input: List<String>): Any
    {
        val map = input.map { it.toCharArray().map(Char::digitToInt) }

        val lowestPoints = mutableListOf<Pair<Int, Int>>()

        (map.indices).forEach { y ->
            (map[y].indices).forEach { x ->
                if (getNeighbours(map, x, y).all { it == null || it > map[y][x] })
                {
                    lowestPoints.add(x to y)
                }
            }
        }

        val basinSizes = lowestPoints.map {
            getBasinSize(map, it.first, it.second)
        }

        return basinSizes.sorted().takeLast(3).reduce{ x, y -> x * y }
    }

    private fun getBasinSize(map: List<List<Int>>, x: Int, y: Int, sum: Int = 0, visited: MutableSet<Pair<Int, Int>> = mutableSetOf()): Int
    {
        var basinSize = sum
        if (!visited.contains(x to y))
        {
            visited.add(x to y)
            if (map[y][x] < 9)
            {
                basinSize++
                if (y != 0)
                {
                    basinSize = getBasinSize(map, x, y-1, basinSize, visited)
                }
                if (x != 0)
                {
                    basinSize = getBasinSize(map, x-1, y, basinSize, visited)
                }
                if (x+1 < map[y].size)
                {
                    basinSize = getBasinSize(map, x+1, y, basinSize, visited)
                }
                if (y+1 < map.size)
                {
                    basinSize = getBasinSize(map, x, y+1, basinSize, visited)
                }
            }
        }

        return basinSize
    }


    private fun getNeighbours(map: List<List<Int>>, x: Int, y: Int): List<Int?>
    {
        return listOf(
            if (y != 0) map[y-1][x] else null, // Top
            if (x != 0) map[y][x-1] else null, // Left
            if (x+1 < map[y].size) map[y][x+1] else null, // Right
            if (y+1 < map.size) map[y+1][x] else null // Bottom
        )
    }
}
