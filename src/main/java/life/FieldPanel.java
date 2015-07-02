package life;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.Timer;

import jalse.DefaultJALSE;
import jalse.JALSE;
import life.actions.Update;
import life.entities.Cell;
import life.entities.Cell.DeadCell;
import life.entities.Cell.LiveCell;
import life.entities.Field;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel implements ActionListener {

    private static final int DEFAULT_SPEED = 1000 / 20;
    private static final double DEFAULT_THRESHOLD = 0.15;

    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;

    private static final int NUM_ROWS = 50;
    private static final int NUM_COLS = 70;

    private static void drawElement(final Graphics g, final Cell cell) {
	final Point position = cell.getPosition();
	final int size = Life.SIZE;

	g.setColor(cell.getColour());
	g.fillRect(position.x, position.y, size, size);
    }

    private final JALSE jalse;
    private final Random random;

    private final Timer timer;

    public FieldPanel() {
	random = new Random();

	// Manually ticked JALSE.
	jalse = new DefaultJALSE.Builder().setManualEngine().build();

	// Create Field and Cells.
	createEntities();

	// Size to Cell size * NUM_ROWS by Cell size * NUM_COLS.
	setPreferredSize(getField().getSize());

	// Create timer for ticking and rendering (20 FPS).
	timer = new Timer(DEFAULT_SPEED, this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
	// Tick for JALSE ManualEngine.
	jalse.resume();
	// Request repaint.
	repaint();
    }

    /**
     * Create Cell entities that make up the field and setup each Cell with neighbours and an initial
     * state.
     */
    private void createEntities() {
	final Field field = jalse.newEntity(Field.ID, Field.class);
	final Cell[][] cells = new Cell[NUM_ROWS][NUM_COLS];
	field.setSize(new Dimension(WIDTH, HEIGHT));

	for (int row = 0; row < NUM_ROWS; row++) {
	    for (int col = 0; col < NUM_COLS; col++) {
		final Cell cell = field.newEntity(UUID.randomUUID(), Cell.class);
		final Point position = new Point(Life.SIZE * col, Life.SIZE * row);
		cell.setRow(row);
		cell.setCol(col);
		cell.setPosition(position);
		cells[row][col] = cell;
	    }
	}
	seedField(DEFAULT_THRESHOLD);
	field.streamCells().forEach(c -> setNeighbors(c, cells));
	field.scheduleForActor(new Update(), 0, DEFAULT_SPEED, TimeUnit.MILLISECONDS);
    }

    private Field getField() {
	return jalse.getEntityAsType(Field.ID, Field.class);
    }

    @Override
    protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	// Draw cells.
	getField().streamCells().forEach(c -> drawElement(g, c));
	// Sync (Linux fix).
	Toolkit.getDefaultToolkit().sync();
    }

    /** Pause ticking. **/
    public void pause() {
	timer.stop();
    }

    /** Pauses, randomises Field Cells and repaint panel. */
    public void randomiseField() {
	timer.stop();
	seedField(DEFAULT_THRESHOLD);
	repaint();
    }

    /** Mark Cell Entities as either LiveCell or DeadCell using random and a threshold value. */
    private void seedField(final double threshold) {
	getField().streamCells().forEach(cell -> {
	    cell.unmarkAsAllTypes();
	    if (random.nextFloat() < threshold) {
		cell.markAsType(LiveCell.class);
		cell.setNextGenStatus(LiveCell.class);
		cell.setColour(Life.LIVE_COLOUR);
	    } else {
		cell.markAsType(DeadCell.class);
		cell.setNextGenStatus(DeadCell.class);
		cell.setColour(Life.DEAD_COLOUR);
	    }
	});
    }

    /** Set neighbors for each cell based on location. */
    private void setNeighbors(final Cell cell, final Cell[][] cells) {
	final int row = cell.getRow();
	final int col = cell.getCol();
	final Set<Cell> neighbors = new LinkedHashSet<>();

	if (row > 0) {
	    neighbors.add(cells[row - 1][col]);
	    if (col > 0) {
		neighbors.add(cells[row - 1][col - 1]);
	    }
	    if (col < NUM_COLS - 1) {
		neighbors.add(cells[row - 1][col + 1]);
	    }
	}
	if (row < NUM_ROWS - 1) {
	    neighbors.add(cells[row + 1][col]);
	    if (col > 0) {
		neighbors.add(cells[row + 1][col - 1]);
	    }
	    if (col < NUM_COLS - 1) {
		neighbors.add(cells[row + 1][col + 1]);
	    }
	}
	if (col > 0) {
	    neighbors.add(cells[row][col - 1]);
	}
	if (col < NUM_COLS - 1) {
	    neighbors.add(cells[row][col + 1]);
	}

	cell.setNeighbors(neighbors);
    }

    /** Start ticking. **/
    public void start() {
	timer.start();
    }
}
