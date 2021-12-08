package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day08 : AoCPuzzle(8)
{
    override fun part1(input: List<String>): Any
    {
        return input.sumOf {
            it.substringAfter(" | ")
                .split(" ")
                .map { it.count() }
                .count { it == 2 || it == 3 || it == 4 || it == 7 }
        }
    }

    override fun part2(input: List<String>): Any
    {
        return input.sumOf { solveLine(it) }
    }

    private fun solveLine(input: String): Int
    {
        val firstHalf = input.substringBefore(" | ").split(" ").toList()
        val secondHalf = input.substringAfter(" | ").split(" ").toList()

        // Possible wires for each segment
        val a = mutableListOf<Char>()
        val b = mutableListOf<Char>()
        val c = mutableListOf<Char>()
        val d = mutableListOf<Char>()
        val e = mutableListOf<Char>()
        val f = mutableListOf<Char>()
        val g = mutableListOf<Char>()

        // Number 1 - 50-50 shot on c & f
        firstHalf.filter { it.count() == 2 }.forEach {
            val chars = it.toCharArray().toList()
            c.addAll(chars)
            f.addAll(chars)
        }

        // Number 7 - determine a
        firstHalf.filter { it.count() == 3 }.first() {
            val chars = it.toCharArray().toList()
            a.addAll(chars - c)
        }

        // Number 4 - 50-50 shot on b & d
        firstHalf.filter { it.count() == 4 }.first() {
            val chars = it.toCharArray().toList() - c
            b.addAll(chars)
            d.addAll(chars)
        }

        // Numbers 2, 3, 5 -> Determine b & d & g
        val dg = firstHalf.filter { it.count() == 5 }
            .map { it.toCharArray().toList() }
            .reduce { acc, value -> acc.intersect(value).toList() } - a
        val dVal = dg intersect d
        val bVal = b - dVal
        b.clear()
        b.addAll(bVal)
        d.clear()
        d.addAll(dVal)
        g.addAll(dg - d)

        // calculate e
        val eVal = "abcdefg".toCharArray().toList() - a - b - c /* contains C & F */ - d - g
        e.addAll(eVal)

        // Solve 50-50 c & f
        // Search for 6, 6 displays & contains D (excludes 0) and E (excludes 9)
        val fVal = firstHalf.map { it.toCharArray().toList() }
            .first { it.count() == 6 && it.containsAll(d + e) } - a - b - d - e - g
        val cVal = c - fVal
        c.clear()
        f.clear()
        c.addAll(cVal)
        f.addAll(fVal)

        val val0 = (a + b + c + e + f + g).sorted()
        val val1 = (c + f).sorted()
        val val2 = (a + c + d + e + g).sorted()
        val val3 = (a + c + d + f + g).sorted()
        val val4 = (b + c + d + f).sorted()
        val val5 = (a + b + d + f + g).sorted()
        val val6 = (a + b + d + e + f + g).sorted()
        val val7 = (a + c + f).sorted()
        val val8 = (a + b + c + d + e + f + g).sorted()
        val val9 = (a + b + c + d + f + g).sorted()

        return secondHalf.joinToString("") {
            when (it.toCharArray().toList().sorted())
            {
                val0 -> "0"
                val1 -> "1"
                val2 -> "2"
                val3 -> "3"
                val4 -> "4"
                val5 -> "5"
                val6 -> "6"
                val7 -> "7"
                val8 -> "8"
                val9 -> "9"
                else -> throw Exception()
            }
        }.toInt()
    }
}
