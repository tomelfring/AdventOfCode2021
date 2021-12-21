package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import java.util.LinkedList
import kotlin.math.abs

class Day19 : AoCPuzzle(19)
{
    override fun part1(input: List<String>): Any
    {
        val scanners = parseScanners(input).flatMap { it.createOrientations() }

        val matched = alignAllScanners(scanners)

        return matched.flatMap { it.absoluteBeacons() }.toSet().count()
    }

    override fun part2(input: List<String>): Any
    {
        val scanners = parseScanners(input).flatMap { it.createOrientations() }
        val scannerPositions = alignAllScanners(scanners).map { it.position!! }

        return scannerPositions.associateWith { scannerPositions }
            .flatMap { set -> set.value.map { it.distance(set.key) } }
            .maxOf { it }
    }

    private fun parseScanners(input: List<String>): List<Scanner>
    {
        return input.joinToString("\r\n").split("\r\n\r\n").map { Scanner.of(it.split("\r\n")) }
    }


    private fun alignAllScanners(scanners: List<Scanner>): List<Scanner>
    {
        val unmatched = scanners.groupBy { it.id }.toMutableMap()
        val matched = mutableMapOf<Int, Scanner>()

        matched[0] = unmatched.getValue(0).first().copy(position = Coordinate(0, 0, 0))
        unmatched.remove(0)

        val queue = LinkedList<Int>()
        queue.add(0)

        while (queue.size > 0 && unmatched.isNotEmpty())
        {
            val id = queue.pop()
            val matches = alignAnyScanners(matched[id]!!, unmatched)
            for (match in matches)
            {
                matched[match.id] = match
                queue.add(match.id)
                unmatched.remove(match.id)
            }
        }
        return matched.values.toList()
    }

    // scanners -> Map of id to list of scanner in any rotation
    // returns: scanner that matches, with all posible rotations
    private fun alignAnyScanners(target: Scanner, scanners: Map<Int, List<Scanner>>): List<Scanner>
    {
        return scanners.flatMap { alignAnyOrentation(target, it.value) }
    }

    private fun alignAnyOrentation(target: Scanner, scanners: List<Scanner>): List<Scanner>
    {
        return scanners.mapNotNull { alignSingelOrientation(target, it) }
    }

    private fun alignSingelOrientation(target: Scanner, scanner: Scanner): Scanner?
    {
        target.absoluteBeacons().forEach { targetCoordinate ->
            scanner.relativeBeacons.forEach { beacon ->
                val offset = targetCoordinate - beacon
                val movedScanner = scanner.copy(position = offset)
                val matches = (target.absoluteBeacons() intersect movedScanner.absoluteBeacons()).count()

                if (matches >= 12)
                {
                    return movedScanner
                }
            }
        }
        return null
    }

    data class Scanner(val id: Int, val relativeBeacons: List<Coordinate>, val position: Coordinate? = null)
    {
        fun absoluteBeacons() = relativeBeacons.map { it + position!! }

        fun createOrientations(): List<Scanner>
        {
            val temp = relativeBeacons.map { it.allOrientations() }
            return (0 until 24).map { count ->
                Scanner(id, temp.map { it[count] })
            }
        }

        companion object
        {
            fun of(input: List<String>): Scanner
            {
                val id = input.first().drop(12).dropLast(4).toInt()
                val coordinates = input.drop(1).map { beaconRegex.matchEntire(it)!!.destructured.let { (x, y, z) -> Coordinate(x.toInt(), y.toInt(), z.toInt()) } }

                return Scanner(id, coordinates)
            }
        }
    }

    data class Coordinate(val x: Int, val y: Int, val z: Int)
    {
        operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Coordinate) = Coordinate(x - other.x, y - other.y, z - other.z)
        fun distance(other: Coordinate) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

        fun allOrientations(): List<Coordinate>
        {
            return listOf(
                Coordinate(x, y, z), Coordinate(x, -z, y), Coordinate(x, -y, -z), Coordinate(x, z, -y), Coordinate(-x, -y, z),
                Coordinate(-x, -z, -y), Coordinate(-x, y, -z), Coordinate(-x, z, y), Coordinate(-z, x, -y), Coordinate(y, x, -z),
                Coordinate(z, x, y), Coordinate(-y, x, z), Coordinate(z, -x, -y), Coordinate(y, -x, z), Coordinate(-z, -x, y),
                Coordinate(-y, -x, -z), Coordinate(-y, -z, x), Coordinate(z, -y, x), Coordinate(y, z, x), Coordinate(-z, y, x),
                Coordinate(z, y, -x), Coordinate(-y, z, -x), Coordinate(-z, -y, -x), Coordinate(y, -z, -x),
            )
        }
    }

    companion object
    {
        val beaconRegex = """(-?\d+),(-?\d+),(-?\d+)""".toRegex()
    }
}
