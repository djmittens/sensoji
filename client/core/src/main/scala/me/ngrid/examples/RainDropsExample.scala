package me.ngrid.examples

import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.math.{MathUtils, Rectangle, Vector3}
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input}

class RainDropsExample extends ApplicationAdapter {
  var assets: Assets = _
  var bucket: Bucket = _
  var rainDrops: List[RainDrop] = List()
  var lastDropTime: Long = 0

  override def create(): Unit = {
    assets = Assets(
      dropImage = new Texture(Gdx.files.internal("droplet.png")),
      dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav")),
      rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3")),
      camera = new OrthographicCamera(),
      batch = new SpriteBatch()
    )

    assets.rainMusic.setLooping(true)
    assets.rainMusic.play()
    assets.camera.setToOrtho(false, 800, 480)
    bucket = Bucket( new Texture(Gdx.files.internal("bucket.png")))
  }


  override def render(): Unit = {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    assets.camera.update()

    if(Gdx.input.isTouched()) {
      val touchPos = new Vector3()
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0)
      assets.camera.unproject(touchPos)
      bucket.x = touchPos.x - 64 / 2
    }

    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime

    if(bucket.x < 0) bucket.x = 0
    if(bucket.x > 800 - 64) bucket.x = 800 -64

    // Droppings
    spawnRaindrop()
    if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop()

    rainDrops = rainDrops.filter{ x =>
      x.move()
    } filter { x =>
      if(x.overlaps(bucket)) {
        assets.dropSound.play()
        false
      } else {
        true
      }
    }

    assets.renderBatch { batch =>
      bucket.draw(batch)
      rainDrops.foreach(_.draw(batch))
    }
  }

  def spawnRaindrop(): Unit = {
    rainDrops = RainDrop(assets.dropImage) +: rainDrops
    lastDropTime = TimeUtils.nanoTime()
  }

  case class Assets
  (
    dropImage: Texture,
    dropSound: Sound,
    rainMusic: Music,
    camera: OrthographicCamera,
    batch: SpriteBatch
  ) {
    def renderBatch(f: SpriteBatch => Unit): Unit = {
      batch.begin()
      batch.setProjectionMatrix(camera.combined)
      try {
        f(batch)
      } finally {
        batch.end()
      }
    }
  }

  override def dispose(): Unit = {
    assets.dropImage.dispose()
    bucket.image.dispose()
    assets.rainMusic.dispose()
    assets.batch.dispose()
  }
}

case class Bucket (image: Texture) extends Rectangle with Drawble2D {
  x = 800 / 2 - 64/2
  y = 20
  width = 64
  height = 64
}

case class RainDrop(image: Texture) extends Rectangle with Drawble2D {
  x = MathUtils.random(0, 800 - 64)
  y = 480
  width = 64
  height = 64

  def move(): Boolean = {
    y -= 200 * Gdx.graphics.getDeltaTime

    y + 64 >= 0
  }
}

trait Drawble2D { self: Rectangle =>
  def image: Texture

  def draw(batch: SpriteBatch): Unit = {
    batch.draw(image, x, y)
  }
}
