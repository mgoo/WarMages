package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

object EventSpec: Spek({
  given("I have an event") {
    var event: Event<Int> = Event()

    beforeEachTest {
      event = Event()
    }

    on("broadcasting a value to the listener") {
      event.registerListener({ value -> assertEquals(10, value)})
      it("should pass the right value to the listeners") {
        event.broadcast(10) // Calls the assertion
      }
    }

    on("broadcasting to listeners multiple listeners") {
      var count: Int = 0
      event.registerListener({count++})
      event.registerListener({count++})
      event.broadcast(0)

      it("should trigger every listener") {
        assertEquals(2, count)
      }
    }

    on("broadcasting after listener has being removed") {
      val remover = event.registerListener({assertFalse(true)})
      remover.run()
      it("should not trigger removed event") {
        event.broadcast(0)
      }
    }
  }
})