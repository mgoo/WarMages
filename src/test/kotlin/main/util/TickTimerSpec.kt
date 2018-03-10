package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object TickTimerSpec: Spek({
  given("I have a ticktimer for 10 ticks") {
    val tickTimer = TickTimer(10)
    tickTimer.restart()

    on("get Progress") {
      val progress = tickTimer.progress
      it("should be 0") {
        assertEquals(0.0, progress, 0.001)
      }
    }

    on("ticking") {
      for (ticks in 1..9) {
        tickTimer.tick(0)
        val progress = tickTimer.progress
        it("progress should increse") {
          assertEquals(ticks.toDouble() / 10.0, progress, 0.001)
        }
      }
    }

    on("finishing the timer") {
      tickTimer.tick(0)
      val progress = tickTimer.progress
      it("should have a progess of 1") {
        assertEquals(1.0, progress, 0.001)
      }
    }

    on("restart") {
      tickTimer.restart()
      val progress = tickTimer.progress
      it("should have progress of 0") {
        assertEquals(0.0, progress, 0.001)
      }
    }
  }
})