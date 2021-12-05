import java.io.File
import kotlin.math.pow

private fun input(): List<List<Int>> {
    return File("inputs/day03").bufferedReader().readLines().map { it ->
        it.split("").drop(1).dropLast(1).map { byte -> byte.toInt() }
    }
}

private fun bytesAtPositions(sequences: List<List<Int>>): Map<Int, List<Int>> {
    val bytesAtPositions = mutableMapOf<Int, List<Int>>()

    for (index in 0 until sequences[0].size) {
        bytesAtPositions[index] = sequences.map { it[index] }
    }

    return bytesAtPositions
}

private fun mostPopularByte(bytes: List<Int>): Int {
    return if (bytes.count { it == 0 } > bytes.count { it == 1 }) {
        0
    } else {
        1
    }
}

private fun leastPopularByte(bytes: List<Int>): Int {
    return if (bytes.count { it == 0 } <= bytes.count { it == 1 }) {
        0
    } else {
        1
    }
}

private fun gamma(): List<Int> {
    return bytesAtPositions(input()).map { (_, bytes) ->
        mostPopularByte(bytes)
    }
}

private fun rating(moreWins: Boolean, selectionFunction: (List<Int>) -> Int): List<Int> {
    var numbers = input()
    var index = 0
    while (numbers.size > 1) {
        val selectedByte = selectionFunction(numbers.map { it[index] })
        numbers = numbers.filter { it[index] == selectedByte }
        index += 1
    }

    return numbers[0]
}

private fun oxygenRating(): List<Int> {
    return rating(true, ::mostPopularByte)
}

private fun carbonRating(): List<Int> {
    return rating(false, ::leastPopularByte)
}


private fun partOne(): Int {
    val gamma = gamma().joinToString("").toInt(2)
    return gamma * (gamma.inv() + 2.toDouble().pow(12).toInt())
}

private fun partTwo(): Int {
    return oxygenRating().joinToString("").toInt(2) * carbonRating().joinToString("").toInt(2)
}

private fun main(args: Array<String>) {
    println(partOne())
    println(partTwo())
}
