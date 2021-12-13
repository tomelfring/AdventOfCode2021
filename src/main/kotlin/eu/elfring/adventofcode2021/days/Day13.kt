package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day13 : AoCPuzzle(13)
{
    override fun part1(input: List<String>): Any
    {
        val (paper, folds) = getParsedInput(input)

        folds.take(1).forEach {
            if (it.first == "y")
            {
                paper.foldY(it.second)
            }
            else
            {
                paper.foldX(it.second)
            }
        }
        return paper.values.size
    }

    override fun part2(input: List<String>): Any
    {
        val (paper, folds) = getParsedInput(input)

        folds.forEach {
            if (it.first == "y")
            {
                paper.foldY(it.second)
            }
            else
            {
                paper.foldX(it.second)
            }
        }

        // Pretty print
        val maxX = paper.values.maxOf { it.first }
        val maxY = paper.values.maxOf { it.second }

        val output = mutableListOf("") // First element empty for pretty print
        for (y in 0..maxY)
        {
            var outputLine = ""
            for (x in 0..maxX)
            {
                if (paper.values.contains(x to y))
                {
                    outputLine+="#"
                }
                else
                {
                    outputLine+=" "
                }
            }
            output.add(outputLine)
        }

        return output.joinToString("\n")
    }

    private fun getParsedInput(input: List<String>): Pair<TransparantPaper, List<Pair<String, Int>>>
    {
        return getPaper(input) to getFolds(input)
    }

    private fun getPaper(input: List<String>): TransparantPaper
    {
        val pairs = input.takeWhile { it != "" }

        val mutableSet = mutableSetOf<Pair<Int, Int>>().apply {
            pairs.map { it.split(",") }.forEach { (x, y) ->
                add(x.toInt() to y.toInt())
            }
        }
        return TransparantPaper(mutableSet)
    }

    private fun getFolds(input: List<String>): List<Pair<String, Int>>
    {
        return input.takeLastWhile { it != "" }.map { it.takeLastWhile { it != ' ' }.split("=") }.map { it[0] to it[1].toInt() }
    }

    private class TransparantPaper(var values: MutableSet<Pair<Int, Int>>)
    {
        fun foldX(xFold: Int)
        {
            val toFold = values.filter { it.first > xFold }
            values.removeAll(toFold)
            values.addAll(toFold.map {
                (it.first - (it.first-xFold)*2) to it.second
            })
        }

        fun foldY(yFold: Int)
        {
            val toFold = values.filter { it.second > yFold }
            values.removeAll(toFold)
            values.addAll(toFold.map {
                it.first to (it.second - (it.second-yFold)*2)
            })
        }
    }
}
