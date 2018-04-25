package com.github.cereda.artexmis.gui

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Rectangle2D
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.Screen
import javafx.stage.Stage
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import java.net.URL
import java.nio.file.Files
import java.util.*

class MainWindowController : Initializable {
  @FXML
  private lateinit var windowBox: VBox
  @FXML
  private lateinit var codePane: TabPane

  private var unmaxPos: Rectangle2D = Screen.getPrimary().visualBounds
  private var xOffset: Double = 0.0
  private var yOffset: Double = 0.0

  private val extensionFilters = arrayListOf(
      FileChooser.ExtensionFilter("TeX files (*.tex)", "*.tex"),
      FileChooser.ExtensionFilter("TeX classes/packages", "*.sty", "*.dtx", "*.cls", "*.ltx"),
      FileChooser.ExtensionFilter("TeX auxiliary files", "*.aux", "*.bbl", "*.bib"),
      FileChooser.ExtensionFilter("Lua files (*.lua)", "*.lua"),
      FileChooser.ExtensionFilter("Asymptote files (*.asy)", "*.asy"),
      FileChooser.ExtensionFilter("All files", "*.*"))

  private fun addTab(title: String = "Untitled") {
    val codeArea = CodeArea()
    val newTab = Tab(title)
    codeArea.prefWidthProperty().bind(codePane.widthProperty())
    codeArea.prefHeightProperty().bind(codePane.heightProperty())
    codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
    newTab.content = VirtualizedScrollPane(codeArea)
    codePane.tabs.add(newTab)
  }

  private fun getCurrentCodeArea(): CodeArea {
    return ((codePane.selectionModel.selectedItem.content as VirtualizedScrollPane<*>).getContent() as CodeArea)
  }

  override fun initialize(p0: URL?, p1: ResourceBundle?) {
    addTab()
    addTab()
    windowBox.setOnMousePressed {
      xOffset = it.sceneX
      yOffset = it.sceneY
    }
    windowBox.setOnMouseDragged {
      (windowBox.scene.window as Stage).run {
        x = it.screenX - xOffset
        y = it.screenY - yOffset
      }
    }

  }

  // TODO: window resize and snapping

  @FXML
  fun maximizeWindow(actionEvent: ActionEvent) {
    // TODO: find out why maximize property doesn't work
    val currScreen = Screen.getScreensForRectangle(windowBox.scene.x,windowBox.scene.y,windowBox.width,windowBox.height)[0]
    (windowBox.scene.window as Stage).run {
      if(this.width == currScreen.visualBounds.width) {
        width = unmaxPos.width
        height = unmaxPos.height
        x = unmaxPos.minX
        y = unmaxPos.minY
      } else {
        unmaxPos = Rectangle2D(x, y, width, height)
        width = currScreen.visualBounds.width
        height = currScreen.visualBounds.height
        x = currScreen.visualBounds.minX
        y = currScreen.visualBounds.minY
      }
    }
  }

  @FXML
  fun minimizeWindow(actionEvent: ActionEvent) {
    val currStage = windowBox.scene.window as Stage
    currStage.isIconified = !currStage.isIconified
  }

  @FXML
  fun quitApp(actionEvent: ActionEvent) {
    Platform.exit()
  }

  @FXML
  fun openFile(actionEvent: ActionEvent) {
    val fc = FileChooser()
    fc.title = "Open TeX-related file"
    fc.extensionFilters += extensionFilters
    val ret = fc.showOpenDialog(codePane.scene.window)
    if (ret != null) {
      getCurrentCodeArea().appendText(Files.readAllLines(ret.toPath()).joinToString("\n"))
    } else {
      Alert(Alert.AlertType.WARNING, "Could not open the file you have not selected.", ButtonType.CLOSE)
    }
  }

  @FXML
  fun saveFile(actionEvent: ActionEvent) {
    val fc = FileChooser()
    fc.title = "Open TeX-related file"
    fc.extensionFilters += extensionFilters
    val ret = fc.showSaveDialog(codePane.scene.window)
    if (ret != null) {
      Files.write(ret.toPath(),getCurrentCodeArea().text.toByteArray())
    } else {
      Alert(Alert.AlertType.WARNING, "Could not save to the file you have not selected.", ButtonType.CLOSE)
    }
  }
}