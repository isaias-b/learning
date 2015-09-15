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

package org.bartelborth.commons.paintables

import java.awt.Graphics
import org.bartelborth.commons._

trait Series[T] {
  val f: Double => T
  var values: List[T] = List.empty

  def put(t: Double, w: Int) = {
    val ft = f(t)
    values :+= ft
    while (values.length > w && w > 0) values = values.tail
  }

  def paintComponent(h: Double, g: Graphics): Unit

  def current = values.lastOption
  def previous = values.dropRight(1).lastOption
  def previousDiffersWith(op: (T, T) => Boolean) =
    for (c <- current; p <- previous)
      yield op(c, p)
  def deltaEventsWith(op: (T, T) => Boolean) = List(this, EventSeries(t => previousDiffersWith(op).getOrElse(false)))
}

object Series {
  def define(ll: List[List[Series[_]]]) = ll.flatten
}

case class ContinuousSeries(f: F) extends Series[Double] {
  def paintComponent(h: Double, g: Graphics) =
    for ((y, x) <- values.zipWithIndex) {
      val sy: Int = (y * h).toInt
      g.drawLine(x, sy, x, sy)
    }
}

case class EventSeries(f: Double => Boolean) extends Series[Boolean] {
  def paintComponent(h: Double, g: Graphics) =
    for ((y, x) <- values.zipWithIndex.filter(_._1)) {
      g.drawLine(x, 0, x, h.toInt)
    }

}