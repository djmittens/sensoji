package me.ngrid.examples


import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d._
import com.badlogic.gdx.graphics.g3d.utils.{CameraInputController, DefaultTextureBinder, ModelBuilder, RenderContext}
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.{Camera, GL20, PerspectiveCamera}
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.{ApplicationListener, Gdx}

class ShaderTest extends ApplicationListener{
  var cam: PerspectiveCamera = _
  var camController: CameraInputController = _
  var shader: Shader = _
  var renderContext: RenderContext = _
  var model: Model = _
  var rend: Renderable = _

  override def resume() = {}

  override def pause() = {}

  override def create(): Unit = {
    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    cam.position.set(2f, 2f, 2f)
    cam.lookAt(0, 0, 0)
    cam.near = 1f
    cam.update()

    camController = new CameraInputController(cam)
    Gdx.input.setInputProcessor(camController)

    val modelBuilder = new ModelBuilder
    model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, new Material(),
      Usage.Position | Usage.Normal | Usage.TextureCoordinates)

    val blockPart = model.nodes.get(0).parts.get(0)

    rend = new Renderable
    blockPart.setRenderable(rend)
    rend.environment = null
    rend.worldTransform.idt()
//    rend.meshPart.primitiveType = GL20.GL_LINES

    renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED,  1))
    shader = new TestShader()
    shader.init()
  }

  override def resize(width: Int, height: Int) = {}

  override def dispose() = {
    shader.dispose()
    model.dispose()
  }

  override def render(): Unit = {
    camController.update()
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    renderContext.begin()
    shader.begin(cam, renderContext)
    shader.render(rend)
    shader.end()
    renderContext.end()
  }
}

class TestShader extends Shader {
  var program: ShaderProgram = _
  var cam: Camera = _
  var ctx: RenderContext = _
  var u_projViewTrans: Int = _
  var u_worldTrans: Int = _


  override def canRender(instance: Renderable) = true

  override def begin(camera: Camera, context: RenderContext): Unit = {
    this.cam = camera
    this.ctx = context
    program.begin()
    program.setUniformMatrix(u_projViewTrans, camera.combined)
    context.setDepthTest(GL20.GL_LEQUAL)
    context.setCullFace(GL20.GL_BACK)
  }

  override def init(): Unit = {
    val vert = Gdx.files.internal("test.vertex.glsl").readString()
    val frag = Gdx.files.internal("test.fragment.glsl").readString()
    program = new ShaderProgram(vert, frag)
    if(!program.isCompiled)
      throw new GdxRuntimeException(program.getLog)
    u_projViewTrans = program.getUniformLocation("u_projViewTrans")
    u_worldTrans = program.getUniformLocation("u_worldTrans")
  }

  override def compareTo(other: Shader) = 0

  override def end() = {}


  override def render(renderable: Renderable) = {
    program.setUniformMatrix(u_worldTrans, renderable.worldTransform)
    renderable.meshPart.render(program)
  }

  override def dispose() = {
    program.dispose()
  }
}
