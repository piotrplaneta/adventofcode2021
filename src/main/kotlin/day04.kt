import java.io.File

typealias Board = MutableMap<Pair<Int, Int>, Pair<Int, Boolean>>
private fun input(): Pair<List<Int>, List<Board>> {
    val lines = File("inputs/day04").bufferedReader().readLines()

    val drawnNumbers = lines[0].split(",").map { it.toInt()}

    val boards = lines.drop(1).chunked(6).map {
        it.drop(1).map { line -> line.split(Regex("""\s+""")).filter { n -> n != ""}.map { n -> Pair(n.toInt(), false) } }
    }
    val boardMaps = boards.map {
        var boardMap = mutableMapOf<Pair<Int, Int>, Pair<Int, Boolean>>()
        for (y in 0..4) {
            for (x in 0..4) {
                boardMap[Pair(y, x)] = it[y][x]
            }
        }
        boardMap
    }

    return Pair(drawnNumbers, boardMaps)
}

private fun playOneRound(number: Int, boards: List<Board>): List<Board> {
    var newBoards = boards.toMutableList()

    for (board in newBoards) {
        for (y in 0..4) {
            for (x in 0..4) {
                if (board[Pair(y, x)]!!.first == number) {
                    board[Pair(y, x)] = Pair(number, true)
                }
            }
        }
    }

    return newBoards
}

private fun winner(boards: List<Board>): Pair<Boolean, Board?> {
    for (board in boards) {
        for (y in 0..4) {
            val row = board.filter { (k, v) -> k.first == y }
            if (row.all { (_, v) -> v.second }) {
                return Pair(true, board)
            }
        }

        for (x in 0..4) {
            val column = board.filter { (k, v) -> k.second == x }
            if (column.all { (_, v) -> v.second }) {
                return Pair(true, board)
            }
        }
    }

    return Pair(false, null)
}

private fun winnerScore(numbers: List<Int>, boards: List<Board>): Int {
    var index = 0
    var afterRoundBoards = playOneRound(numbers[index], boards)

    while (!winner(afterRoundBoards).first) {
        index += 1
        afterRoundBoards = playOneRound(numbers[index], boards)
    }

    val winner =  winner((afterRoundBoards))
    return winner.second!!.values.filter { (_, v) -> !v }.sumOf { it.first } * numbers[index]
}

private fun loser(boards: List<Board>): Pair<Boolean, Board?> {
    val winners = boards.map { false }.toMutableList()

    for ((index, board) in boards.withIndex()) {
        for (y in 0..4) {
            val row = board.filter { (k, v) -> k.first == y }
            if (row.all { (_, v) -> v.second }) {
                winners[index] = true
            }
        }

        for (x in 0..4) {
            val column = board.filter { (k, v) -> k.second == x }
            if (column.all { (_, v) -> v.second }) {
                winners[index] = true
            }
        }
    }

    return if (winners.count { !it } == 1) {
        Pair(true, boards[winners.indexOf(false)])
    } else {
        Pair(false, null)
    }
}

private fun loserScore(numbers: List<Int>, boards: List<Board>): Int {
    var index = 0
    var afterRoundBoards = playOneRound(numbers[index], boards)

    while (!loser(afterRoundBoards).first) {
        index += 1
        afterRoundBoards = playOneRound(numbers[index], boards)
    }

    return winnerScore(numbers.drop(index), listOf(loser(afterRoundBoards).second!!))
}

private fun main(args: Array<String>) {
    val input = input()

    println(winnerScore(input.first, input.second))
    println(loserScore(input.first, input.second))
}
