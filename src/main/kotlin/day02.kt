import java.io.File

private fun input(): List<Pair<String, Int>> {
    return File("inputs/day02").bufferedReader().readLines().map {
        Pair(it.split(" ")[0], it.split(" ")[1].toInt())
    }
}

private fun partOne(): Pair<Int, Int> {
    return input().fold(Pair(0, 0)) { (acc_f, acc_d), (move, amount) ->
        when(move) {
            "forward" -> Pair(acc_f + amount, acc_d)
            "down" -> Pair(acc_f, acc_d + amount)
            "up" -> Pair(acc_f, acc_d - amount)
            else -> throw Exception()
        }
    }
}

private fun partTwo(): Triple<Int, Int, Int> {
    return input().fold(Triple(0, 0, 0)) { (acc_f, acc_d, acc_aim), (move, amount) ->
        when(move) {
            "forward" -> Triple(acc_f + amount, acc_d + amount * acc_aim, acc_aim)
            "down" -> Triple(acc_f, acc_d, acc_aim + amount)
            "up" -> Triple(acc_f, acc_d, acc_aim - amount)
            else -> throw Exception()
        }
    }
}

private fun main(args: Array<String>) {
    println(partOne().first * partOne().second)
    println(partTwo().first * partTwo().second)
}
