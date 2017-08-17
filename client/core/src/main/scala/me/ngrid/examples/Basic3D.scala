package me.ngrid.examples



import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g3d._
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.graphics.{GL20, PerspectiveCamera}
import com.badlogic.gdx.{ApplicationListener, Gdx}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Basic3D extends ApplicationListener{
  var cam: PerspectiveCamera = _
  var assets: AssetManager = _
  var modelBatch: ModelBatch = _
  var camController: CameraInputController = _
  var environment: Environment = _

  val instances: mutable.ArrayBuffer[ModelInstance] = ArrayBuffer()
  var loading = true

  override def resume(): Unit = {}

  override def pause(): Unit = {}

  override def create(): Unit = {
    modelBatch = new ModelBatch()
    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    cam.position.set(1f, 1f, 1f)
    cam.lookAt(0, 0, 0)
    cam.near = 1f
    cam.far = 300f
    cam.update()

    environment = new Environment()
    environment.set(ColorAttribute.createAmbient(0.4f, 0.4f, 0.4f, 1f))
    environment.add(new DirectionalLight().set(0.8f,  0.8f, 0.8f, -1f, -0.8f, -0.2f))

//    val modelBuilder = new ModelBuilder
//    model = modelBuilder.createBox(5f, 5f, 5f,
//      new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//      Usage.Position | Usage.Normal
//    )
//    val loader = new ObjLoader
//    model = loader.loadModel(Gdx.files.internal("ship.obj"))

    assets = new AssetManager()
    assets.load("ship.obj", classOf[Model])

    camController  = new CameraInputController(cam)
    Gdx.input.setInputProcessor(camController)
  }

  override def resize(width: Int, height: Int): Unit = {}

  override def dispose(): Unit = {
    modelBatch.dispose()
    instances.clear()
    assets.dispose()
  }

  override def render(): Unit = {
    if(loading && assets.update())
      doneLoading()
    camController.update()
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    modelBatch.begin(cam)
    instances.foreach(modelBatch.render(_, environment))
    modelBatch.end()
  }

  def doneLoading (): Unit = {
    val model = assets.get("ship.obj", classOf[Model])
    instances += new ModelInstance(model)
    instances ++= (for {
      x <- (-5f).to(5f, 2)
      z <- (-5f).to(5f, 2)
    } yield {
      val instance = new ModelInstance(model)
      instance.transform.setTranslation(x, 0, z)
      instance
    })

    loading = false
  }

}
