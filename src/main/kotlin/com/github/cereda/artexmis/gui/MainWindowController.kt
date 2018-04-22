package com.github.cereda.artexmis.gui

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import java.net.URL
import java.nio.file.Files
import java.util.*

class MainWindowController : Initializable {
  @FXML
  private lateinit var codePane: AnchorPane

  private var codeArea = CodeArea()

  private val extensionFilters = arrayListOf(
      FileChooser.ExtensionFilter("TeX files", "*.tex"),
      FileChooser.ExtensionFilter("TeX class/packages", "*.sty", "*.dtx", "*.cls", "*.ltx"),
      FileChooser.ExtensionFilter("TeX auxiliary files", "*.aux", "*.bbl", "*.bib"),
      FileChooser.ExtensionFilter("Lua files", "*.lua"),
      FileChooser.ExtensionFilter("Asymptote files", "*.asy"))

  override fun initialize(p0: URL?, p1: ResourceBundle?) {
    codeArea.prefWidthProperty().bind(codePane.widthProperty())
    codeArea.prefHeightProperty().bind(codePane.heightProperty())
    codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
    codePane.children.add(VirtualizedScrollPane(codeArea))
  }

  fun quitApp(actionEvent: ActionEvent) {
    Platform.exit()
  }

  fun openFile(actionEvent: ActionEvent) {
    val fc = FileChooser()
    fc.title = "Open TeX-related file"
    fc.extensionFilters += extensionFilters
    val ret = fc.showOpenDialog(codePane.scene.window)
    if (ret != null) {
      codeArea.appendText(Files.readAllLines(ret.toPath()).joinToString("\n"))
    } else {
      Alert(Alert.AlertType.WARNING, "Could not open the file you have not selected.", ButtonType.CLOSE)
    }
  }

  fun saveFile(actionEvent: ActionEvent) {
    val fc = FileChooser()
    fc.title = "Open TeX-related file"
    fc.extensionFilters += extensionFilters
    val ret = fc.showSaveDialog(codePane.scene.window)
    if (ret != null) {
      Files.write(ret.toPath(),codeArea.text.toByteArray())
    } else {
      Alert(Alert.AlertType.WARNING, "Could not save to the file you have not selected.", ButtonType.CLOSE)
    }
  }
}