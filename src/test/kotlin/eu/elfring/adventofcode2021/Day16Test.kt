package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day16

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 16")
internal class Day16Test
{
    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(16, Day16().part1(listOf("8A004A801A8002F478")))
        Assertions.assertEquals(12, Day16().part1(listOf("620080001611562C8802118E34")))
        Assertions.assertEquals(23, Day16().part1(listOf("C0015000016115A2E0802F182340")))
        Assertions.assertEquals(31, Day16().part1(listOf("A0016C880162017C3686B18A3D4780")))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(3L, Day16().part2(listOf("C200B40A82")))
        Assertions.assertEquals(54L, Day16().part2(listOf("04005AC33890")))
        Assertions.assertEquals(7L, Day16().part2(listOf("880086C3E88112")))
        Assertions.assertEquals(9L, Day16().part2(listOf("CE00C43D881120")))
        Assertions.assertEquals(1L, Day16().part2(listOf("D8005AC2A8F0")))
        Assertions.assertEquals(0L, Day16().part2(listOf("F600BC2D8F")))
        Assertions.assertEquals(0L, Day16().part2(listOf("9C005AC2F8F0")))
        Assertions.assertEquals(1L, Day16().part2(listOf("9C0141080250320F1802104A08")))
    }
}
