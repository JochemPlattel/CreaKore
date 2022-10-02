package creakore

import org.lwjgl.opengl.GL20.*

var activeShader: Shader? = null

class Shader {
    var isCreated = false
    private var id = 0
    var attributeSizes = intArrayOf()
    var vertexShaderSource = ""
    var fragmentShaderSource = ""

    fun prepare() {
        val vertexID = glCreateShader(GL_VERTEX_SHADER)
        val fragmentID = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(vertexID, vertexShaderSource)
        glCompileShader(vertexID)
        var status = glGetShaderi(vertexID, GL_COMPILE_STATUS)
        if (status == GL_FALSE) {
            val len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)
            throw Exception("VertexShader compilation failed: " + glGetShaderInfoLog(vertexID, len))
        }
        glShaderSource(fragmentID, fragmentShaderSource)
        glCompileShader(fragmentID)
        status = glGetShaderi(fragmentID, GL_COMPILE_STATUS)
        if (status == GL_FALSE) {
            val len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH)
            throw Exception("FragmentShader compilation failed: " + glGetShaderInfoLog(fragmentID, len))
        }
        id = glCreateProgram()
        glAttachShader(id, vertexID)
        glAttachShader(id, fragmentID)
        glLinkProgram(id)
        status = glGetProgrami(id, GL_LINK_STATUS)
        if (status == GL_FALSE) {
            val len = glGetProgrami(vertexID, GL_INFO_LOG_LENGTH)
            throw Exception(glGetProgramInfoLog(vertexID, len))
        }
        glDeleteShader(vertexID)
        glDeleteShader(fragmentID)

        isCreated = true
    }

    fun use() {
        if (!isCreated)
            throw Exception("ShaderProgram cannot be used before it's been created")
        if (activeShader != this)
            glUseProgram(id)
    }

    fun setInt(uniformName: String, value: Int) {
        use()
        glUniform1i(glGetUniformLocation(id, uniformName), value)
    }

    fun setFloat(uniformName: String, value: Float) {
        use()
        glUniform1f(glGetUniformLocation(id, uniformName), value)
    }

    fun setVec2(uniformName: String, value: Vec2) {
        use()
        glUniform2f(glGetUniformLocation(id, uniformName), value.x, value.y)
    }
    fun setVec4(uniformName: String, value: Color) {
        use()
        glUniform4f(glGetUniformLocation(id, uniformName), value.r, value.g, value.b, value.a)
    }

    fun setMat4(uniformName: String, value: Matrix4) {
        use()
        glUniformMatrix4fv(glGetUniformLocation(id, uniformName), true, value.toFloatArray())
    }
}

val colorShader = Shader()
val textureShader = Shader()

fun setupShaders() {
    colorShader.vertexShaderSource = """
        #version 400
        layout (location = 0) in vec2 aPosition;
        layout (location = 1) in vec4 aColor;
        uniform mat4 projection;
        out vec4 color;
        
        void main() {
            color = aColor;
            gl_Position = projection * vec4(aPosition, 0, 1);
        }
    """.trimIndent()
    colorShader.fragmentShaderSource = """
        #version 400
        in vec4 color;
        void main() {
            gl_FragColor = color;
        }
    """.trimIndent()
    colorShader.prepare()

    textureShader.vertexShaderSource = """
        #version 400
        layout (location = 0) in vec2 aPosition;
        layout (location = 1) in vec2 aTexCoords;
        uniform mat4 projection;
        uniform mat4 transformation;
        
        out vec2 texCoords;
        
        void main() {
            texCoords = aTexCoords;
            gl_Position = projection * transformation * vec4(aPosition, 0, 1);
        }
    """.trimIndent()
    textureShader.fragmentShaderSource = """
        #version 400
        in vec2 texCoords;
        uniform sampler2D sampler;
        void main() {
            gl_FragColor = texture(sampler, texCoords);
        }
    """.trimIndent()
    textureShader.prepare()
}