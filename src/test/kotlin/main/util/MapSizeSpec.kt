package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.*

object MapSizeSpec: Spek ({
  given("there is a MapSize") {
    val size = MapSize(1.0, 1.0)

    on("getting width") {
      val width = size.width
      it("should match what was passes to the constructor") {
        assertEquals(1.0, width, 0.001)
      }
    }

    on("getting height") {
      val height = size.height
      it ("should match what was passed to the contructor") {
        assertEquals(1.0, height, 0.001)
      }
    }

    on("equality") {
      val sameSize = MapSize(1.0, 1.0)
      it("should be equal to a size with the same width and height") {
        assertTrue(size.equals(sameSize))
      }

      val differentWidth = MapSize(2.0, 1.0)
      it ("should be different from a size with a different width") {
        assertFalse(size.equals(differentWidth))
      }

      val differentHeight = MapSize(1.0, 2.0)
      it("shoud be different from a size with different height") {
        assertFalse(size.equals(differentHeight))
      }
    }
  }
})