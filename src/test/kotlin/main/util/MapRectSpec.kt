package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.*

object MapRectSpec: Spek({
  given("a MapRect from two points") {
    val rect: MapRect = MapRect(MapPoint(1.0, 3.0), MapPoint(2.0, 0.0))

    on("getting top left") {
      val topLeft: MapPoint = rect.topLeft
      it("should get the smallest x and y") {
        assertEquals(1.0, topLeft.x, 0.001)
        assertEquals(0.0, topLeft.y, 0.001)
      }
    }

    on("getting top right") {
      val bottomRight: MapPoint = rect.bottomRight
      it("should get the largest x and y") {
        assertEquals(2.0, bottomRight.x, 0.001)
        assertEquals(3.0, bottomRight.y, 0.001)
      }
    }
  }

  given("a MapRect from a MapPoint and Size") {
    val topleft: MapPoint = MapPoint(1.0, 1.0)
    val size: MapSize =  MapSize(1.0, 1.0)
    val rect: MapRect = MapRect(topleft, size)

    on("getting topleft") {
      val rectTopLeft: MapPoint = rect.topLeft
      it("should be equal to the topleft passed") {
        assertEquals(topleft, rectTopLeft)
      }
    }

    on("getting bottomRight") {
      val rectBottomRight: MapPoint = rect.bottomRight
      it ("should be equal to the top left plus the size") {
        assertEquals(MapPoint(topleft.x + size.width, topleft.y + size.height), rectBottomRight)
      }
    }
  }

  given("a MapRect from x, y width height") {
    val x: Double = 0.0
    val y: Double = 0.0
    val width: Double = 2.0
    val height: Double = 3.0
    val rect: MapRect = MapRect(x, y, width, height)

    on("getting the top left point") {
      val topLeft: MapPoint = rect.topLeft
      it("should match a MapPoint with x and y") {
        assertEquals(MapPoint(x, y), topLeft)
      }
    }

    on("getting the bottom right point") {
      val bottomRight: MapPoint = rect.bottomRight
      it("should match x plus width and y plus height") {
        assertEquals(MapPoint(x + width, y + height), bottomRight)
      }
    }
  }

  given("a rectangle") {
    val rect: MapRect = MapRect(1.0, 2.0, 2.0, 1.0)
    on ("checking if a point is contained in the rectangle") {
      val inside: MapPoint = MapPoint(2.5, 2.5)
      it("should be true if the point is inside") {
        assertTrue(rect.contains(inside))
        assertTrue(rect.contains(inside.x, inside.y))
      }

      val outside: MapPoint = MapPoint(0.0, 0.0)
      it("should be false if the point is outside") {
        assertFalse(rect.contains(outside))
        assertFalse(rect.contains(outside.x, outside.y))
      }

      val edge: MapPoint = MapPoint(1.0, 2.0)
      it("should be true if the point is on the edge") {
        assertTrue(rect.contains(edge))
        assertTrue(rect.contains(edge.x, edge.y))
      }
    }

    on("checking if another contains with another rectangle")  {
      val inside: MapRect = MapRect(1.5, 2.2, 1.0, 0.6)
      it("should return true if the rectangle is inside rect") {
        assertTrue(rect.contains(inside))
      }

      val containing: MapRect = MapRect(0.0, 0.0, 4.0, 4.0)
      it("should return false if the rectangle completely contains rect") {
        assertFalse(rect.contains(containing))
      }

      val outside: MapRect = MapRect(4.0, 1.0, 2.0, 2.0)
      it("should return false if the rectangle is outside rect") {
        assertFalse(rect.contains(outside))
      }

      val overlapping: MapRect = MapRect(1.0, 1.0, 1.0, 2.0)
      it ("should return false is the rectangle is overlapping rect") {
        assertFalse(rect.contains(overlapping))
      }

      val edge: MapRect = MapRect(2.0, 2.0, 1.0, 1.0)
      it("should return true if the rectangle is on the edge of rect") {
        assertTrue(rect.contains(edge))
      }
    }

    on("moving the rectange") {
      val movedRect: MapRect = rect.move(1.0, 1.0)
      it("should move both the topleft and bottomRight") {
        assertEquals(MapPoint(2.0, 3.0), movedRect.topLeft)
        assertEquals(MapPoint(4.0, 4.0), movedRect.bottomRight)
      }
    }

    on("checking equality") {
      val sameRect: MapRect = MapRect(rect.topLeft, rect.bottomRight)
      it("should be true for the same rectangle") {
        assertTrue(rect.equals(sameRect))
      }

      val differentRect = MapRect(rect.topLeft.translate(0.1, 0.0), rect.bottomRight)
      it("should be false for a differrent rectangle") {
        assertFalse(rect.equals(differentRect))
      }
    }
  }
})