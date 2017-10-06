package me.ngrid.examples

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage, ui}
import com.badlogic.gdx.{ApplicationAdapter, Gdx}

class UI2D extends ApplicationAdapter {
  var stage: Stage  = _
  var table: ui.Table =_

  override def create() = {
    stage = new Stage()
    Gdx.input.setInputProcessor(stage)

    val skin = new ui.Skin(Gdx.files.internal("sgx/skin/sgx-ui.json") )
    table = new ui.Table(skin)
    table.setFillParent(true)
    stage.addActor(table)

//    table.setDebug(true)

    val button = new ui.Button(skin)
    val list = new ui.List[String](skin)
    button.setChecked(true)
    button.add("What the fuck buttons").bottom()
    button.addListener(new ClickListener(){
      override def clicked(event: InputEvent, x: Float, y: Float) = {
        println(s"$event, ($x, $y), ${list.getSelected}")
      }
    })


    // Add the widgets
    table.add("This is some sweet ass text")
    table.top.row.bottom
    table.add(button)

    table.row
//    list.setFillParent(true)
    list.getItems.addAll("Wooh", "Mooh", "Blarga blarga")

    table.add(list)
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
