package life.entities;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import life.FieldPanel;

public class ControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;;

    private static JButton createButton(final String label, final ActionListener listener) {
	final JButton button = new JButton(label);
	button.setAlignmentX(Component.CENTER_ALIGNMENT);
	button.addActionListener(listener);
	return button;
    }

    public ControlPanel(final FieldPanel fieldPanel) {
	final GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.ipadx = 10;
	gbc.ipady = 5;

	setLayout(new GridBagLayout());
	add(createButton("Start", e -> fieldPanel.start()), gbc);
	gbc.gridy++;
	add(createButton("Pause", e -> fieldPanel.pause()), gbc);
	gbc.gridy++;
	add(createButton("Randomize", e -> fieldPanel.randomiseField()), gbc);
    }
}
