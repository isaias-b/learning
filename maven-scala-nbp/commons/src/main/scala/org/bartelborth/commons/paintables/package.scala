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

package org.bartelborth.commons

import java.awt.Graphics2D
import java.awt.Color._
import java.awt.Shape
import java.awt.geom.Line2D

package object paintables {
  type P = Graphics2D => Unit
  def at(x: Double, y: Double): P => P = p => g => {
    g.translate(x, y)
    p(g)
    g.translate(-x, -y)
  }
  def preserveColor(p: P): P = g => {
    val c = g.getColor
    p(g)
    g.setColor(c)
  }

  def portrait(w: Int, h: Int)(f: F): P = portrait(portraitize(f)(w, h))
  private[this] val pointZero = new Line2D.Double(0, 0, 0, 0)
  def draw(s: Shape): P = g => g.draw(s)
  def pointAt(x: Double, y: Double): P = at(x, y)(draw(pointZero))
  def pointAt(p: Point): P = pointAt(p.x, p.y)
  def portrait(points: List[Point]): P = g => {
    points.foreach(p => pointAt(p)(g))
  }
  def portraitize(f: F)(w: Double, h: Double) = {
    (0 to w.toInt).map(x => {
      val t = x / w
      val y = f(t) * h
      Point(x, y)
    }).toList
  }
  def whiteBoard(w: Int, h: Int): P => P = p => g => {
    val c = g.getColor
    g.setColor(white)
    g.fillRect(0, 0, w, h)
    g.setColor(c)
    p(g)
    g.setColor(black)
    g.drawRect(0, 0, w, h)
    g.setColor(c)
  }
  def row(sep: Int, ps: P*): P = g => {
    for (p <- ps) {
      p(g)
      g.translate(sep, 0)
    }
    g.translate(-sep * ps.length, 0)
  }

}
