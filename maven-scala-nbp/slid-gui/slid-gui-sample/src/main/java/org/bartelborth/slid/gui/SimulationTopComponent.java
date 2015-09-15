/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bartelborth.slid.gui;

import org.bartelborth.commons.animations.Animator;
import org.bartelborth.commons.animations.Scene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.bartelborth.slid.gui//Simulation//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SimulationTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.bartelborth.slid.gui.SimulationTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SimulationAction",
        preferredID = "SimulationTopComponent"
)
@Messages({
  "CTL_SimulationAction=Simulation",
  "CTL_SimulationTopComponent=Simulation Window",
  "HINT_SimulationTopComponent=This is a Simulation window"
})
public final class SimulationTopComponent extends TopComponent implements Scene {

  private Animator animator = new Animator();

  public SimulationTopComponent() {
    initComponents();
    setName(Bundle.CTL_SimulationTopComponent());
    setToolTipText(Bundle.HINT_SimulationTopComponent());
    animator.addScene(this);
    animator.addScene(scene);
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    buttonPanel = new javax.swing.JPanel();
    start = new javax.swing.JButton();
    stop = new javax.swing.JButton();
    pause = new javax.swing.JButton();
    velocity = new javax.swing.JSlider();
    scene = new org.bartelborth.commons.animations.SimpleScene();
    position = new javax.swing.JProgressBar();

    setLayout(new java.awt.BorderLayout());

    org.openide.awt.Mnemonics.setLocalizedText(start, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.start.text")); // NOI18N
    start.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        startActionPerformed(evt);
      }
    });
    buttonPanel.add(start);

    org.openide.awt.Mnemonics.setLocalizedText(stop, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.stop.text")); // NOI18N
    stop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopActionPerformed(evt);
      }
    });
    buttonPanel.add(stop);

    org.openide.awt.Mnemonics.setLocalizedText(pause, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.pause.text")); // NOI18N
    pause.setToolTipText(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.pause.toolTipText")); // NOI18N
    pause.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        pauseActionPerformed(evt);
      }
    });
    buttonPanel.add(pause);

    velocity.setMaximum(1000);
    velocity.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        velocityStateChanged(evt);
      }
    });
    buttonPanel.add(velocity);

    add(buttonPanel, java.awt.BorderLayout.NORTH);

    javax.swing.GroupLayout sceneLayout = new javax.swing.GroupLayout(scene);
    scene.setLayout(sceneLayout);
    sceneLayout.setHorizontalGroup(
      sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    sceneLayout.setVerticalGroup(
      sceneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 250, Short.MAX_VALUE)
    );

    add(scene, java.awt.BorderLayout.CENTER);
    scene.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.scene.AccessibleContext.accessibleName")); // NOI18N

    position.setMaximum(10000);
    add(position, java.awt.BorderLayout.SOUTH);
  }// </editor-fold>//GEN-END:initComponents

  private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
    animator.start();
  }//GEN-LAST:event_startActionPerformed

  private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
    animator.stop();
  }//GEN-LAST:event_stopActionPerformed

  private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
    animator.pause();
  }//GEN-LAST:event_pauseActionPerformed

  private void velocityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_velocityStateChanged
    animator.velocity_$eq(velocity.getValue() / 1000d);
  }//GEN-LAST:event_velocityStateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel buttonPanel;
  private javax.swing.JButton pause;
  private javax.swing.JProgressBar position;
  private org.bartelborth.commons.animations.SimpleScene scene;
  private javax.swing.JButton start;
  private javax.swing.JButton stop;
  private javax.swing.JSlider velocity;
  // End of variables declaration//GEN-END:variables

  void writeProperties(java.util.Properties p) {
  }

  void readProperties(java.util.Properties p) {
  }

  @Override
  public void animate(double t) {
    position.setValue((int) (t * 10000));
  }
}
