package creakore

import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

class RenderUnit(
    val usage: Int,
    val attributeLayout: IntArray,
    val useIndices: Boolean = false
): GLObject() {
    var vao = 0
    var vbo = 0
    var ebo = 0

    init {
        init()
    }
    override fun generate() {
        vao = glGenVertexArrays()
        vbo = glGenBuffers()
        glBindVertexArray(vao)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, 1000000, usage)
        if (useIndices) {
            ebo = glGenBuffers()
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, 100000, usage)
        }
        var pointer = 0L
        for (i in attributeLayout.indices) {
            glVertexAttribPointer(
                i,
                attributeLayout[i],
                GL_FLOAT,
                false,
                attributeLayout.sum() * Float.SIZE_BYTES,
                pointer * Float.SIZE_BYTES
            )
            glEnableVertexAttribArray(i)
            pointer += attributeLayout[i]
        }
    }
    fun setVertices(floatArray: FloatArray) {
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, floatArray)
    }
    fun setIndices(indices: IntArray) {
        glBindVertexArray(vao)
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indices)
    }
    fun draw(primitive: Int, start: Int, count: Int) {
        glBindVertexArray(vao)
        glDrawArrays(primitive, start, count)
    }
    fun drawIndexed(primitive: Int, count: Int) {
        glBindVertexArray(vao)
        glDrawElements(primitive, count, GL_UNSIGNED_INT, 0)
    }
}

val GLObjects = mutableListOf<GLObject>()
var isGLInit = false

fun generateGLObjects() {
    println(2)
    for (it in GLObjects)
        it.generate()
}

abstract class GLObject {
    fun init() {
        if (isGLInit)
            generate()
        else
            GLObjects.add(this)
    }
    abstract fun generate()
}