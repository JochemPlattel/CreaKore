import creakore.*
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL15.GL_TRIANGLES

val window = Window.create(1280, 720)
val renderUnit = RenderUnit(GL_STATIC_DRAW, intArrayOf(2, 4))
fun main() {
    window.run(::update)
}

fun update() {
    colorShader.use()
    colorShader.setMat4("projection", Matrix4.fromViewport(Vec2.zero, 1f, 1f))
    val vertices = floatArrayOf(
        0f, 0f, 0f, 0f, 1f, 1f,
        1f, 0f, 0f, 1f, 0f, 1f,
        1f, 1f, 1f, 0f, 0f, 1f
    )
    renderUnit.setVertices(vertices)
    renderUnit.draw(GL_TRIANGLES, 0, 3)
}