package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import java.util.LinkedList

class Day16 : AoCPuzzle(16)
{
    override fun part1(input: List<String>): Any
    {
        return parse(input.first()).versionSum()
    }

    override fun part2(input: List<String>): Any
    {
        return parse(input.first()).valueSum()
    }

    private fun parse(input: String): Packet
    {
        val binaryString = input.map { it.digitToInt(16) }.joinToString("") { it.toString(2).padStart(4, '0') }
        val binaryList = LinkedList(binaryString.toList())

        return parsePacket(binaryList)
    }

    private fun parsePacket(binaryList: LinkedList<Char>): Packet
    {
        val packageVersion = binaryList.pop(3).toInt(2)
        val packetType = binaryList.pop(3).toInt(2)

        return if (packetType == 4)
        {
            parseLiteral(packageVersion, packetType, binaryList)
        }
        else
        {
            parseOperator(packageVersion, packetType, binaryList)
        }
    }

    private fun parseLiteral(packageVersion: Int, packetType: Int, binaryList: LinkedList<Char>): Literal
    {
        var hasNext = true
        var stringValue = ""
        while (hasNext)
        {
            val addedValue = binaryList.pop(5)
            if (addedValue.first() == '0')
            {
                hasNext = false
            }
            stringValue += addedValue.drop(1)
        }

        return Literal(packageVersion, packetType, stringValue.toLong(2))
    }

    private fun parseOperator(packageVersion: Int, packetType: Int, binaryList: LinkedList<Char>): Operator
    {
        val lengthId = binaryList.pop(1).toInt(2)
        val packages: List<Packet> = if (lengthId == 0) // total length in bits
        {
            val totalLengthToRead = binaryList.pop(15).toInt(2)
            val readUntilLength = binaryList.size - totalLengthToRead

            val packets = mutableListOf<Packet>()

            while (binaryList.size > readUntilLength)
            {
                packets.add(parsePacket(binaryList))
            }

            return Operator(packageVersion, packetType, packets)
        }
        else if (lengthId == 1) // number of sub-packets
        {
            val amountOfPackages = binaryList.pop(11).toInt(2)

            (0 until amountOfPackages).map { parsePacket(binaryList) }.toList()
        }
        else
        {
            throw IllegalArgumentException("Unknown lengthId $lengthId")
        }

        return Operator(packageVersion, packetType, packages)
    }

    interface Packet
    {
        val packageVersion: Int
        val packetType: Int

        fun versionSum(): Int
        fun valueSum(): Long
    }

    class Literal(override val packageVersion: Int, override val packetType: Int, private val value: Long) : Packet
    {
        override fun versionSum(): Int
        {
            return packageVersion
        }

        override fun valueSum(): Long
        {
            return value
        }
    }

    class Operator(override val packageVersion: Int, override val packetType: Int, private val packets: List<Packet>) : Packet
    {
        override fun versionSum(): Int
        {
            return packets.sumOf { it.versionSum() } + packageVersion
        }

        override fun valueSum(): Long
        {
            return when (packetType)
            {
                0    -> packets.sumOf { it.valueSum() }
                1    -> packets.map { it.valueSum() }.reduce { a, b -> a * b }
                2    -> packets.minOf { it.valueSum() }
                3    -> packets.maxOf { it.valueSum() }
                5    -> packets.map { it.valueSum() }.let { (a, b) -> if (a > b) 1 else 0 }
                6    -> packets.map { it.valueSum() }.let { (a, b) -> if (a < b) 1 else 0 }
                7    -> packets.map { it.valueSum() }.let { (a, b) -> if (a == b) 1 else 0 }
                else -> throw IllegalArgumentException("Unknown operator $packetType")
            }
        }
    }

    private fun <E> LinkedList<E>.pop(count: Int): String
    {
        var output = ""
        repeat(count)
        {
            output += pop()
        }
        return output
    }
}
