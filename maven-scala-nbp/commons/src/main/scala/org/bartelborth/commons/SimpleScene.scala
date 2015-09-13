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
  private var t = 0.0d

  setDoubleBuffered(true)

  val tx: F = sin(1)
  val ty: F = cos(1)
  val sx: F = t => t * cx
  val sy: F = t => t * cy
  val pr: Double => Point2D.Double = r => new Point2D.Double(px(r), py(r))

  val px: F = t => sx(tx(t))
  val py: F = t => sy(ty(t))

  private val series = Series.define(List(
    ContinuousSeries(square(200)).deltaEventsWith((p, c) => p != c),
    ContinuousSeries(identity).deltaEventsWith((p, c) => p < c)
  ))

  def animate(t: Double) = {
    this.t = t
    series.foreach(_.put(t, w))
    repaint()
  }

  def w = getWidth
  def h = getHeight
  def cx = w / 2
  def cy = h / 2

  override def paintComponent(g: Graphics) = {
    super.paintComponent(g)

    series.zipWithIndex.foreach(t => t._1.paintComponent(t._2, g))

    g.drawString("t   = %2.3f".format(t), 12, h - 12 * 0 - 5)
  }
}