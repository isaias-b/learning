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

import java.awt.Graphics
import java.awt.geom.Point2D
import javax.swing.JPanel

class SimpleScene extends JPanel with Scene {
  private var point = new Point2D.Double(0, 0)
  private var t = 0.0d
  private var values: List[Double] = List.empty
  private val s1 = Series(t => (t * 20).frac.signum * 0.5 + 0.5)
  private val s2 = Series(t => t)
  private var nSeries = 0

  case class Series(f: Double => Double) {
    var values: List[Double] = List.empty
    val nr = nSeries
    nSeries += 1

    def put(t: Double) = {
      values :+= f(t)
      if (values.length > w) values = values.tail
    }
    def paintComponent(g: Graphics) = for (d <- values.zipWithIndex) {
      val x: Int = d._2
      val y: Int = 10 + nr * 50 + (d._1 * 50).toInt
      g.drawLine(x, y, x, y)
    }
  }
  setDoubleBuffered(true)

  val tx: Double => Double = Math.sin
  val ty: Double => Double = Math.cos
  val sx: Double => Double = t => t * cx
  val sy: Double => Double = t => t * cy
  val pr: Double => Point2D.Double = r => new Point2D.Double(px(r), py(r))

  val px: Double => Double = t => sx(tx(t))
  val py: Double => Double = t => sy(ty(t))

  val rad: Double => Double = t => t * 2 * Math.PI

  def animate(t: Double) = {
    this.t = t
    point = pr(rad(t))
    s1.put(t)
    s2.put(t)
    repaint()
  }

  def w = getWidth
  def h = getHeight
  def cx = w / 2
  def cy = h / 2

  override def paintComponent(g: Graphics) = {

    s1.paintComponent(g)
    s2.paintComponent(g)

    g.drawString("x   = %2.2f".format(point.x), 12, h - 12 * 1)
    g.drawString("y   = %2.2f".format(point.y), 12, h - 12 * 2)
    g.drawString("t   = %2.2f".format(t), 12, h - 12 * 3)
    g.drawString("rad = %2.2f".format(rad(t)), 12, h - 12 * 4)
    g.drawString("sx  = %2.2f".format(sx(tx(rad(t)))), 12, h - 12 * 5)
  }
}