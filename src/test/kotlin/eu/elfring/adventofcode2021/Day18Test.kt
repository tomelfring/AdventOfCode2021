package eu.elfring.adventofcode2021

import eu.elfring.adventofcode2021.days.Day18

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2021 - Day 18")
internal class Day18Test
{
    private val input = """
        [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
        [[[5,[2,8]],4],[5,[[9,9],0]]]
        [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
        [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
        [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
        [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
        [[[[5,4],[7,7]],8],[[8,3],8]]
        [[9,3],[[9,9],[6,[4,9]]]]
        [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
        [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Explode")
    fun explode()
    {
        // the 9 has no regular number to its left, so it is not added to any regular number
        Assertions.assertEquals("[[[[0,9],2],3],4]", Day18.SnailNumber.of("[[[[[9,8],1],2],3],4]").reduce().toString())

        // the 2 has no regular number to its right, and so it is not added to any regular number
        Assertions.assertEquals("[7,[6,[5,[7,0]]]]", Day18.SnailNumber.of("[7,[6,[5,[4,[3,2]]]]]").reduce().toString())

        Assertions.assertEquals("[[6,[5,[7,0]]],3]", Day18.SnailNumber.of("[[6,[5,[4,[3,2]]]],1]").reduce().toString())

        // the pair [3,2] is unaffected because the pair [7,3] is further to the left; [3,2] would explode on the next action
        // Note: we are reducing, second explosion also happend
        Assertions.assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", Day18.SnailNumber.of("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").reduce().toString())

        Assertions.assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", Day18.SnailNumber.of("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").reduce().toString())
    }

    @Test
    @DisplayName("Split")
    fun split()
    {
        Assertions.assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
                                (Day18.SnailNumber.of("[[[[4,3],4],4],[7,[[8,4],9]]]") +
                                        Day18.SnailNumber.of("[1,1]")).reduce().toString())
    }

    @Test
    @DisplayName("Magnitude")
    fun magnitude()
    {
        Assertions.assertEquals(143, Day18.SnailNumber.of("[[1,2],[[3,4],5]]").magnitude())
        Assertions.assertEquals(1384, Day18.SnailNumber.of("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude())
        Assertions.assertEquals(445, Day18.SnailNumber.of("[[[[1,1],[2,2]],[3,3]],[4,4]]").magnitude())
        Assertions.assertEquals(791, Day18.SnailNumber.of("[[[[3,0],[5,3]],[4,4]],[5,5]]").magnitude())
        Assertions.assertEquals(1137, Day18.SnailNumber.of("[[[[5,0],[7,4]],[5,5]],[6,6]]").magnitude())
        Assertions.assertEquals(3488, Day18.SnailNumber.of("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude())
    }

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(4140, Day18().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(3993, Day18().part2(input))
    }
}
