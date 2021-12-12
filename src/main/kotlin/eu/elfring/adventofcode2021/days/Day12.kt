package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day12 : AoCPuzzle(12)
{
    override fun part1(input: List<String>): Any
    {
        return Passage(input).getPathCount(false)
    }

    override fun part2(input: List<String>): Any
    {
        return Passage(input).getPathCount(true)
    }

    class Passage(private val input: List<String>)
    {
        private val edges = mutableMapOf<String, Set<String>>().withDefault { setOf() }.apply {
            input.map { it.split("-") }.forEach { (a, b) ->
                put(a, getValue(a) + b)
                put(b, getValue(b) + a)
            }
        }

        fun getPathCount(allowExtraCave: Boolean): Int
        {
            return searchRecursiveDfs("start", allowExtraCave).size
        }

        private fun searchRecursiveDfs(curr: String, allowExtraCave: Boolean, path: List<String> = listOf()): List<List<String>>
        {
            val updatedPath = path + curr
            if (curr == "end") return listOf(updatedPath)

            return edges.getValue(curr).filterNot { next ->
                next == "start" ||
                next.isLower() && next in updatedPath &&
                if (allowExtraCave) updatedPath.filter { it.isLower() }.groupingBy { it }.eachCount().values.any { it >= 2 } else true
            }.flatMap { searchRecursiveDfs(it, allowExtraCave, updatedPath) }
        }

        private fun String.isLower() = this.all { it.isLowerCase() }
    }
}
