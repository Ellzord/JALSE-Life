package life.entities;

import life.FieldPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

  private class ControlButton extends JButton {

    ControlButton(String label, ActionListener listener) {
      super(label);
      setAlignmentX(Component.CENTER_ALIGNMENT);
      addActionListener(listener);
    }
  }

  private class StartListener implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent event) {
      fieldPanel.start();
    }
  }

  private class PauseListener implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent event) {
      fieldPanel.pause();
    }
  }

  private class RandomizeListener implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent event) {
      fieldPanel.randomizeField();
    }
  }

  private final FieldPanel fieldPanel;

  public ControlPanel(final FieldPanel fieldPanel) {
    this.fieldPanel = fieldPanel;

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.ipadx = 10;
    gbc.ipady = 5;

    setLayout(new GridBagLayout());
    add(new ControlButton("Start", new StartListener()), gbc);
    gbc.gridy++;
    add(new ControlButton("Pause", new PauseListener()), gbc);
    gbc.gridy++;
    add(new ControlButton("Randomize", new RandomizeListener()), gbc);
  }
}
