package me.ngrid.examples

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table

class UI2D extends ApplicationAdapter {
  var stage: Stage  = _
  var table: Table =_

  override def create() = {
    stage = new Stage()
    Gdx.input.setInputProcessor(stage)

    table = new Table()
    table.setFillParent(true)
    stage.addActor(table)

    table.setDebug(true)

    // Add the widgets
  }

  override def resize(width: Int, height: Int) = {
    stage.getViewport.update(width, height, true)
  }

  override def dispose() = {
    stage.dispose()
  }

  override def render() = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.act(Gdx.graphics.getDeltaTime)
    stage.draw()
  }
}
