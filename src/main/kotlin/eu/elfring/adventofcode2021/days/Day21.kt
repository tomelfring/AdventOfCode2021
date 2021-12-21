package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import kotlin.math.max
import kotlin.math.min

class Day21 : AoCPuzzle(21)
{
    override fun part1(input: List<String>): Any
    {
        var (start1, start2) = parseInput(input)
        var score1 = 0
        var score2 = 0

        var count = 0
        val diceSequence = generateSequence(0) { (count++)%100 }.map { it+1 }

        while (score1 < 1000 && score2 < 1000)
        {
            start1 = (((start1 + diceSequence.take(4).drop(1).sum()) - 1) % 10) + 1
            score1 += start1

            if (score1 >= 1000)
            {
                break
            }
            start2 = (((start2 + diceSequence.take(4).drop(1).sum()) -1 ) % 10) + 1
            score2 += start2
        }


        return min(score1, score2) * count
    }

    override fun part2(input: List<String>): Any
    {
        val (start1, start2) = parseInput(input)

        // Map of gameState and count how many of those there are (Memoization)
        val counter = mutableMapOf(GameState(start1, start2, 0, 0) to 1L)

        var wins1 = 0L
        var wins2 = 0L

        while (counter.isNotEmpty())
        {
            val iterator = counter.iterator()

            val (gameState, count) = iterator.next()
            iterator.remove()

            val (startPos1, startPos2, startScore1, startScore2) = gameState

            repeat(3) { d1 ->
                repeat(3) { d2 ->
                    repeat(3) { d3 ->
                        val pos1 = (((startPos1 + d1 + d2 + d3 + 3) -1 ) % 10) + 1

                        val score1 = startScore1 + pos1

                        if (score1 >= 21)
                        {
                            wins1 += count
                        }
                        else
                        {
                            repeat(3) { d4 ->
                                repeat(3) { d5 ->
                                    repeat(3) { d6 ->
                                        val pos2 = (((startPos2 + d4 + d5 + d6 + 3) -1 ) % 10) + 1
                                        val score2 = startScore2 + pos2

                                        if (score2 >= 21)
                                        {
                                            wins2 += count
                                        }
                                        else
                                        {
                                            // No winner, add game with new state back to pool
                                            val newGameState = GameState(pos1, pos2, score1, score2)
                                            counter[newGameState] = counter.getOrDefault(newGameState, 0)+count
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return max(wins1, wins2)
    }

    data class GameState(val pos1: Int, val pos2: Int, val score1: Int, val score2: Int)

    private fun parseInput(input: List<String>): Pair<Int, Int>
    {
        val values = input.map { it.takeLastWhile { it != ' ' } }
        return values[0].toInt() to values[1].toInt()
    }
}
