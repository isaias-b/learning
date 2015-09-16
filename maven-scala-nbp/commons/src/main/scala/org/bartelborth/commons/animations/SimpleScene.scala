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

package org.bartelborth.commons.animations

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.geom.Line2D
import java.awt.geom.Point2D
import java.awt.image.BufferedImage
import javax.swing.JComponent

import org.bartelborth.commons._
import org.bartelborth.commons.paintables._

class SimpleScene extends JComponent with Scene {
  private var t = 0.0d
  import Color._
  object resizer extends ComponentAdapter {
    private[this] def newImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR)
    def createImage =
      if (w <= 0 || h <= 0) None
      else Some(newImage)
    override def componentResized(e: ComponentEvent) = {
      buffer = createImage.map(b => {
        val g = b.createGraphics
        g.setColor(white)
        g.fillRect(0, 0, w + 2 * MARGIN - 1, h + 2 * MARGIN - 1)
        g.dispose
        b
      })
    }
  }
  private var buffer = resizer.createImage

  setDoubleBuffered(true)
  addComponentListener(resizer)

  val tx: F = sin(1)
  val ty: F = cos(1)
  val sx: F = t => t * cx
  val sy: F = t => t * cy
  val pr: Double => Point2D.Double = r => new Point2D.Double(px(r), py(r))

  val px: F = t => sx(tx(t))
  val py: F = t => sy(ty(t))

  val incrementalA = ContinuousSeries(square(200))
  val incrementalB = ContinuousSeries(square(200) compose translate(0.25 / 200))

  def animate(t: Double) = {
    this.t = t
    incrementalA.put(t, w - 125)
    incrementalB.put(t, w - 125)
    repaint()
  }

  val MARGIN = 10

  def w = getWidth - 2 * MARGIN
  def h = getHeight - 2 * MARGIN
  def cx = w / 2
  def cy = h / 2

  def setRenderingHints(g: Graphics2D) = {
    import RenderingHints._
    g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    g.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON)
    g.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY)
  }

  override def paintComponent(graphics: Graphics) = {
    super.paintComponent(graphics)
    buffer.foreach(b => {
      val g = b.createGraphics
      setRenderingHints(g)

      g.setColor(new Color(255, 255, 255, 64))
      g.fillRect(0, 0, w - 1, h - 1)

      g.setColor(new Color(0, 0, 0, 255))

      g.translate(50, 50)
      g.draw(new Line2D.Double(0, 0, tx(t) * 50 - 25, ty(t) * 50 - 25))
      g.drawOval(-25, -25, 50, 50)

      g.translate(50, -25)
      g.setColor(red)
      incrementalA.paintComponent(45, g)
      g.translate(0, 5)
      g.setColor(blue)
      incrementalB.paintComponent(45, g)
      g.translate(0, -5)
      g.translate(-50, +25)

      g.translate(-50, -50)

      row1(g)
      row2(g)
      row3(g)

      g.drawString("t   = %2.3f".format(t), 12, h - 12)

      g.dispose()

      graphics.drawImage(b, MARGIN, MARGIN, w, h, null)
    })
  }
  val row1 = at(0, 100)(myrow(identity, sin(1), cos(1)))
  val row2 = at(0, 140)(mywhiteBoard(600, 40)(incrementalA.f))
  val row3 = at(0, 190)(mywhiteBoard(600, 40)(incrementalB.f))
  def myrow(fs: F*): P = {
    val (w, h) = (100, 30)
    val ps = fs.map(f => mywhiteBoard(w, h)(f))
    row(10 + w, ps: _*)
  }
  def mywhiteBoard(w: Int, h: Int)(f: F): P = g => {
    whiteBoard(w, h)(at(2, 2)(portrait(w - 4, h - 4)(f)))(g)
    scanlineV(w, h)(t)(g)
  }

  def scanlineV(w: Int, h: Int): Double => P = t => g => {
    val x = (t * w).toInt
    g.drawLine(x, 0, x, h)
  }

}