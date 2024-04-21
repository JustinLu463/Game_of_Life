package life3singleton;

import java.awt.Color;
import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;


// Game of life main app using DuDraw
// Use mouse clicks to toggle cells
// Use spacebar to advance game

public class GameOfLifeApp implements DrawListener {
	// width and height in pixels
	private int width;
    private int height;
    private Draw window;
 
    // rows and cols for the game
    private int rows;
    private int cols;
    private Cell[][] grid; 
   
    
    public GameOfLifeApp(String title, int rows, int cols, int width, int height) {
        
    	// Save the instance variables
    	this.rows = rows;
		this.cols = cols;
		this.width = width;
		this.height = height;
		
		// Setup the grid
        this.grid = new Cell[rows][cols];
        setupGrid(grid);
         
        // Setup the DuDraw board
        window = new Draw(title);
        window.setCanvasSize(width, height);
        window.setXscale(0, width);
		window.setYscale(0, height);
       
		// Add the mouse/key listeners
        window.addListener(this);
        
        // Draw the initial board
	    update();
    }
   
    // the following initializes the grid of cells and their neighbors
    private void setupGrid(Cell[][] g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g[i][j] = new Cell();
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                    for (int colOffset = -1; colOffset <= 1; colOffset++) {
                        if (rowOffset == 0 && colOffset == 0) continue;
                        int newRow = (i + rowOffset + rows) % rows;
                        int newCol = (j + colOffset + cols) % cols;
                        g[i][j].addNeighbor(g[newRow][newCol]);
                    }
                }
            }
        }
    }
    
    // the following draws the grid lines on the board
    private void drawGrid() {
        
    	window.setPenColor(Color.black);
 		
        // the following calculates cell width and height
        int cellWidth = width / cols;
        int cellHeight = height / rows;
     
        // the following draws the horizontal lines 
        for (int i = 0; i <= rows; i++) {
        	window.line(0, i * cellHeight, this.width, i * cellHeight);
        }
        
        //the following draws vertical lines
        for (int i = 0; i <= cols; i++) {
        	window.line(i * cellWidth, 0, i * cellWidth, this.height);
        }
    }
     
    // the following fills in the cells on the board that are alive with red
    private void drawLives() {
        int cellWidth = width / cols;
        int cellHeight = height / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	// draw the alive cells as red
                if (grid[i][j].isAlive()) {
                    window.setPenColor(Color.RED);
                    window.filledRectangle((j * cellWidth) + (cellWidth / 2), (i * cellHeight) + (cellHeight / 2), cellWidth / 2, cellHeight / 2);
                }
            }
        }
    }
   

    // This method implements the rules of the Game of Life. For each cell,
    //   we simple find the number of neighbors and then bring the cell to life
    //   if appropriate.
    public void advance() {
        Cell[][] newGrid = new Cell[rows][cols];
        setupGrid(newGrid);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int livingNeighbors = grid[i][j].nbrAliveNeighbors();
                if (grid[i][j].isAlive()) {
                    newGrid[i][j].setState(livingNeighbors == 2 || livingNeighbors == 3 ? AliveState.create() : DeadState.create());
                } else {
                    newGrid[i][j].setState(livingNeighbors == 3 ? AliveState.create() : DeadState.create());
                }
            }
        }

        grid = newGrid;
        update();
    }

    // Below are the mouse/key listeners
    
	@Override
	public void keyPressed(int key) {
		// This is the advance button
		// Only advance for spacebar (ascii 32)
		if (key==32) {
			advance();
		}
	}

	@Override
	public void keyReleased(int key) {
		// Do nothing
	}

	@Override
	public void keyTyped(char key) {
		// Do nothing
	}

	@Override
	public void mouseClicked(double arg0, double arg1) {
		// Do nothing
	}

	@Override
	public void mouseDragged(double x, double y) {
		// Do nothing
	}

	// the following will bring a cell to life or kill it 
	@Override
    public void mousePressed(double x, double y) {
        int cellWidth = width / cols;
        int cellHeight = height / rows;

        int cellLocRow = (int) (y / cellHeight);
        int cellLocCol = (int) (x / cellWidth);

        grid[cellLocRow][cellLocCol].toggle();
        update();
    }

	@Override
	public void mouseReleased(double x, double y) {
		// Do nothing
	}

	@Override
	public void update() {
		// Redraw the entire board
		window.clear(Color.white);  // Clear in white
	 	drawGrid();
	 	drawLives();
	}
        
}