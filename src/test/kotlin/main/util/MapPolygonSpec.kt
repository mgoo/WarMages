package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object MapPolygonSpec: Spek({
  given("a dimond polygon") {
    val polyon = MapPolygon(
        MapPoint(-1.0, 0.0),
        MapPoint(0.0, 1.0),
        MapPoint(1.0, 0.0),
        MapPoint(0.0, -1.0)
    )

    on("checking contains point") {
      val inside = polyon.contains(MapPoint(0.0, 0.0))
      val outside = polyon.contains(MapPoint(0.75, 0.75))
      val edge = polyon.contains(MapPoint(-1.0, 0.0))

      it("should be true when the point is inside") {
        assertTrue { inside }
      }

      it("should be false when the point is outside") {
        assertFalse { outside }
      }

      it("should be true when the point is on the edge") {
        assertTrue { edge }
      }
    }

    on("checking contains MapRect") {
      val inside = MapRect(-0.1, -0.1, 0.1, 0.1)
      val contains = MapRect(-2.0, -2.0, 4.0, 4.0)
      val outside = MapRect(3.0, 3.0, 1.0, 1.0)
      val overlap1 = MapRect(-1.0, -1.0, 1.0, 1.0)
      val overlap2 = MapRect(-0.1, 0.5, 0.2, 1.0)
      val overlap3 = MapRect(-0.8, -0.8, 0.6, 0.6)

      it("should be true if the rect is inside") {
        assertTrue { polyon.contains(inside) }
      }

      it("should be true if the rect contains the polygon") {
        assertTrue { polyon.contains(contains) }
      }

      it("should be false if the rect is completely outside the polygon") {
        assertFalse { polyon.contains(outside) }
      }

      it ("should be true if the rect overlaps the polygin in any way") {
        assertTrue { polyon.contains(overlap1) }
        assertTrue { polyon.contains(overlap2) }
        assertTrue { polyon.contains(overlap3) }
      }
    }
  }
})