package life;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import life.entities.ControlPanel;

public class Life {

    public static final Color DEAD_COLOUR = Color.GREEN;

    public static final Color LIVE_COLOUR = Color.BLUE;

    public static final int SIZE = 10;

    public static void main(final String[] args) {
	final JFrame frame = new JFrame("JALSE-Life");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	final FieldPanel fieldPanel = new FieldPanel();
	frame.add(fieldPanel, BorderLayout.CENTER);
	frame.add(new ControlPanel(fieldPanel), BorderLayout.EAST);
	frame.pack();
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}
