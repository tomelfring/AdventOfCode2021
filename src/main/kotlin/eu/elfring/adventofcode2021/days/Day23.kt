package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import java.util.PriorityQueue

class Day23 : AoCPuzzle(23)
{
    override fun part1(input: List<String>): Any
    {
        val hallway = Hallway.of(input)

        return solve(hallway)
    }

    override fun part2(input: List<String>): Any
    {
        val hiddenRules = """
              #D#C#B#A#
              #D#B#A#C#
        """.trimIndent().split("\n")

        val hallway = Hallway.of(input.take(3) + hiddenRules + input.drop(3))

        return solve(hallway)
    }

    private fun solve(hallway: Hallway): Int
    {
        val queue = PriorityQueue(compareBy(Hallway::cost))
        queue.add(hallway)

        val seenPositions = mutableMapOf<String, Int>()
        var lowestCost = Int.MAX_VALUE

        while (queue.isNotEmpty())
        {
            val item = queue.remove()
            item.getAllPossibleSteps().forEach {
                if (it.isDone())
                {
                    if (it.cost < lowestCost)
                    {
                        lowestCost = it.cost
                    }
                }
                else if (it.cost < lowestCost && seenPositions.getOrDefault(it.getHash(), Int.MAX_VALUE) > it.cost)
                {
                    seenPositions[it.getHash()] = it.cost
                    queue.add(it)
                }
            }
        }

        return lowestCost
    }

    // 7 hallway followed by 4 times roomDepth rooms
    data class Hallway(val positions: List<Char?>, val roomDepth: Int, val cost: Int = 0)
    {
        fun getAllPossibleSteps(): List<Hallway>
        {
            return getPossibleStepsToHallway() + getPossibleSepsToRoom()
        }

        private fun getPossibleStepsToHallway(): List<Hallway>
        {
            val possibleSteps = mutableListOf<Hallway>()

            val (roomA, roomB, roomC, roomD) = positions.drop(7).chunked(roomDepth)
            // Move first out of room to all possible positions
            if (roomA.any { it != 'A' && it != null })
            {
                val first = roomA.first { it != null }!!
                val which = roomA.indexOfFirst { it == first }
                val stepsToMoveOut = which + 1
                val newHallway = positions.toMutableList().apply { this[7 + which] = null }

                if (positions[1] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[1] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))

                    if (positions[0] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[0] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 2) * costMap[first]!!
                        ))
                    }
                }
                if (positions[2] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[2] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[3] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[3] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 3) * costMap[first]!!
                        ))
                        if (positions[4] == null)
                        {
                            possibleSteps.add(Hallway(
                                newHallway.toMutableList().apply { this[4] = first },
                                roomDepth,
                                cost + (stepsToMoveOut + 5) * costMap[first]!!
                            ))
                            if (positions[5] == null)
                            {
                                possibleSteps.add(Hallway(
                                    newHallway.toMutableList().apply { this[5] = first },
                                    roomDepth,
                                    cost + (stepsToMoveOut + 7) * costMap[first]!!
                                ))
                                if (positions[6] == null)
                                {
                                    possibleSteps.add(Hallway(
                                        newHallway.toMutableList().apply { this[6] = first },
                                        roomDepth,
                                        cost + (stepsToMoveOut + 8) * costMap[first]!!
                                    ))
                                }
                            }
                        }
                    }
                }
            }

            if (roomB.any { it != 'B' && it != null })
            {
                val first = roomB.first { it != null }!!
                val which = roomB.indexOfFirst { it == first }
                val stepsToMoveOut = which + 1
                val newHallway = positions.toMutableList().apply { this[7 + roomDepth + which] = null }

                if (positions[2] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[2] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[1] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[1] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 3) * costMap[first]!!
                        ))
                        if (positions[0] == null)
                        {
                            possibleSteps.add(Hallway(
                                newHallway.toMutableList().apply { this[0] = first },
                                roomDepth,
                                cost + (stepsToMoveOut + 4) * costMap[first]!!
                            ))
                        }
                    }
                }
                if (positions[3] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[3] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[4] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[4] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 3) * costMap[first]!!
                        ))
                        if (positions[5] == null)
                        {
                            possibleSteps.add(Hallway(
                                newHallway.toMutableList().apply { this[5] = first },
                                roomDepth,
                                cost + (stepsToMoveOut + 5) * costMap[first]!!
                            ))
                            if (positions[6] == null)
                            {
                                possibleSteps.add(Hallway(
                                    newHallway.toMutableList().apply { this[6] = first },
                                    roomDepth,
                                    cost + (stepsToMoveOut + 6) * costMap[first]!!
                                ))
                            }
                        }
                    }
                }
            }

            if (roomC.any { it != 'C' && it != null })
            {
                val first = roomC.first { it != null }!!
                val which = roomC.indexOfFirst { it == first }
                val stepsToMoveOut = which + 1
                val newHallway = positions.toMutableList().apply { this[7 + 2 * roomDepth + which] = null }
                if (positions[3] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[3] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[2] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[2] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 3) * costMap[first]!!
                        ))
                        if (positions[1] == null)
                        {
                            possibleSteps.add(Hallway(
                                newHallway.toMutableList().apply { this[1] = first },
                                roomDepth,
                                cost + (stepsToMoveOut + 5) * costMap[first]!!
                            ))
                            if (positions[0] == null)
                            {
                                possibleSteps.add(Hallway(
                                    newHallway.toMutableList().apply { this[0] = first },
                                    roomDepth,
                                    cost + (stepsToMoveOut + 6) * costMap[first]!!
                                ))
                            }
                        }
                    }
                }
                if (positions[4] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[4] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[5] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[5] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 3) * costMap[first]!!
                        ))
                        if (positions[6] == null)
                        {
                            possibleSteps.add(Hallway(
                                newHallway.toMutableList().apply { this[6] = first },
                                roomDepth,
                                cost + (stepsToMoveOut + 4) * costMap[first]!!
                            ))
                        }
                    }
                }
            }

            if (roomD.any { it != 'D' && it != null })
            {
                val first = roomD.first { it != null }!!
                val which = roomD.indexOfFirst { it == first }
                val stepsToMoveOut = which + 1
                val newHallway = positions.toMutableList().apply { this[7 + 3 * roomDepth + which] = null }
                if (positions[4] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[4] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[3] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[3] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 3) * costMap[first]!!
                        ))
                        if (positions[2] == null)
                        {
                            possibleSteps.add(Hallway(
                                newHallway.toMutableList().apply { this[2] = first },
                                roomDepth,
                                cost + (stepsToMoveOut + 5) * costMap[first]!!
                            ))
                            if (positions[1] == null)
                            {
                                possibleSteps.add(Hallway(
                                    newHallway.toMutableList().apply { this[1] = first },
                                    roomDepth,
                                    cost + (stepsToMoveOut + 7) * costMap[first]!!
                                ))
                                if (positions[0] == null)
                                {
                                    possibleSteps.add(Hallway(
                                        newHallway.toMutableList().apply { this[0] = first },
                                        roomDepth,
                                        cost + (stepsToMoveOut + 8) * costMap[first]!!
                                    ))
                                }
                            }
                        }
                    }
                }
                if (positions[5] == null)
                {
                    possibleSteps.add(Hallway(
                        newHallway.toMutableList().apply { this[5] = first },
                        roomDepth,
                        cost + (stepsToMoveOut + 1) * costMap[first]!!
                    ))
                    if (positions[6] == null)
                    {
                        possibleSteps.add(Hallway(
                            newHallway.toMutableList().apply { this[6] = first },
                            roomDepth,
                            cost + (stepsToMoveOut + 2) * costMap[first]!!
                        ))
                    }
                }
            }

            return possibleSteps
        }

        private fun getPossibleSepsToRoom(): List<Hallway>
        {
            val possibleSteps = mutableListOf<Hallway>()

            val (roomA, roomB, roomC, roomD) = positions.drop(7).chunked(roomDepth)

            // Each hallway position
            positions.take(7).forEachIndexed { index, c ->
                when (c)
                {
                    'A' ->
                    {
                        if (roomA.all { it == 'A' || it == null })
                        {
                            val which = roomA.indexOfLast { it == null }
                            val stepsToMoveIn = which + 1
                            val toCheck = when (index)
                            {
                                0    -> (1 to 1) to 2
                                1    -> (null) to 1
                                2    -> (null) to 1
                                3    -> (2 to 2) to 3
                                4    -> (2 to 3) to 5
                                5    -> (2 to 4) to 7
                                6    -> (2 to 5) to 8
                                else -> throw IllegalArgumentException("Unknown toCheck")
                            }
                            if (toCheck.first == null || positions.subList(toCheck.first!!.first, toCheck.first!!.second + 1).all { it == null })
                            {
                                possibleSteps.add(Hallway(
                                    positions.toMutableList().apply {
                                        this[index] = null
                                        this[7 + which] = 'A'
                                    },
                                    roomDepth,
                                    cost + (toCheck.second + stepsToMoveIn) * costMap['A']!!
                                ))
                            }
                        }
                    }
                    'B' ->
                    {
                        if (roomB.all { it == 'B' || it == null })
                        {
                            val which = roomB.indexOfLast { it == null }
                            val stepsToMoveIn = which + 1
                            val toCheck = when (index)
                            {
                                0    -> (1 to 2) to 4
                                1    -> (2 to 2) to 3
                                2    -> (null) to 1
                                3    -> (null) to 1
                                4    -> (3 to 3) to 3
                                5    -> (3 to 4) to 5
                                6    -> (3 to 5) to 6
                                else -> throw IllegalArgumentException("Unknown toCheck")
                            }
                            if (toCheck.first == null || positions.subList(toCheck.first!!.first, toCheck.first!!.second + 1).all { it == null })
                            {
                                possibleSteps.add(Hallway(
                                    positions.toMutableList().apply {
                                        this[index] = null
                                        this[7 + roomDepth + which] = 'B'
                                    },
                                    roomDepth,
                                    cost + (toCheck.second + stepsToMoveIn) * costMap['B']!!
                                ))
                            }
                        }
                    }
                    'C' ->
                    {
                        if (roomC.all { it == 'C' || it == null })
                        {
                            val which = roomC.indexOfLast { it == null }
                            val stepsToMoveIn = which + 1
                            val toCheck = when (index)
                            {
                                0    -> (1 to 3) to 6
                                1    -> (2 to 3) to 5
                                2    -> (3 to 3) to 3
                                3    -> (null) to 1
                                4    -> (null) to 1
                                5    -> (4 to 4) to 3
                                6    -> (4 to 5) to 4
                                else -> throw IllegalArgumentException("Unknown toCheck")
                            }
                            if (toCheck.first == null || positions.subList(toCheck.first!!.first, toCheck.first!!.second + 1).all { it == null })
                            {
                                possibleSteps.add(Hallway(
                                    positions.toMutableList().apply {
                                        this[index] = null
                                        this[7 + 2 * roomDepth + which] = 'C'
                                    },
                                    roomDepth,
                                    cost + (toCheck.second + stepsToMoveIn) * costMap['C']!!
                                ))
                            }
                        }
                    }
                    'D' ->
                    {
                        if (roomD.all { it == 'D' || it == null })
                        {
                            val which = roomD.indexOfLast { it == null }
                            val stepsToMoveIn = which + 1
                            val toCheck = when (index)
                            {
                                0    -> (1 to 4) to 8
                                1    -> (2 to 4) to 7
                                2    -> (3 to 4) to 5
                                3    -> (4 to 4) to 3
                                4    -> (null) to 1
                                5    -> (null) to 1
                                6    -> (5 to 5) to 2
                                else -> throw IllegalArgumentException("Unknown toCheck")
                            }
                            if (toCheck.first == null || positions.subList(toCheck.first!!.first, toCheck.first!!.second + 1).all { it == null })
                            {
                                possibleSteps.add(Hallway(
                                    positions.toMutableList().apply {
                                        this[index] = null
                                        this[7 + 3 * roomDepth + which] = 'D'
                                    },
                                    roomDepth,
                                    cost + (toCheck.second + stepsToMoveIn) * costMap['D']!!
                                ))
                            }
                        }
                    }
                }
            }

            return possibleSteps
        }

        fun isDone(): Boolean
        {
            return positions.drop(7).chunked(roomDepth).let { line ->
                line[0].all { it == 'A' } &&
                        line[1].all { it == 'B' } &&
                        line[2].all { it == 'C' } &&
                        line[3].all { it == 'D' }
            }
        }

        fun getHash(): String
        {
            return positions.map { it ?: '.' }.joinToString("")
        }

        companion object
        {
            fun of(input: List<String>): Hallway
            {
                val lines = input.drop(2).dropLast(1).map { it.toCharArray().filter { c -> c in listOf('A', 'B', 'C', 'D') } }

                val positions = buildList {
                    addAll(listOf(null, null, null, null, null, null, null))
                    repeat(4) { count ->
                        lines.forEach { add(it[count]) }
                    }
                }

                return Hallway(positions, lines.size)
            }

            val costMap = mapOf(
                'A' to 1,
                'B' to 10,
                'C' to 100,
                'D' to 1000
            )
        }
    }
}
