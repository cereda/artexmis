package com.github.cereda.artexmis

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.util.*

class AppMain : Application() {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      Application.launch(AppMain::class.java)
    }
  }

  override fun start(primaryStage: Stage) {
    try {
      val root = FXMLLoader(javaClass.getResource("/com/github/cereda/artexmis/fxml/MainWindow.fxml"),
          ResourceBundle.getBundle("com/github/cereda/artexmis/bundles/MainWindow", Locale.ENGLISH)).load<Parent>()
      val scene = Scene(root, 800.0, 600.0)
      primaryStage.title = "ArTeXmis - the geeky TeX editor"
      primaryStage.scene = scene
      primaryStage.initStyle(StageStyle.TRANSPARENT)
      primaryStage.show()
    } catch (ex: Exception) {
      ex.printStackTrace()
    }
  }
}
