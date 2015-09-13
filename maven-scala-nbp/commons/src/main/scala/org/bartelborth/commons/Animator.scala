/*
 * Copyright 2015 Isaias.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bartelborth.commons

class Animator {
  private val TARGET_FPS = 125
  private val FRAME_LENGTH = 1000000000 / TARGET_FPS

  private var scenes = List.empty[Scene]
  private var time = 0.0;
  private var step = 0.01;
  private var stats = Statistics(scala.collection.immutable.Queue.empty)

  updateStats(FRAME_LENGTH)

  case class Statistics(deltas: scala.collection.immutable.Queue[Long]) {
    val avg = deltas.sum.toDouble / Math.max(1, deltas.length).toDouble
    def next(delta: Long) = Statistics(deltas.enqueueFinite(delta, TARGET_FPS))
  }

  def avgFrameLength = stats.avg
  def avgFps = 1000000000d / avgFrameLength

  def update = {
    updateTime
    updateScenes(time)
  }

  private def updateTime = time = (time + step).frac
  private def updateScenes(t: Double) = scenes.foreach(_.animate(t))
  private def updateStats(frame: Long) = stats = stats.next(frame)

  private var running = false

  private var stopped = true
  private var paused = false

  def start = {
    if (stopped && !running) {
      stopped = false
      new Thread(new Runnable() {
        def run() = {
          //            running = true
          try {
            while (!stopped) {
              if (paused) Thread.sleep(333)
              else {
                val work = timingOf(update).delta
                val rest: Long = Math.max(0, FRAME_LENGTH - work)
                val millis = rest / 1000000
                val nanos = rest % 1000000
                val wait = timingOf(Thread.sleep(millis, nanos.toInt)).delta
                updateStats(work + wait)
              }
            }
          } finally {
            running = false
          }
        }
      }).start
    }
  }

  //                 def sleep(millis: Long) = {
  //          import scala.compat.Platform._
  //          var ts = currentTime
  //          var t1 = ts
  //          var t2 = ts + millis
  //
  //          var d = t2 - t1
  //          var interval: Long = Math.max(1, 3 * d / 4)
  //
  //          while (Math.abs(d) > 1) {
  //            Thread.sleep(interval)
  //            t1 = currentTime
  //            d = t2 - t1
  //            interval = Math.max(1, 3 * d / 4)
  //    }
  //  }
  def pause = { paused = !paused }
  def stop = { stopped = true }
  def addScene(s: Scene): Unit = {
    scenes :+= s
  }

  def velocity_=(v: Double): Unit = {
    step = Math.pow(v, 2) * 0.25
  }
}