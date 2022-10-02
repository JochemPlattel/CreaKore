package creakore

import kotlin.math.cos
import kotlin.math.sin

class Matrix4(
    var e11: Float, var e12: Float, var e13: Float, var e14: Float,
    var e21: Float, var e22: Float, var e23: Float, var e24: Float,
    var e31: Float, var e32: Float, var e33: Float, var e34: Float,
    var e41: Float, var e42: Float, var e43: Float, var e44: Float,
) {

    fun toFloatArray(): FloatArray {
        return floatArrayOf(e11, e12, e13, e14, e21, e22, e23, e24, e31, e32, e33, e34, e41, e42, e43, e44)
    }

    fun transpose(): Matrix4 {
        return Matrix4(
            e11, e21, e31, e41,
            e12, e22, e32, e42,
            e13, e23, e33, e43,
            e14, e24, e34, e44
        )
    }

    fun translate(translation: Vec2): Matrix4 {
        val m = translation(translation) * this
        this.e11 = m.e11; this.e12 = m.e12; this.e13 = m.e13; this.e14 = m.e14
        this.e21 = m.e21; this.e22 = m.e22; this.e23 = m.e23; this.e24 = m.e24
        this.e31 = m.e31; this.e32 = m.e32; this.e33 = m.e33; this.e34 = m.e34
        this.e41 = m.e41; this.e42 = m.e42; this.e43 = m.e43; this.e44 = m.e44
        return this
    }

    fun scale(scale: Vec2): Matrix4 {
        val m = Companion.scaling(scale) * this
        this.e11 = m.e11; this.e12 = m.e12; this.e13 = m.e13; this.e14 = m.e14
        this.e21 = m.e21; this.e22 = m.e22; this.e23 = m.e23; this.e24 = m.e24
        this.e31 = m.e31; this.e32 = m.e32; this.e33 = m.e33; this.e34 = m.e34
        this.e41 = m.e41; this.e42 = m.e42; this.e43 = m.e43; this.e44 = m.e44
        return this
    }

    fun rotate(angle: Float): Matrix4 {
        val m = rotation(angle) * this
        this.e11 = m.e11; this.e12 = m.e12; this.e13 = m.e13; this.e14 = m.e14
        this.e21 = m.e21; this.e22 = m.e22; this.e23 = m.e23; this.e24 = m.e24
        this.e31 = m.e31; this.e32 = m.e32; this.e33 = m.e33; this.e34 = m.e34
        this.e41 = m.e41; this.e42 = m.e42; this.e43 = m.e43; this.e44 = m.e44
        return this
    }

    override fun toString(): String {
        return """
            [$e11 $e12 $e13 $e14]
            [$e21 $e22 $e23 $e24]
            [$e31 $e32 $e33 $e34]
            [$e41 $e42 $e43 $e44]
        """.trimIndent()
    }

    operator fun times(other: Matrix4): Matrix4 {
        val a = this
        return Matrix4(
            a.e11 * other.e11 + a.e12 * other.e21 + a.e13 * other.e31 + a.e14 * other.e41,
            a.e11 * other.e12 + a.e12 * other.e22 + a.e13 * other.e32 + a.e14 * other.e42,
            a.e11 * other.e13 + a.e12 * other.e23 + a.e13 * other.e33 + a.e14 * other.e43,
            a.e11 * other.e14 + a.e12 * other.e24 + a.e13 * other.e34 + a.e14 * other.e44,
            a.e21 * other.e11 + a.e22 * other.e21 + a.e23 * other.e31 + a.e24 * other.e41,
            a.e21 * other.e12 + a.e22 * other.e22 + a.e23 * other.e32 + a.e24 * other.e42,
            a.e21 * other.e13 + a.e22 * other.e23 + a.e23 * other.e33 + a.e24 * other.e43,
            a.e21 * other.e14 + a.e22 * other.e24 + a.e23 * other.e34 + a.e24 * other.e44,
            a.e31 * other.e11 + a.e32 * other.e21 + a.e33 * other.e31 + a.e34 * other.e41,
            a.e31 * other.e12 + a.e32 * other.e22 + a.e33 * other.e32 + a.e34 * other.e42,
            a.e31 * other.e13 + a.e32 * other.e23 + a.e33 * other.e33 + a.e34 * other.e43,
            a.e31 * other.e14 + a.e32 * other.e24 + a.e33 * other.e34 + a.e34 * other.e44,
            a.e41 * other.e11 + a.e42 * other.e21 + a.e43 * other.e31 + a.e44 * other.e41,
            a.e41 * other.e12 + a.e42 * other.e22 + a.e43 * other.e32 + a.e44 * other.e42,
            a.e41 * other.e13 + a.e42 * other.e23 + a.e43 * other.e33 + a.e44 * other.e43,
            a.e41 * other.e14 + a.e42 * other.e24 + a.e43 * other.e34 + a.e44 * other.e44,
        )
    }

    companion object {
        val identity
            get() = Matrix4(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        val zero
            get() = Matrix4(
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f
            )

        fun translation(translation: Vec2): Matrix4 {
            return Matrix4(
                1f, 0f, 0f, translation.x,
                0f, 1f, 0f, translation.y,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun rotation(angle: Float): Matrix4 {
            val cos = cos(angle)
            val sin = sin(angle)
            return Matrix4(
                cos, -sin, 0f, 0f,
                sin, cos, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun scaling(scale: Vec2): Matrix4 {
            return Matrix4(
                scale.x, 0f, 0f, 0f,
                0f, scale.y, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun fromViewport(origin: Vec2, width: Float, height: Float): Matrix4 {
            return Matrix4(
                2f / width, 0f, 0f, -1 - 2f / width * origin.x,
                0f, 2f / height, 0f, -1 - 2f / height * origin.y,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        }
    }
}