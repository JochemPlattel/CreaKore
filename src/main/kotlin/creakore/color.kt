package creakore

import kotlin.random.Random

class Color(var r: Float, var g: Float, var b: Float, var a: Float = 1f) {

    override fun toString(): String {
        return ("$r, $g, $b")
    }

    companion object {
        fun byte(r: Float, g: Float, b: Float, a: Float = 1f): Color {
            return Color(r / 255, g / 255, b / 255, a)
        }

        fun random(): Color {
            return Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
        }

        fun random(alpha: Float): Color {
            return Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), alpha)
        }

        fun red(a: Float = 1f): Color {
            return Color(1f, 0f, 0f, a)
        }
        fun green(a: Float = 1f): Color {
            return Color(0f, 1f, 0f, a)
        }
        fun blue(a: Float = 1f): Color {
            return Color(0f, 0f, 1f, a)
        }
        fun yellow(a: Float = 1f): Color {
            return Color(1f, 1f, 0f, a)
        }
        fun purple(a: Float = 1f): Color {
            return Color(.5f, 0f, .5f, a)
        }
        fun pink(a: Float = 1f): Color {
            return Color(1f, 0f, 1f, a)
        }
        fun orange(a: Float = 1f): Color {
            return Color(1f, .5f, 0f, a)
        }
        fun gray(a: Float = 1f): Color {
            return Color(.5f, .5f, .5f, a)
        }
        fun white(a: Float = 1f): Color {
            return Color(1f, 1f, 1f, a)
        }
        fun black(a: Float = 1f): Color {
            return Color(0f, 0f, 0f, a)
        }
    }
}