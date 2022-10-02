package creakore

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

object Window2 {
    private var glfwWindow = 0L
    fun create(
        width: Int,
        height: Int,
        title: String = "New CreaK Window",
        fullscreen: Boolean = false,
        backgroundColor: Color = Color.white(),
        vSyncEnabled: Boolean = true,
        icon: Image? = null,
    ) {
        glfwInit()
    }
}

object Window {
    var glfwWindow = 0L
    var width = 1280
        private set
    var height = 720
        private set
    var aspectRatio = width.toFloat() / height

    fun create(
        width: Int,
        height: Int,
        title: String = "My Creak Project",
        backgroundColor: Color = Color.white(),
        resizable: Boolean = false,
        antiAliasingSamples: Int = 8,
        vSyncEnabled: Boolean = true,
    ): Window {
        this.width = width
        this.height = height
        aspectRatio = width.toFloat() / height

        glfwInit()
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_RESIZABLE, if (resizable) 1 else 0)
        glfwWindowHint(GLFW_SAMPLES, antiAliasingSamples)

        glfwWindow = glfwCreateWindow(width, height, title, 0L, 0L)
        glfwMakeContextCurrent(glfwWindow)
        glfwSwapInterval(if (vSyncEnabled) 1 else 0)
        glfwShowWindow(glfwWindow)
        initCreak()
        glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a)
        return this
    }

    fun run(updateFunction: () -> Unit) {
        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents()

            glClear(GL_COLOR_BUFFER_BIT)

            updateFunction()

            glfwSwapBuffers(glfwWindow)
        }
    }
}

fun initCreak() {
    println(1)
    GL.createCapabilities()
    isGLInit = true
    generateGLObjects()
    setupShaders()
}