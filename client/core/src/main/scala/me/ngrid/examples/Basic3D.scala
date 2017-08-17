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

  var ship: ModelInstance = _
  val invaders: ArrayBuffer[ModelInstance] = ArrayBuffer()
  val blocks: ArrayBuffer[ModelInstance] = ArrayBuffer()
  var loading = true

  override def resume(): Unit = {}

  override def pause(): Unit = {}

  override def create(): Unit = {
    modelBatch = new ModelBatch()
    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    cam.position.set(10f, 10f, 10f)
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
//    assets.load("ship.g3db", classOf[Model])
    assets.load("level.g3db", classOf[Model])

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
    val model = assets.get("level.g3db", classOf[Model])
//    instances += new ModelInstance(model)
//    instances ++= (for {
//      x <- (-5f).to(5f, 2)
//      z <- (-5f).to(5f, 2)
//    } yield {
//      val instance = new ModelInstance(model)
//      instance.transform.setTranslation(x, 0, z)
//      instance
//    })
    model.nodes.forEach { x =>
      val id = x.id
      val instance = new ModelInstance(model, id)
      val node = instance.getNode(id)

      //Why?
      instance.transform.set(node.globalTransform)
      node.translation.set(0, 0, 0)
      node.scale.set(1,1,1)
      node.rotation.idt()
      instance.calculateTransforms()

      instances += instance

      if(id.equals("ship"))
        ship = instance
      else if (id.startsWith("Block"))
        blocks += instance
      else if (id.startsWith("Invader"))
        invaders += instance
    }

    loading = false
  }

}
