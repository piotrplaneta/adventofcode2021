import java.io.File
import kotlin.math.abs

typealias Point = Pair<Int, Int>
typealias Grid = MutableMap<Point, Int>

private fun input(): List<Pair<Point, Point>> {
    val lines = File("inputs/day05").bufferedReader().readLines()

    return lines.map { line ->
        val points = line.split(" -> ").map { point ->
            Pair(point.split(",")[0].toInt(), point.split(",")[1].toInt())
        }

        Pair(points[0], points[1])
    }
}

private fun isRightLine(line: Pair<Point, Point>): Boolean {
    return line.first.first == line.second.first || line.first.second == line.second.second
}

private fun markRightLines(): Grid {
    return input().filter { isRightLine(it) }.fold(mutableMapOf<Point, Int>()) { acc, (p1, p2) ->
        val xMin = minOf(p1.first, p2.first)
        val xMax = maxOf(p1.first, p2.first)
        val yMin = minOf(p1.second, p2.second)
        val yMax = maxOf(p1.second, p2.second)

        for (x in xMin..xMax) {
            for (y in yMin..yMax) {
                acc[Pair(x, y)] = acc.getOrPut(Pair(x, y)) { 0 } + 1
            }
        }

        acc
    }
}

private fun markDiagonalLines(grid: Grid): Grid {
    return input().filter { !isRightLine(it) }.fold(grid) { acc, (p1, p2) ->
        val xDelta = if (p1.first > p2.first)  -1 else 1
        val yDelta = if (p1.second > p2.second) -1 else 1

        for (delta in 0..abs(p1.first - p2.first)) {
            val point = Pair(p1.first + xDelta * delta, p1.second + yDelta * delta)
            acc[point] = acc.getOrPut(point) { 0 } + 1
        }

        acc
    }
}


private fun main() {
    println(markRightLines().values.count { it > 1 })
    println(markDiagonalLines(markRightLines()).values.count { it > 1 })
}
