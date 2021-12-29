package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day24

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("Year 2021 - Day 24")
internal class Day24Test
{
    @Test
    @DisplayName("ALU - negates input")
    fun negateInput()
    {
        val input = """
            inp x
            mul x -1
        """.trimIndent().split("\n")
        val alu = Day24.ALU(input)
        val aluMemory = Day24.ALUMemory()
        alu.run(listOf(7), aluMemory)
        Assertions.assertEquals(-7L, aluMemory.x)
    }

    @Test
    @DisplayName("ALU - Three times")
    fun threeTimes()
    {
        val input = """
            inp z
            inp x
            mul z 3
            eql z x
        """.trimIndent().split("\n")
        val alu = Day24.ALU(input)
        val aluMemory1 = Day24.ALUMemory()
        alu.run(listOf(1, 3), aluMemory1)
        Assertions.assertEquals(1, aluMemory1.z)

        val aluMemory2 = Day24.ALUMemory()
        alu.run(listOf(1, 4), aluMemory2)
        Assertions.assertEquals(0, aluMemory2.z)
    }

    @Test
    @DisplayName("ALU - Binary")
    fun binary()
    {
        val input = """
            inp w
            add z w
            mod z 2
            div w 2
            add y w
            mod y 2
            div w 2
            add x w
            mod x 2
            div w 2
            mod w 2
        """.trimIndent().split("\n")
        val alu = Day24.ALU(input)
        val aluMemory = Day24.ALUMemory()
        alu.run(listOf(7), aluMemory)
        Assertions.assertEquals(0, aluMemory.w)
        Assertions.assertEquals(1, aluMemory.x)
        Assertions.assertEquals(1, aluMemory.y)
        Assertions.assertEquals(1, aluMemory.z)
    }
}
