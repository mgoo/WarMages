package main.util

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.lang.IllegalStateException
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

object LooperSpec: Spek({
  describe("I have a looper") {
    var looper = Looper()
    var runnableMock = mock(Runnable::class.java)

    beforeEachTest {
      looper = Looper()
      runnableMock = mock(Runnable::class.java)
    }

    context("looper running") {
      beforeEachTest {
        looper.startWithSchedule(runnableMock, 100)
      }

      afterEachTest {
        looper.stop()
      }

      it("should call the runnable when its looped") {
        Thread.sleep(100)
        verify(runnableMock, atLeastOnce()).run()
      }

      it("should only be able to start once") {
        assertTrue(true)
        assertFailsWith<IllegalStateException> { looper.start { /* do nothing */ } }
        assertFailsWith<IllegalStateException> { looper.startWithSchedule({ /* do nothing */ }, 100) }
      }

      it("should stick to schedule") {
        val invocationsSoFar: Int = Mockito.mockingDetails(runnableMock).invocations.size
        Thread.sleep(100)
        verify(runnableMock, times(invocationsSoFar + 1)).run()
      }

      on("pausing the looper") {
        looper.setPaused(true)
        val invocationsSoFar= Mockito.mockingDetails(runnableMock).invocations.size

        it("should stop calling the runnable") {
          Thread.sleep(200)
          verify(runnableMock, atMost(invocationsSoFar + 1)).run()
          verify(runnableMock, atLeast(invocationsSoFar)).run()
        }
      }

      on("pausing then unpausing") {
        looper.setPaused(true)
        val invocationsSoFar= Mockito.mockingDetails(runnableMock).invocations.size
        looper.setPaused(false)

        it("should call the runnable") {
          Thread.sleep(100)
          verify(runnableMock, atLeast(invocationsSoFar + 1)).run()
        }
      }
    }

    context("loop stopped") {
      beforeEachTest {
        looper.start(runnableMock)
      }

      on("stoping the looper") {
        looper.stop()
        val invocationsWhenStoped: Int = Mockito.mockingDetails(runnableMock).invocations.size

        it("should stop calling the runnable") {
          Thread.sleep(200)
          verify(runnableMock, atMost(invocationsWhenStoped + 1)).run()
          verify(runnableMock, atLeast(invocationsWhenStoped)).run()
        }
      }
    }
  }
})