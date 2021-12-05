package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day05 : AoCPuzzle(5)
{
    private val inputRegex = """(\d+).(\d+) -> (\d+).(\d+)""".toRegex()

    override fun part1(input: List<String>): Any
    {
        val lines = getLines(input)
        val drawnLines = drawLines(lines)

        return drawnLines.count { it.value >= 2 }
    }

    override fun part2(input: List<String>): Any
    {
        val lines = getLines(input, false)
        val drawnLines = drawLines(lines)

        return drawnLines.count { it.value >= 2 }
    }

    private fun drawLines(input: List<Line>): Map<Pair<Int, Int>, Int>
    {
        val output = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach { line ->
            if (!line.isDiagonal())
            {
                val xRange = if (line.x1 < line.x2) line.x1..line.x2 else line.x2..line.x1
                val yRange = if (line.y1 < line.y2) line.y1..line.y2 else line.y2..line.y1

                xRange.forEach { x ->
                    yRange.forEach { y ->
                        val newValue = output.getOrDefault(x to y, 0) + 1
                        output[x to y] = newValue
                    }
                }
            }
            else
            {
                val xRange = if (line.x1 < line.x2) line.x1..line.x2 else line.x1 downTo line.x2
                val yRange = if (line.y1 < line.y2) line.y1..line.y2 else line.y1 downTo line.y2

                xRange.forEachIndexed { index, x ->
                    val y = yRange.first + (index * yRange.step)
                    val newValue = output.getOrDefault(x to y, 0) + 1
                    output[x to y] = newValue
                }
            }
        }

        return output
    }

    private fun getLines(input: List<String>, filterDiagonals: Boolean = true): List<Line>
    {
        return input.map(::parseLine).filter { !(filterDiagonals && it.isDiagonal()) }
    }

    private fun parseLine(input: String): Line
    {
        val (x1, y1, x2, y2) = inputRegex.find(input)!!.destructured
        return Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
    }

    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int)
    {
        fun isDiagonal(): Boolean
        {
            return !(x1 == x2 || y1 == y2)
        }
    }
}
