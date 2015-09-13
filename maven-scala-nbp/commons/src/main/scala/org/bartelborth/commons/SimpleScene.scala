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

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Line2D
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

  val incrementalA = ContinuousSeries(square(200))
  val incrementalB = ContinuousSeries(square(200) compose translate(0.25 / 200))

  private val series = Series.define(List(
    incrementalA,
    incrementalB
  ))

  def animate(t: Double) = {
    this.t = t
    series.foreach(_.put(t, w))
    repaint()
  }

  val MARGIN = 10

  def w = getWidth - 2 * MARGIN
  def h = getHeight - 2 * MARGIN
  def cx = w / 2
  def cy = h / 2

  override def paintComponent(graphics: Graphics) = {
    val g = graphics.asInstanceOf[Graphics2D]
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

    super.paintComponent(g)

    g.translate(MARGIN, MARGIN)

    g.translate(50, 50)
    g.draw(new Line2D.Double(0, 0, tx(t) * 50 - 25, ty(t) * 50 - 25))
    g.drawOval(-25, -25, 50, 50)

    g.translate(50, -25)
    g.setColor(Color.red)
    incrementalA.paintComponent(45, g)
    g.translate(0, 5)
    g.setColor(Color.blue)
    incrementalB.paintComponent(45, g)
    g.translate(0, -5)
    g.translate(-50, +25)

    g.translate(-50, -50)

    g.drawString("t   = %2.3f".format(t), 12, h - 12)

    g.translate(-MARGIN, -MARGIN)
  }
}