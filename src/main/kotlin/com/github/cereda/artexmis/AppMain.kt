package com.github.cereda.artexmis

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class AppMain : Application() {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      Application.launch(AppMain::class.java)
    }
  }

  override fun start(primaryStage: Stage) {
    try {
      val root = FXMLLoader(javaClass.getResource("/fxml/MainWindow.fxml")).load<Parent>()
      val scene = Scene(root, 800.0, 600.0)
      primaryStage.title = "ArTeXmis - the geeky TeX editor"
      primaryStage.scene = scene
      primaryStage.show()
    } catch (ex: Exception) {
      println(ex.message)
    }
  }
}