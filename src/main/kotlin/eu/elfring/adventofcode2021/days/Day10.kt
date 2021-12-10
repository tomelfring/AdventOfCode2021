package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day10 : AoCPuzzle(10)
{
    private val corruptedScore = mapOf<String, Long>(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137
    )

    private val incompleteScore = mapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4
    )

    override fun part1(input: List<String>): Any
    {
        return input.map(::checkSyntax).sumOf { if (it.first) it.second else 0 }
    }

    override fun part2(input: List<String>): Any
    {
        val scores = input.map(::checkSyntax).mapNotNull { if (!it.first) it.second else null }.sorted()

        return scores[scores.size/2]
    }

    private fun checkSyntax(input: String): Pair<Boolean, Long>
    {
        val openPairs = mutableListOf<Char>()

        input.toCharArray().forEach {
            when(it)
            {
                '(' -> openPairs.add(it)
                '[' -> openPairs.add(it)
                '{' -> openPairs.add(it)
                '<' -> openPairs.add(it)
                ')' -> if (openPairs.last() != '(') return true to corruptedScore.getOrElse(")") { throw Exception() } else openPairs.removeLast()
                ']' -> if (openPairs.last() != '[') return true to corruptedScore.getOrElse("]") { throw Exception() } else openPairs.removeLast()
                '}' -> if (openPairs.last() != '{') return true to corruptedScore.getOrElse("}") { throw Exception() } else openPairs.removeLast()
                '>' -> if (openPairs.last() != '<') return true to corruptedScore.getOrElse(">") { throw Exception() } else openPairs.removeLast()
            }
        }

        return false to openPairs.reversed().fold(0L) { acc, value -> acc * 5 + incompleteScore.getOrElse(value) { throw Exception() } }
    }
}
