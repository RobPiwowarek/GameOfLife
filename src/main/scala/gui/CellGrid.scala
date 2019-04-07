package gui

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

class CellGrid extends Canvas {
  private val cellDimension = 10
  private val gc = graphicsContext2D
  private var cells: Set[(Long, Long)] = Set.empty
  gc.fill = Color.Black

  def clear(): Unit = {
    gc.clearRect(0, 0, width.value, height.value)
    cells = Set.empty
  }

  def cellsGrid: Set[(Long, Long)] = cells

  def drawCell(x: Long, y: Long): Unit = {
    gc.fillRect(x * cellDimension, y * cellDimension, cellDimension, cellDimension)
    cells = cells + ((x, y))
  }

  def clearCell(x: Long, y: Long): Unit = {
    gc.clearRect(x*cellDimension, y*cellDimension, cellDimension, cellDimension)
    cells = cells - ((x, y))
  }

  def enablePlotting(): Unit = {
    addEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler)
    addEventHandler(MouseEvent.MOUSE_DRAGGED, clickEventHandler)
  }

  def disablePlotting(): Unit = removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler)

  private val clickEventHandler = new EventHandler[MouseEvent] {
    override def handle(e: MouseEvent): Unit = {
      val x = ((e.getX - (e.getX % cellDimension)) / cellDimension).toLong
      val y = ((e.getY - (e.getY % cellDimension)) / cellDimension).toLong

      if (cells.contains((x, y)))
        clearCell(x, y)
      else
        drawCell(x, y)
    }
  }
}
