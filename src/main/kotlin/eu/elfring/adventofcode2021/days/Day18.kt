package eu.elfring.adventofcode2021.days

import eu.elfring.adventofcode2021.AoCPuzzle
import java.util.LinkedList
import kotlin.math.ceil
import kotlin.math.floor

class Day18 : AoCPuzzle(18)
{
    override fun part1(input: List<String>): Any
    {
        return input.map { SnailNumber.of(it) }.reduce { x, y -> x + y }.magnitude()
    }

    override fun part2(input: List<String>): Any
    {
        return input.flatMap { a ->
            input.mapNotNull { b ->
                if (a != b) (SnailNumber.of(a) + SnailNumber.of(b)).magnitude() else null
            }
        }.maxOf { it }
    }

    class SnailNumber(
        private var left: SnailNumber?,
        private var right: SnailNumber?,
        private var value: Int?,
        private var parent: SnailNumber? = null)
    {
        init
        {
            left?.parent = this
            right?.parent = this
        }

        operator fun plus(other: SnailNumber) = SnailNumber(this, other, null).also { it.reduce() }

        fun magnitude(): Int
        {
            return if (value != null)
            {
                value!!
            }
            else
            {
                left!!.magnitude() * 3 + right!!.magnitude() * 2
            }
        }

        fun reduce(): SnailNumber
        {
            while (true)
            {
                val endValues = getEndSnailNumbers()
                if (endValues.any { it.tryExplode() })
                {
                    continue
                }
                else if (endValues.any { it.trySplit() })
                {
                    continue
                }
                break
            }
            return this
        }

        private fun getEndSnailNumbers(): List<SnailNumber>
        {
            if (value != null)
            {
                return listOf(this)
            }
            return left!!.getEndSnailNumbers() + right!!.getEndSnailNumbers()
        }

        private fun tryExplode(): Boolean
        {
            if (this.parent?.parent?.parent?.parent?.parent != null)
            {
                this.parent!!.explode()
                return true
            }

            return false
        }

        private fun trySplit(): Boolean
        {
            if (value != null && value!! >= 10)
            {
                split()
                return true
            }
            return false
        }

        private fun explode()
        {
            val leftNumber = getLeftSnailNumber()?.getRightMostChild()
            val rightNumber = getRightSnailNumber()?.getLeftMostChild()
            leftNumber?.value = leftNumber!!.value!! + left!!.value!!
            rightNumber?.value = rightNumber!!.value!! + right!!.value!!

            left = null
            right = null
            value = 0
        }

        private fun split()
        {
            val leftNumber = floor(value!! / 2.0).toInt()
            val rightNumber = ceil(value!! / 2.0).toInt()
            left = SnailNumber(null, null, leftNumber, this)
            right = SnailNumber(null, null, rightNumber, this)
            value = null
        }

        private fun getRightSnailNumber(): SnailNumber?
        {
            return if (parent == null || parent?.right == null)
            {
                null
            }
            else if (parent!!.right != this)
            {
                parent!!.right
            }
            else
            {
                parent!!.getRightSnailNumber()
            }
        }

        private fun getLeftSnailNumber(): SnailNumber?
        {
            return if (parent == null || parent?.left == null)
            {
                null
            }
            else if (parent!!.left != this)
            {
                parent!!.left
            }
            else
            {
                parent!!.getLeftSnailNumber()
            }
        }

        private fun getRightMostChild(): SnailNumber?
        {
            return if (value != null)
            {
                this
            }
            else if (right!!.value != null)
            {
                right
            }
            else
            {
                right!!.getRightMostChild()
            }
        }

        private fun getLeftMostChild(): SnailNumber?
        {
            return if (value != null)
            {
                this
            }
            else if (left!!.value != null)
            {
                left
            }
            else
            {
                left!!.getLeftMostChild()
            }
        }

        override fun toString(): String
        {
            return if (left != null && right != null)
            {
                "[$left,$right]"
            }
            else
            {
                "$value"
            }
        }

        companion object
        {
            fun of(input: String) = of(LinkedList(input.toList()))

            private fun of(input: LinkedList<Char>): SnailNumber
            {
                val firstChar = input.pop()
                if (firstChar != '[')
                {
                    throw IllegalArgumentException("Illegal character to start SnailNumber: $firstChar")
                }

                val left = when
                {
                    input.first == '['    -> of(input)
                    input.first.isDigit() -> SnailNumber(null, null, input.pop().digitToInt())
                    else                  -> throw IllegalArgumentException("Illegal character")
                }

                val separator = input.pop()
                if (separator != ',')
                {
                    throw IllegalArgumentException("Illegal separator: $separator")
                }

                val right = when
                {
                    input.first == '['    -> of(input)
                    input.first.isDigit() -> SnailNumber(null, null, input.pop().digitToInt())
                    else                  -> throw IllegalArgumentException("Illegal character")
                }

                val end = input.pop()
                if (end != ']')
                {
                    throw IllegalArgumentException("Illegal end character: $end")
                }

                return SnailNumber(left, right, null)
            }
        }
    }
}
