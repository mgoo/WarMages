package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.*

object MapPointSpec : Spek({
  given("there is a point") {
    val startingPoint = MapPoint(0.0, 0.0)

    on("getting the x value") {
      val x = startingPoint.x
      it("should match what was passed in the constructor") {
        assertEquals(0.0, x, 0.001)
      }
    }

    on("getting the y value") {
      val y = startingPoint.y
      it("should match what was passed in the constructor") {
        assertEquals(0.0, y, 0.001)
      }
    }

    on("translate the new MapPoint should have moved") {
      val north = startingPoint.translate(0.0, -1.0)
      it("should move north the correct amount") {
        assertEquals(0.0, north.x, 0.001)
        assertEquals(-1.0, north.y, 0.001)
      }

      val east = startingPoint.translate(1.0, 0.0)
      it("should move east the correct amount") {
        assertEquals(1.0, east.x, 0.001)
        assertEquals(0.0, east.y, 0.001)
      }

      val southWest = startingPoint.translate(-1.0, 1.0)
      it("should move diagonally the correct amount") {
        assertEquals(-1.0, southWest.x, 0.001)
        assertEquals(1.0, southWest.y, 0.001)
      }
    }

    on("calculating angle") {
      val northEast = startingPoint.translate(1.0, -1.0)
      val northEastAngle: Double = startingPoint.angleTo(northEast)
      it("should get angle north east to be pi/4") {
        assertEquals(Math.PI / 4, northEastAngle, 0.001)
      }

      val east = MapPoint(1.0, 0.0)
      val eastAngle: Double = startingPoint.angleTo(east);
      it("should get angle north to be 0") {
        assertEquals(0.0, eastAngle, 0.001);
      }
    }

    on("calculating distance") {
      val south = startingPoint.translate(0.0, 1.0)
      it("should be 1 from point 1 south") {
        assertEquals(1.0, startingPoint.distanceTo(south), 0.001)
      }

      val southWest = startingPoint.translate(-1.0, 1.0)
      it("should be root 2 from a diagonal point") {
        assertEquals(Math.sqrt(2.0), startingPoint.distanceTo(southWest), 0.001)
      }

      val otherPoint = MapPoint(23.0, 234.0)
      it("should be the same distance to a point than from the point") {
        assertEquals(
          startingPoint.distanceTo(otherPoint),
          otherPoint.distanceTo(startingPoint),
          0.001
        )
      }
    }

    on ("checking equality") {
      it("should be equal to itself") {
        assertTrue(startingPoint.equals(startingPoint))
      }

      val samePoint = MapPoint(0.0, 0.0)
      it("should be equals to a point at the same location") {
        assertTrue(startingPoint.equals(samePoint))
        assertTrue(samePoint.equals(startingPoint))
      }

      val differentPoint = MapPoint(0.0, 0.2)
      it("should not be equals to a point in a different location") {
        assertFalse(startingPoint.equals(differentPoint))
        assertFalse(differentPoint.equals(startingPoint))
      }
    }
  }
})