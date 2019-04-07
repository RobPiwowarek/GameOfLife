package logic

class Game {
  private def neighbours(cells: Set[(Long, Long)]) =
    cells.flatMap {
      case (x, y) =>
        for {
          _x <- x - 1 to x + 1
          _y <- y - 1 to y + 1
        } yield (_x, _y)
    }

  private def aliveNeighbours(cell: (Long, Long), cells: Set[(Long, Long)]) =
    cells.count(c => c != cell && math.abs(c._1 - cell._1) <= 1 && math.abs(c._2 - cell._2) <= 1)

  def evolve(cells: Set[(Long, Long)]): Set[(Long, Long)] =
    neighbours(cells).filter {
      cell =>
        val n = aliveNeighbours(cell, cells)
        (cells contains cell) && n == 2 || n == 3
    }
}
