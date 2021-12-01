import java.io.File

fun input(): List<Int> = File("inputs/day01").bufferedReader().readLines().map { it.toInt() }

fun partOne(): Int {
    return input().zip(input().drop(1)).map { (first, second) -> second > first }.count { it }
}

fun partTwo(): Int {
    val triplets = input().zip(input().drop(1)).zip(input().drop(2)).map {
        listOf(it.first.first, it.first.second, it.second)
    }

    return triplets.zip(triplets.drop(1)).map { (first, second) -> second.sum() > first.sum() }.count { it }
}

fun main(args: Array<String>) {
    println(partOne())
    println(partTwo())
}
