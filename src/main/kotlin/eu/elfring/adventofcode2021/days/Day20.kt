package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle

class Day20 : AoCPuzzle(20)
{
    override fun part1(input: List<String>): Any
    {
        val (imageEnhancing, image) = parseInput(input)

        var enhancedImage = image
        repeat(2)
        {
            enhancedImage = enhance(imageEnhancing, enhancedImage)
        }

        return enhancedImage.pixels.count { it.value == 1 }
    }

    override fun part2(input: List<String>): Any
    {
        val (imageEnhancing, image) = parseInput(input)

        var enhancedImage = image
        repeat(50)
        {
            enhancedImage = enhance(imageEnhancing, enhancedImage)
        }

        return enhancedImage.pixels.count { it.value == 1 }
    }

    private fun parseInput(input: List<String>): Pair<List<Int>, Image>
    {
        val imageEnhancing = input.first().map { if (it == '#') 1 else 0 }

        val pixels = input.drop(2).flatMapIndexed { y, line ->
            line.mapIndexed { x, pixel ->
                Coordinate(x, y) to if (pixel == '#') 1 else 0
            }
        }.toMap()

        return imageEnhancing to Image(0, pixels)
    }

    private fun enhance(imageEnhancing: List<Int>, image: Image): Image
    {
        val background = when
        {
            image.background == 0 && imageEnhancing.first() == 1 -> 1
            image.background == 1 && imageEnhancing.last() == 0  -> 0
            else                                                 -> image.background
        }
        val newPixels = buildMap {
            image.pixels.flatMap { it.key.getNeighboursIncludingSelf() }.toSet().forEach { coordinate ->
                val pixelSum = coordinate.getNeighboursIncludingSelf()
                    .map { image.pixels.getOrDefault(it, image.background) }
                    .joinToString("").toInt(2)
                val pixelValue = imageEnhancing[pixelSum]
                if (pixelValue != background)
                {
                    put(coordinate, pixelValue)
                }
            }
        }
        return Image(background, newPixels)
    }

    private data class Image(val background: Int, val pixels: Map<Coordinate, Int>)

    private data class Coordinate(val x: Int, val y: Int)
    {
        fun getNeighboursIncludingSelf(): List<Coordinate>
        {
            return listOf(
                Coordinate(x - 1, y - 1), Coordinate(x, y - 1), Coordinate(x + 1, y - 1),
                Coordinate(x - 1, y), Coordinate(x, y), Coordinate(x + 1, y),
                Coordinate(x - 1, y + 1), Coordinate(x, y + 1), Coordinate(x + 1, y + 1)
            )
        }
    }
}


