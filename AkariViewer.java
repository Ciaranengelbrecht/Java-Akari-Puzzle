/**
 * AkariViewer represents an interface for a player of Akari.
 *
 * @author Lyndon While
 * @version 2021
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.font.FontRenderContext;

public class AkariViewer implements MouseListener
{    
    private Akari puzzle;    // the internal representation of the puzzle
    private SimpleCanvas sc; // the display window  
    private static int cellsize = 60;
    private final static Color white = new Color(230,230,230);
    private final static Color BLK = new Color(30, 30, 30);
    private final Color lit = new Color(255, 255, 90);
    private final Color bright = new Color(255, 255, 0);
    private final Color bottom = new Color(128, 129, 255);
    private final Color text = new Color(190, 0, 0);
    private final Color glow = new Color(255,170,0);
    private final Color but = new Color(0,240,240);

    int t;
    

    
    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        // TODO 10
        this.puzzle = puzzle;
        int size = puzzle.getSize();
        if (size > 10)cellsize = 40; if (size > 20) cellsize = 25;
        sc = new SimpleCanvas("AKARI", (size * cellsize), (size * cellsize)+150, bottom); 
        sc.addMouseListener(this);
        if (size <10) sc.setFont(new Font("ComicSans", Font.PLAIN , cellsize / 10 * 4));
        if (size > 10) sc.setFont(new Font("ComicSans", Font.PLAIN , cellsize / 10 * 6));
        if (size >20) sc.setFont(new Font("ComicSans", Font.PLAIN , cellsize / 10 * 7));
        displayPuzzle();
    }
    /**
     * Draws a string with the centre of this string
     * at the input coordinates
     */
    public void drawCenteredString(String text, int x, int y, Color Colour)
    {
        FontRenderContext frc = new FontRenderContext(null,true,true);
        Rectangle2D size = sc.getFont().getStringBounds(text, frc);
        int fontX = x - (int)size.getWidth() / 2 + (cellsize/2)+1;
        int fontY = y + (int)size.getHeight() / 3 + (cellsize/2)+1;
        sc.drawString(text,fontX,fontY,Colour);
    }
    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n / 10 + "-e" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AkariViewer()
    {
        this(77);
    }
    
    /**
     * Returns the internal state of the puzzle.
     */
    public Akari getPuzzle()
    {
        // TODO 9a
        return puzzle;
    }
    
    /**
     * Returns the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 9b
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for a suggested layout. 
     */
    private void displayPuzzle()
    {
        // TODO 11
        int size = puzzle.getSize();
        Color col;
        
        //drawing each cell and its corresponding colour
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
        if (!Space.isMutable(puzzle.getBoard(j,i))) col = BLK;
            else if(puzzle.getBoard(j,i) == Space.BULB && puzzle.isLegal(j,i))
            col = glow; 
            else if(puzzle.canSeeBulb(j,i)) col = lit;
            else col = white;
        sc.drawRectangle(i * cellsize, j * cellsize, (i + 1) * cellsize, (j + 1) * cellsize, col);
        //value of cell drawn
            if (puzzle.getBoard(j,i) == Space.ZERO)
        {drawCenteredString("0", cellsize*i, cellsize*j, text);}
          if (puzzle.getBoard(j,i) == Space.ONE)
        {drawCenteredString("1", cellsize*i, cellsize*j, text);}
          if (puzzle.getBoard(j,i) == Space.TWO)
        {drawCenteredString("2", cellsize*i, cellsize*j, text);}
          if (puzzle.getBoard(j,i) == Space.THREE)
        {drawCenteredString("3", cellsize*i, cellsize*j, text);}
          if (puzzle.getBoard(j,i) == Space.FOUR)
        {drawCenteredString("4", cellsize*i, cellsize*j, text);}
        if(puzzle.getBoard(j,i) == Space.BULB && puzzle.isLegal(j,i))
        {sc.drawRectangle(i * cellsize+5, j * cellsize+5, (i + 1) * cellsize-3, (j + 1) * cellsize-3, bright);
        drawCenteredString("\u2600", cellsize*i, cellsize*j, text);}
        if(puzzle.getBoard(j,i) == Space.BULB && puzzle.isLegal(j,i) && puzzle.canSeeBulb(j,i))
        {sc.drawRectangle(i * cellsize+5, j * cellsize+5, (i + 1) * cellsize-3, (j + 1) * cellsize-3, bright);
        drawCenteredString("\u2600", cellsize*i, cellsize*j, Color.red);}
        }
        
            
         // Grid and shadow
        for (int i = 0; i <= size; i++)
            sc.drawLine(i * cellsize, 0, i * cellsize, size * cellsize, BLK);
        for (int j = 0; j <= size; j++)
            sc.drawLine(0, j * cellsize, size * cellsize, j * cellsize,  BLK);
        for (int i = 0; i <= size; i++)
            sc.drawLine(i * cellsize+1, 0, i * cellsize+1, size * cellsize+1, BLK);
        for (int j = 0; j <= size; j++)
            sc.drawLine(0, j * cellsize+1, size * cellsize+1, j * cellsize+1,  BLK);
        //buttons
        sc.drawRectangle(53, (size * cellsize)+33 , 203, (size * cellsize)+63, BLK);
        sc.drawRectangle(50, (size * cellsize)+30 , 200, (size * cellsize)+60, but);
        sc.drawString("Clear Board", 55, (size * cellsize)+55, BLK);
        sc.drawRectangle(53, (size * cellsize)+73 , 203, (size * cellsize)+98, BLK);
        sc.drawRectangle(50, (size * cellsize)+70 , 200, (size * cellsize)+95, but);
        sc.drawString("Done", 55, (size * cellsize)+90, BLK);
        sc.drawRectangle(10, (size * cellsize)+30 , 40, (size * cellsize)+60, but);
        sc.drawString("?", 20, (size * cellsize)+55, BLK);
    }
    
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 12
        int size = puzzle.getSize();
        if (!puzzle.isLegal(r,c)) {
            return;}
        puzzle.leftClick(r,c);
        
        //extension moves counter
        if (Space.isMutable(puzzle.getBoard(r,c)))
        t++;
        String click = Integer.toString(t);
        sc.drawRectangle(228, (size * cellsize)+33 , 303, (size * cellsize)+63, BLK);
        sc.drawRectangle(225, (size * cellsize)+30 , 300, (size * cellsize)+60, but);
        sc.drawString(click, 230, (size * cellsize)+55, BLK);
        sc.drawString("Moves:", 225, (size * cellsize)+25, BLK);
    
    }

    //buttons
        public void clear(int r, int c)
    {
        if (r >50 && r < 200 && c> (puzzle.getSize() * cellsize)+30  && 
        c < (puzzle.getSize() * cellsize)+60 ) puzzle.clear();
    }
    
        public void solution(int r, int c)
    {
        if (r >50 && r < 200 && c> (puzzle.getSize() * cellsize)+70  && 
        c < (puzzle.getSize() * cellsize)+95 ) {
        sc.drawRectangle(53, (puzzle.getSize() * cellsize)+108 , 383, (puzzle.getSize() * cellsize)+133, BLK);
        sc.drawRectangle(50, (puzzle.getSize() * cellsize)+105 , 380, (puzzle.getSize() * cellsize)+130, text);
        sc.drawString(puzzle.isSolution(), 55, (puzzle.getSize() * cellsize)+125, BLK);
        String click = Integer.toString(t);
        
        //extended completion
        if (puzzle.isSolution()=="\u2713\u2713\u2713"){
        sc.drawDisc(200, 200, 100, BLK);
        sc.drawString("WELL DONE!!!", 120, 220, lit);
        sc.drawString("It took you "+ click + " moves!", 115, (puzzle.getSize() * cellsize)+130, BLK);}
    }
    }
    
    // extensions
    private final long start = System.currentTimeMillis();
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000;
    }
    /**
     //Displays the time elapsed count
     */
    public void run()
    {
        int size = puzzle.getSize();
        String time = Double.toString(elapsedTime());
        sc.drawRectangle(318, (size * cellsize)+33 , 413, (size * cellsize)+63, BLK);
        sc.drawRectangle(315, (size * cellsize)+30 , 410, (size * cellsize)+60, but);
        sc.drawString(time, 320, (size * cellsize)+55, BLK);
        sc.drawString("Time:", 320, (size * cellsize)+25, BLK);
    }
    /**
     //Marks where a bulb definity cannot be placed on the board 
     */
    public void hint(int i, int j)
    {
        int size = puzzle.getSize();
        if (i >10 && i < 40 && j> (puzzle.getSize() * cellsize)+30  && 
        j < (puzzle.getSize() * cellsize)+60){
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++){
        int rp = (r + 1);
        int rm = (r - 1);
        int cp = (c + 1);
        int cm = (c - 1);
        if (puzzle.isLegal(c,r) && puzzle.getBoard(c,r) == Space.ZERO){
        if (puzzle.isLegal(cm,r) && puzzle.getBoard(cm,r) == Space.EMPTY) 
        drawCenteredString("X", cellsize * r, cellsize * cm, text);
        if (puzzle.isLegal(cp,r) && puzzle.getBoard(cp,r) == Space.EMPTY) 
        drawCenteredString("X", cellsize * r, cellsize * cp, text);
        if (puzzle.isLegal(c,rm) && puzzle.getBoard(c,rm) == Space.EMPTY)  
        drawCenteredString("X", cellsize * rm,cellsize * c, text);
        if (puzzle.isLegal(c,rp) && puzzle.getBoard(c,rp) == Space.EMPTY)  
        drawCenteredString("X",cellsize * rp, cellsize * c, text);}
        }}
    }
    
    
    // TODO 13
    public void mousePressed (MouseEvent e) {
    leftClick(e.getY()/cellsize, e.getX()/cellsize);
    clear(e.getX(), e.getY());
    displayPuzzle();
    hint(e.getX(), e.getY());
    run();
    solution(e.getX(), e.getY());
    }
    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {run();}
    public void mouseExited  (MouseEvent e) {run();}
    
    
}
