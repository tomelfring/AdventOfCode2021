package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day25 : AoCPuzzle(25)
{
    override fun part1(input: List<String>): Any
    {
        val seaFloor = getSeaFloor(input)

        var counter = 0
        while(seaFloor.doMove())
        {
            counter++
            if (counter % 10 == 0)
            {
                println(counter)
            }
        }
        return counter+1
    }

    override fun part2(input: List<String>): Any
    {
        return "Merry Christmas!"
    }

    private fun getSeaFloor(input: List<String>): SeaFloor
    {
        val seaCucumbers = buildList {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    when (char)
                    {
                        'v' -> add(SeaCucumber(Coordinate(x, y), Coordinate(0, 1)))
                        '>' -> add(SeaCucumber(Coordinate(x, y), Coordinate(1, 0)))
                    }
                }
            }
        }
        return SeaFloor(seaCucumbers, input.first().length, input.size)
    }

    private data class SeaFloor(var seaCucumbers: List<SeaCucumber>, val width: Int, val height: Int)
    {
        fun doMove(): Boolean
        {
            var moved = false
            seaCucumbers = buildList {
                seaCucumbers.filter { it.heading == Coordinate(1, 0) }.forEach {
                    val target = it.getTarget(width, height)
                    if (isCoordinateFree(target))
                    {
                        moved = true
                        add(SeaCucumber(target, it.heading))
                    }
                    else
                    {
                        add(it)
                    }
                }
                seaCucumbers.filter { it.heading == Coordinate(0, 1) }.forEach { add(it) }
            }
            seaCucumbers = buildList {
                seaCucumbers.filter { it.heading == Coordinate(1, 0) }.forEach { add(it) }
                seaCucumbers.filter { it.heading == Coordinate(0, 1) }.forEach {
                    val target = it.getTarget(width, height)
                    if (isCoordinateFree(target))
                    {
                        moved = true
                        add(SeaCucumber(target, it.heading))
                    }
                    else
                    {
                        add(it)
                    }
                }
            }
            return moved
        }

        fun isCoordinateFree(coordinate: Coordinate): Boolean
        {
            return !seaCucumbers.map { it.coordinate }.contains(coordinate)
        }
    }

    private data class SeaCucumber(val coordinate: Coordinate, val heading: Coordinate)
    {
        fun getTarget(width: Int, height: Int) = Coordinate((coordinate.x + heading.x) % width, (coordinate.y + heading.y) % height)
    }

    private data class Coordinate(val x: Int, val y: Int)
    {
        operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
    }
}
