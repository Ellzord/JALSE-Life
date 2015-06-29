package life;

import javax.swing.*;
import java.awt.*;

public class Life {
  public static void main(final String[] args) {
    final JFrame frame = new JFrame("JALSE-Life");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    final FieldPanel fieldPanel = new FieldPanel();
    frame.add(fieldPanel, BorderLayout.CENTER);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
