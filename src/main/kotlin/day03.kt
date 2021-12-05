import java.io.File
import kotlin.math.pow

private fun input(): List<List<Int>> {
    return File("inputs/day03").bufferedReader().readLines().map { it ->
        it.split("").drop(1).dropLast(1).map { byte -> byte.toInt() }
    }
}

private fun bytesAtPositions(): Map<Int, List<Int>> {
    val bytesAtPositions = mutableMapOf<Int, List<Int>>()
    val codes = input()

    for (index in 0 until codes[0].size) {
        bytesAtPositions[index] = codes.map { it[index] }
    }

    return bytesAtPositions
}

private fun gamma(): List<Int> {
    return bytesAtPositions().map { (position, bytes) ->
        if (bytes.count { it == 0 } > bytes.count { it == 1 }) {
            0
        } else {
            1
        }
    }
}

private fun partOne(): Int {
    val gamma = gamma().joinToString("").toInt(2)
    return gamma * (gamma.inv() + 2.toDouble().pow(12).toInt())
}

fun main(args: Array<String>) {
    println(partOne())
}
