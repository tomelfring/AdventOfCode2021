package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import java.util.PriorityQueue

class Day15 : AoCPuzzle(15)
{
    override fun part1(input: List<String>): Any
    {
        val grid = mutableMapOf<Pair<Int, Int>, GridItem?>().withDefault { null }.apply {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, value ->
                    put(x to y, GridItem(x, y, value.digitToInt()))
                }
            }
        }

        val maxSize = grid.maxOf { it.key.first }
        val astar = AStar(grid, 0 to 0, maxSize to maxSize)

        return astar.drop(1).sumOf { grid[it]!!.value }
    }

    override fun part2(input: List<String>): Any
    {
        val grid = mutableMapOf<Pair<Int, Int>, GridItem?>().withDefault { null }.apply {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, value ->
                    put(x to y, GridItem(x, y, value.digitToInt()))
                }
            }

            repeat(4) { count ->
                for (x in input.indices)
                {
                    for (y in input.indices)
                    {
                        val newX = ((count+1)*input.size)+x
                        val newY = y
                        var newValue = get(x to y)!!.value + count + 1
                        if (newValue > 9) newValue-=9

                        put(newX to newY, GridItem(newX, newY, newValue))
                    }
                }
            }

            // Remaining 4 rows
            val size = this.maxOf { it.key.first }

            repeat(4) { count ->
                for (x in 0..size)
                {
                    for (y in input.indices)
                    {
                        val newX = x
                        val newY = ((count+1)*input.size)+y
                        var newValue = get(x to newY-1*input.size)!!.value + 1
                        if (newValue > 9) newValue-=9

                        put(newX to newY, GridItem(newX, newY, newValue))
                    }
                }
            }
            println("a")
        }

        val maxSize = grid.maxOf { it.key.first }
        val astar = AStar(grid, 0 to 0, maxSize to maxSize)

        return astar.drop(1).sumOf { grid[it]!!.value }
    }

    private fun AStar(grid: Map<Pair<Int, Int>, GridItem?>, start: Pair<Int, Int>, end: Pair<Int, Int>): List<Pair<Int, Int>>
    {
        //<Priority, <X, Y>>
        val comparator: Comparator<Pair<Int, Pair<Int, Int>>> = compareBy { it.first }
        val openSet = PriorityQueue(comparator)
        openSet.add(0 to start)

        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        val gScore = mutableMapOf<Pair<Int, Int>, Int>()
        gScore[start] = 0

        val fScore = mutableMapOf<Pair<Int, Int>, Int>()
        fScore[start] = 0

        val maxSize = grid.maxOf { it.key.first }

        while (openSet.isNotEmpty())
        {
            val current = openSet.remove().second

            if (current == end)
            {
                return reconstructPath(cameFrom, current)
            }

            for (neighbour in grid[current]!!.getNeighbours(grid))
            {
                val tentative_gScore = gScore[current]!! + grid[neighbour.getXY()]!!.value
                if (tentative_gScore < gScore.getOrDefault(neighbour.getXY(), Int.MAX_VALUE))
                {
                    cameFrom[neighbour.getXY()] = current
                    gScore[neighbour.getXY()] = tentative_gScore
                    fScore[neighbour.getXY()] = tentative_gScore + (maxSize*2 - neighbour.x - neighbour.y) //Manhatten distance
                    if (openSet.none { it.second == neighbour.getXY() })
                    {
                        openSet.add(fScore[neighbour.getXY()]!! to neighbour.getXY())
                    }
                }
            }
        }

        return emptyList()
    }

    private fun reconstructPath(cameFrom: MutableMap<Pair<Int, Int>, Pair<Int, Int>>, current: Pair<Int, Int>): List<Pair<Int, Int>>
    {
        val result = mutableListOf<Pair<Int, Int>>()

        var toCheck = current
        result.add(toCheck)
        while(cameFrom.contains(toCheck))
        {
            toCheck = cameFrom[toCheck]!!
            result.add(toCheck)
        }
        return result.reversed()
    }

    data class GridItem(val x: Int, val y: Int, var value: Int)
    {
        fun getNeighbours(grid: Map<Pair<Int, Int>, GridItem?>): List<GridItem>
        {
            return listOfNotNull(
                grid[x to y-1],
                grid[x-1 to y  ], grid[x+1 to y],
                grid[x to y+1])
        }

        fun getXY(): Pair<Int, Int>
        {
            return x to y
        }
    }
}
