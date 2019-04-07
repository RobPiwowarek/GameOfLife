package gui

import logic.Game
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Separator, ToggleButton, ToolBar}
import scalafx.scene.layout.BorderPane
import scalafx.scene.{Group, Scene}
import scalafx.util.Duration

object GameOfLife extends JFXApp {
  val cellGrid = new CellGrid
  cellGrid.enablePlotting()

  stage = new PrimaryStage {
    title = "Game of Life"
    width = 800
    height = 600
    resizable = false

    scene = new Scene {
      root = new BorderPane {
        top = new ToolBar {
          val game = new Game

          val timeline: Timeline = new Timeline {
            cycleCount = Timeline.Indefinite
            keyFrames = KeyFrame(Duration(100), onFinished = (e: ActionEvent) => {
              val seed = cellGrid.cellsGrid
              cellGrid.clear()
              val evolved = game.evolve(seed)
              evolved.foreach(cell => cellGrid.drawCell(cell._1, cell._2))
            })
          }

          val startButton: ToggleButton = new ToggleButton("Start") {
            handleEvent(ActionEvent.Action) {
              e: ActionEvent =>
                if (!selected.value)
                  timeline.pause()
                else {
                  cellGrid.disablePlotting()
                  if (cellGrid.cellsGrid.nonEmpty)
                    timeline.play()
                }
            }
          }

          val restartButton: Button = new Button("Restart") {
            handleEvent(ActionEvent.Any) {
              e: ActionEvent =>
                if (startButton.selected.value)
                  startButton.fire()

                cellGrid.clear()
                cellGrid.enablePlotting()
                timeline.stop()
            }
          }

          content = List(
            startButton,
            restartButton,
            new Separator)
        }
        center = new Group {
          cellGrid.width <== width
          cellGrid.height <== height
          children = cellGrid
        }
      }
    }
  }
}
