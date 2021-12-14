package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day14 : AoCPuzzle(14)
{
    override fun part1(input: List<String>): Any
    {
        val (template, pairInsertionRules) = parseInput(input)

        return doRun(template, 10, pairInsertionRules)
    }

    override fun part2(input: List<String>): Any
    {
        val (template, pairInsertionRules) = parseInput(input)

        return doRun(template, 40, pairInsertionRules)
    }

    private fun parseInput(input: List<String>): Pair<String, Map<String, List<String>>>
    {
        val template = input.first()
        val regex = """([A-Z]+) -> ([A-Z]+)""".toRegex()
        val pairInsertionRules = input.drop(2)
            .associate { regex.matchEntire(it)!!.destructured.let { (a, b) -> a to listOf(a[0] + b, b + a[1]) } }
        return template to pairInsertionRules
    }

    private fun doRun(template: String, steps: Int, pairInsertionRules: Map<String, List<String>>): Long
    {
        var state = template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        repeat(steps) {
            // Map each pair to the next set of pairs
            state = mutableMapOf<String, Long>().withDefault { 0 }.apply {
                for ((src, n) in state)
                {
                    for (dst in pairInsertionRules.getValue(src))
                    {
                        put(dst, getValue(dst) + n)
                    }
                }
            }
        }

        val characterCounts = mutableMapOf<Char, Long>().withDefault { 0 }.apply {
            // Each value, except first & last is counted double, add them
            put(template.first(), 1)
            put(template.last(), getValue(template.last()) + 1)

            for ((pair, n) in state)
            {
                for (c in pair)
                {
                    put(c, getValue(c) + n)
                }
            }
        }

        // As each is double counted, divide by 2
        return (characterCounts.values.maxOf { it } - characterCounts.values.minOf { it }) / 2
    }
}
