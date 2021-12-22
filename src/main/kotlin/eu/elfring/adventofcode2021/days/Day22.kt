package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import kotlin.math.max
import kotlin.math.min

class Day22 : AoCPuzzle(22)
{
    override fun part1(input: List<String>): Any
    {
        val instructions = input.map { Instruction.of(it).limitToRange(-50..50) }

        return calculate(instructions)
    }

    override fun part2(input: List<String>): Any
    {
        val instructions = input.map(Instruction::of)

        return calculate(instructions)
    }

    private fun calculate(instructions: List<Instruction>): Long
    {
        var count = 0L
        instructions.forEachIndexed { index, instruction ->
            val cubeCount = instruction.area.getCount() - getOverlap(instructions.subList(0, index), instruction.area, instruction.on)
            if (instruction.on)
            {
                count += cubeCount
            }
            else
            {
                count -= cubeCount
            }
        }
        return count
    }

    private fun getOverlap(instructions: List<Instruction>, area: Area, on: Boolean): Long
    {
        var overlap = if (on) 0L else area.getCount()
        instructions.forEachIndexed { index, instruction ->
            val overlapArea = instruction.area.getOverlap(area)
            var value = overlapArea?.getCount() ?: 0L

            if (overlapArea != null)
            {
                value -= getOverlap(instructions.subList(0, index), overlapArea, instruction.on)
            }

            if (instruction.on != on)
            {
                overlap -= value
            }
            else
            {
                overlap += value
            }
        }
        return overlap
    }

    data class Instruction(val area: Area, val on: Boolean)
    {
        fun limitToRange(range: IntRange) = Instruction(area.limitToRange(range), on)

        companion object
        {
            private val instructionRegex = """(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""".toRegex()

            fun of(input: String): Instruction
            {
                val (on, x1, x2, y1, y2, z1, z2) = instructionRegex.matchEntire(input)!!.destructured
                return Instruction(
                    Area(
                        Coordinate(min(x1.toInt(), x2.toInt()), min(y1.toInt(), y2.toInt()), min(z1.toInt(), z2.toInt())),
                        Coordinate(max(x1.toInt(), x2.toInt()), max(y1.toInt(), y2.toInt()), max(z1.toInt(), z2.toInt()))
                    ),
                    on == "on"
                )
            }
        }
    }

    data class Area(val from: Coordinate, val to: Coordinate)
    {
        private fun getRanges() = Triple(from.x..to.x, from.y..to.y, from.z..to.z)

        fun getCount(): Long = getRanges().let { it.first.count().toLong() * it.second.count() * it.third.count() }

        fun getOverlap(other: Area): Area?
        {
            val x1 = max(other.from.x, from.x)
            val y1 = max(other.from.y, from.y)
            val z1 = max(other.from.z, from.z)

            val x2 = min(other.to.x, to.x)
            val y2 = min(other.to.y, to.y)
            val z2 = min(other.to.z, to.z)

            return if (x2 < x1 || y2 < y1 || z2 < z1)
            {
                null
            }
            else
            {
                Area(Coordinate(x1, y1, z1), Coordinate(x2, y2, z2))
            }
        }

        fun limitToRange(range: IntRange): Area
        {
            val x1 = max(from.x, range.first)
            val y1 = max(from.y, range.first)
            val z1 = max(from.z, range.first)

            val x2 = min(to.x, range.last)
            val y2 = min(to.y, range.last)
            val z2 = min(to.z, range.last)

            return Area(Coordinate(x1, y1, z1), Coordinate(x2, y2, z2))
        }
    }

    data class Coordinate(val x: Int, val y: Int, val z: Int)
}
