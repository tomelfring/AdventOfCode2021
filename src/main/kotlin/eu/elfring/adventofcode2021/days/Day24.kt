package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import java.util.LinkedList

class Day24 : AoCPuzzle(24)
{
    override fun part1(input: List<String>): Any
    {
        val serialNumber = checkModelNumber(input, false)

        // Double check
        val alu = ALU(input)
        val aluMemory = ALUMemory()
        alu.run(serialNumber.toCharArray().map { it.digitToInt().toLong() }, aluMemory)

        return if (aluMemory.z == 0L)
        {
            serialNumber
        }
        else
        {
            "Error"
        }
    }

    override fun part2(input: List<String>): Any
    {
        val serialNumber = checkModelNumber(input, true)

        // Double check
        val alu = ALU(input)
        val aluMemory = ALUMemory()
        alu.run(serialNumber.toCharArray().map { it.digitToInt().toLong() }, aluMemory)

        return if (aluMemory.z == 0L)
        {
            serialNumber
        }
        else
        {
            "Error"
        }
    }

    private fun checkModelNumber(input: List<String>, lowest: Boolean): String
    {
        val perNumber = input.chunked(18)
        val stack = ArrayDeque<Pair<Int, Int>>()
        val finalNumber = MutableList(14) { 0 }

        perNumber.forEachIndexed { index, instructions ->
            val fifth = instructions[5].substringAfterLast(" ").toInt()
            if (fifth >= 0)
            {
                stack.add(index to instructions[15].substringAfterLast(" ").toInt())
            }
            else
            {
                val popped = stack.removeLast()
                val value = popped.second + fifth
                val digit = (if (lowest) 1..9 else 9 downTo 1).first { it + value in 1..9 }
                finalNumber[popped.first] = digit
                finalNumber[index] = digit + value
            }
        }

        return finalNumber.joinToString("")
    }

    class ALU(private val program: List<String>)
    {
        fun run(input: List<Long>, memory: ALUMemory)
        {
            val inputList = LinkedList(input)
            program.forEach {
                val split = it.split(" ")
                val operator = split[0]
                val variable = split[1]
                val value = when
                {
                    split.size == 2                        -> null
                    split[2] in listOf("w", "x", "y", "z") ->
                    {
                        when (split[2])
                        {
                            "w"  -> memory.w
                            "x"  -> memory.x
                            "y"  -> memory.y
                            "z"  -> memory.z
                            else -> throw IllegalArgumentException()
                        }
                    }
                    split[2].toIntOrNull() != null         -> split[2].toLong()
                    else                                   -> throw IllegalArgumentException("Unknown value")
                }
                when (operator)
                {
                    "inp" -> test(memory, variable) { inputList.pop() }
                    "add" -> test(memory, variable) { a -> a + value!! }
                    "mul" -> test(memory, variable) { a -> a * value!! }
                    "div" -> test(memory, variable) { a -> a / value!! }
                    "mod" -> test(memory, variable) { a -> a % value!! }
                    "eql" -> test(memory, variable) { a -> if (a == value!!) 1 else 0 }
                }
            }
        }

        private fun test(memory: ALUMemory, variable: String, operator: (Long) -> Long)
        {
            when (variable)
            {
                "w" -> memory.w = operator(memory.w)
                "x" -> memory.x = operator(memory.x)
                "y" -> memory.y = operator(memory.y)
                "z" -> memory.z = operator(memory.z)
            }
        }
    }

    data class ALUMemory(var w: Long = 0, var x: Long = 0, var y: Long = 0, var z: Long = 0)
}
