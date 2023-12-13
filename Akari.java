/**
 * Akari represents a single puzzle of the game Akari.
 *
 * @author Lyndon While 
 * @version 2021
 */
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Akari
{
    private String filename; // the name of the puzzle file
    private int size;        // the board is size x size
    private Space[][] board; // the board is a square grid of spaces of various types

    /**
     * Constructor for objects of class Akari. 
     * Creates and initialises the fields with the contents of filename. 
     * The layout of a puzzle file is described on the LMS page; 
     * you may assume the layout is always correct. 
     */
    public Akari(String filename)
    {
        // TODO 3
        FileIO file = new FileIO(filename);
        this.filename = filename;
       
        ArrayList<String> black, zeroes, ones, twos, threes, fours; 
        black = parseString(file.getLines().get(1));
        zeroes = parseString(file.getLines().get(2));
        ones = parseString(file.getLines().get(3));
        twos = parseString(file.getLines().get(4));
        threes = parseString(file.getLines().get(5));
        fours = parseString(file.getLines().get(6));
        
        //initiate size
        size = Integer.parseInt(file.getLines().get(0));
        board = new Space[size][size];
        Space space[] = Space.values();
        
        for (int k = 1; k < 7; k++)
        if(!file.getLines().get(k).equals("")){
        for (int i = 0; i < parseString(file.getLines().get(k)).size(); i++){
        char r = parseString(file.getLines().get(k)).get(i).charAt(0);
            for (int j = 1; j < parseString(file.getLines().get(k)).get(i).length(); j++) 
        {char c = parseString(file.getLines().get(k)).get(i).charAt(j);
            int x = parseIndex(r);
            int y = parseIndex(c);
            board[x][y] = space[k-1];}}
        }
        //for empty tiles
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j]== null) 
                {board[i][j]= Space.EMPTY;}
    }

    /**
     * helper method
     */
    public void setup(String s)
    {
       
    }
    
    /**
     * helper method
     * Parses strings and adds into an array
     */
    public static ArrayList<String> parseString(String s)
    {
        ArrayList<String> side = new ArrayList<>();
        String[] array = s.trim().split("\\s+");
        for(int i = 0; i < array.length; i++){
              side.add(new String(array [i]));}
        return side;       
    }
    
    /**
     * Uses the example file from the LMS page.
     */
    public Akari()
    {
        this("Puzzles/p7-e7.txt");
    }
    
    /**
     * Returns the name of the puzzle file.
     */
    public String getFilename()
    {
        // TODO 1a
        return filename;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 1b
        return size;
    }
    
    /**
     * Returns true iff k is a legal index into the board. 
     */
    public boolean isLegal(int k)
    {
        // TODO 5
        return 0 <= k && k < size;
    }
    
    /**
     * Returns true iff r and c are both legal indices into the board. 
     */
    public boolean isLegal(int r, int c)
    {
        // TODO 6
        return 0 <= r && r < size && 0 <= c && c < size; 
    }
    
    /**
     * Returns the contents of the square at r,c if the indices are legal, 
     * o/w throws an illegal argument exception. 
     */
    public Space getBoard(int r, int c)
    {
        // TODO 7
        if (isLegal(r,c))
        return board[r][c];
        else throw new IllegalArgumentException("Invalid Input");
    }
    
    /**
     * Returns the int equivalent of x. 
     * If x is a digit, returns 0 -> 0, 1 -> 1, etc; 
     * if x is an upper-case letter, returns A -> 10, B -> 11, etc; 
     * o/w throws an illegal argument exception. 
     */
    public static int parseIndex(char x)
    {
        // TODO 2
        if (Character.isDigit(x) ||Character.isUpperCase(x) ) return Character.getNumericValue(x);
        else throw new IllegalArgumentException("Incorrect Input");
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * If r,c is empty, a bulb is placed; if it has a bulb, that bulb is removed.
     */
    public void leftClick(int r, int c)
    {
        // TODO 8
        Space x;
        if (isLegal(r,c)){
        x = board[r][c]; 
        if (x==Space.EMPTY){board[r][c]=Space.BULB;}
        else if (x==Space.BULB){board[r][c]=Space.EMPTY;}
        }
    }
    
    /**
     * Sets all mutable squares on the board to empty.
     */
    public void clear()
    {
        // TODO 4
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j]== Space.BULB) 
                {
                   board[i][j]= Space.EMPTY;
                } 
    }
    
    
    /**
     * Returns the number of bulbs adjacent to the square at r,c. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public int numberOfBulbs(int r, int c)
    {
        // TODO 14
        if (!isLegal(r,c)) 
        throw new IllegalArgumentException("Invalid Input");
        int n = 0;
        int rp = (r + 1);
        int rm = (r - 1);
        int cp = (c + 1);
        int cm = (c - 1);
        if (isLegal(r,cm) && board[r][cm]== Space.BULB) n++;
        if (isLegal(r,cp) && board[r][cp]== Space.BULB) n++;
        if (isLegal(rm,c) && board[rm][c]== Space.BULB)  n++;
        if (isLegal(rp,c) && board[rp][c]== Space.BULB)  n++;
        return n;
    }
    
    /**
     * Returns true iff the square at r,c is lit by a bulb elsewhere. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public boolean canSeeBulb(int r, int c)
    {
        // TODO 15
        if (!isLegal(r,c)) {
        throw new IllegalArgumentException("Invalid Input");}
        for (int k = 1; k < size; k++){int rp = (r + k) ;
        if (!isLegal(rp,c) || !Space.isMutable(board[rp][c])) break;
        else if (board[rp][c]== Space.BULB && isLegal(rp,c)) return true;}
        for (int k = 1; k < size; k++){int rm = (r - k) ;
        if (!isLegal(rm,c) || !Space.isMutable(board[rm][c])) break;
        else if (board[rm][c]== Space.BULB) return true;}
        for (int k = 1; k < size; k++){int cp = (c + k) ;
        if (!isLegal(r,cp) || !Space.isMutable(board[r][cp])) break;
        else if (board[r][cp]== Space.BULB && isLegal(r,cp)) return true;}
        for (int k = 1; k < size; k++){int cm = (c - k) ;
        if (!isLegal(r,cm) || !Space.isMutable(board[r][cm])) break;
        else if (board[r][cm]== Space.BULB && isLegal(r,cm)) return true;}
        return false;
    }
    
    /**
     * Returns an assessment of the state of the puzzle, either 
     * "Clashing bulb at r,c", 
     * "Unlit square at r,c", 
     * "Broken number at r,c", or
     * three ticks, as on the LMS page. 
     * r,c must be the coordinates of a square that has that kind of error. 
     * If there are multiple errors on the board, you may return any one of them. 
     */
    public String isSolution()
    {
        // TODO 16
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (board[r][c] == Space.ZERO && numberOfBulbs(r,c) != 0)
                return "Broken number at "  + r +','+ c;
                else if(board[r][c] == Space.ONE && numberOfBulbs(r,c) != 1)
                return "Broken number at "  + r +','+ c;
                else if(board[r][c] == Space.TWO && numberOfBulbs(r,c) != 2)
                return "Broken number at "  + r +','+ c;
                else if(board[r][c] == Space.THREE && numberOfBulbs(r,c) != 3)
                return "Broken number at "  + r +','+ c;
                else if(board[r][c] == Space.FOUR && numberOfBulbs(r,c) != 4)
                return "Broken number at "  + r +','+ c;
                else if(board[r][c] == Space.BULB && canSeeBulb(r,c))
                return "Clashing bulb at "  + r +','+ c;
                else if(board[r][c] == Space.EMPTY && !canSeeBulb(r,c))
                return "Unlit square at "  + r +','+ c;      
        return "\u2713\u2713\u2713";
    }
}
