package com.github.cereda.artexmis.gui

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import java.net.URL
import java.text.DecimalFormat
import java.util.*

class MainWindowController : Initializable {
  @FXML
  private lateinit var input: TextField
  @FXML
  private lateinit var output: Label
  @FXML
  private lateinit var progress: ProgressIndicator
  @FXML
  private lateinit var progsimulator: Slider

  override fun initialize(p0: URL?, p1: ResourceBundle?) {
    progsimulator.valueProperty().addListener({ _, _, _ ->
      progress.progressProperty().set(progsimulator.value / progsimulator.max)
    })
  }

  fun openFile(actionEvent: ActionEvent) {
    output.text = "NIY"
  }

  fun quitApp(actionEvent: ActionEvent) {
    Platform.exit()
  }

  fun processProgress(actionEvent: ActionEvent) {
    val maxint = Integer.parseInt(input.text) * 10000
    (0..maxint).forEach {
      // this doesn't update the UI fast enough
      progress.progressProperty().set(it.toDouble() / maxint.toDouble())
      output.text = DecimalFormat("##.##").format(progress.progress)
    }
  }
}