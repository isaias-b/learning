/*
 * Copyright Error: on line 4, column 29 in Templates/Licenses/license-apache20.txt
 The string doesn't match the expected date/time format. The string to parse was: "30.08.2015". The expected format was: "dd-MMM-yyyy". Isaias.
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

package org.bartelborth

package object commons {
  type F = Double => Double
  val identity: F = t => t
  val mathSin: F = Math.sin
  val mathCos: F = Math.cos
  val scale0p1: F = t => (t + 1) / 2d

  def square(l: Double): F = t => if (((t * l).frac - 0.5).signum > 0) 1 else 0
  def sawtooth(l: Double): F = t => (t * l).frac
  def triangle(l: Double): F = t => Math.abs(sawtooth(l)(t) * 2 - 1)
  def rad(l: Double): F = t => t * 2 * Math.PI * l
  def sin(l: Double): F = scale0p1 compose mathSin compose rad(l)
  def cos(l: Double): F = scale0p1 compose mathCos compose rad(l)

  implicit class RichDouble(d: Double) {
    def frac: Double = d - d.toInt
  }
  implicit class RichBoolean(b: Boolean) {
    def toInt: Int = if (b) 1 else 0
  }

  case class Timing[R](result: R, delta: Long)
  def timingOf[R](block: => R): Timing[R] = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    Timing(result, t1 - t0)
  }

  import scala.collection.immutable.Queue
  class FiniteQueue[A](q: Queue[A]) {
    def enqueueFinite[B >: A](elem: B, maxSize: Int): Queue[B] = {
      var ret = q.enqueue(elem)
      while (ret.size > maxSize) { ret = ret.dequeue._2 }
      //      println(ret)
      ret
    }
  }
  implicit def queue2finitequeue[A](q: Queue[A]) = new FiniteQueue[A](q)
  implicit def series2list[T](s: Series[T]) = List(s)

  def lift2edt(f: => Unit): Unit = {
    import javax.swing.SwingUtilities
    SwingUtilities.invokeLater(new Runnable() {
      def run(): Unit = f
    })
  }
}
