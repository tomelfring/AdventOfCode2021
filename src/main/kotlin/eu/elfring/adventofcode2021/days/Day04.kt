package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day04 : AoCPuzzle(4)
{
    override fun part1(input: List<String>): Any
    {
        val game = parseInput(input)

        var lastNumber = 0
        while(game.boards.all{ !it.isWinning() })
        {
            lastNumber = game.playRound()
        }

        return game.boards.first { it.isWinning() }.score() * lastNumber
    }

    override fun part2(input: List<String>): Any
    {
        val game = parseInput(input)

        var lastNumber = 0
        val winningBoards = mutableListOf<Board>()
        while(game.boards.any { !it.isWinning() })
        {
            lastNumber = game.playRound()
            game.boards.forEach { if (it.isWinning() && !winningBoards.contains(it)) winningBoards.add(it) }
        }

        return winningBoards.last().score() * lastNumber
    }

    private fun parseInput(input: List<String>): BingoGame
    {
        val drawnNumbers = input.first().split(",").map { it.toInt() }.toMutableList()

        val boards = input.drop(2).chunked(6).map(::parseBoard)

        return BingoGame(drawnNumbers, boards)
    }

    private fun parseBoard(boardInput: List<String>): Board
    {
        val numbers = boardInput.take(5).map { it.trim().split("\\s+".toRegex()).map { it.toInt() }.toMutableList() }.toMutableList()
        return Board(numbers)
    }

    data class BingoGame(val drawnNumbers: MutableList<Int>, val boards: List<Board>)
    {
        fun playRound(): Int
        {
            val number = drawnNumbers.removeFirst()
            boards.forEach { it.markNumber(number) }

            return number
        }
    }

    data class Board(val numbers: MutableList<MutableList<Int>>)
    {
        fun isWinning(): Boolean
        {
            val horWin = numbers.any{ it.sum() == -5 }

            val vertWin = (0..4).map { index -> (0..4).map { line -> numbers[line][index] } }.any { it.sum() == -5 }

            return horWin || vertWin
        }

        fun markNumber(number: Int) = numbers.forEach { line -> line.forEachIndexed { index, i -> if (i == number) line[index] = -1 } }

        fun score() = numbers.sumOf { it.sumOf { if (it == -1) 0 else it } }
    }
}
