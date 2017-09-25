/**
 * @author - Manoj Raghunathan
 * @created - 09/22/2017
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int gridSize; // Saves the grid size passed by the user
	private int arraySize; //Saves the 1D array size converted from 2D grid size
	private WeightedQuickUnionUF grid; // The grid object 
	private boolean[] openSite;
	private int vTopSite;
	private int vBottomSite;
	
	   /**
	    * Constructor that creates the grid, saves the grid size, and 
	    * initializes all elements as closed.
	    * 
	    * create n-by-n grid, with all sites blocked
	    * @param integer n - n+1 x n+1 grid size. e.g 20x20, 100x100 grid
	    */
	   public Percolation(int n) {
		  this.gridSize = n;
		  this.arraySize = convertGridToArray(gridSize, gridSize+1);
		  this.grid = new WeightedQuickUnionUF(arraySize);
		  this.openSite = new boolean[arraySize];
		  this.vTopSite = 0;
		  this.vBottomSite = convertGridToArray(gridSize,0);
		  
		  /* Connect the top and bottom grid to virtual top and virtual bottom sites*/
		  for(int i=1; i<=gridSize; i++) {
			  this.grid.union(vTopSite,convertGridToArray(vTopSite + 1,i));
			  this.grid.union(vBottomSite, vBottomSite + i);
		  }
		  
	   }  
	   
	   /**
	    * Open the site -> Connect to all adjacent open sites. Check the sites have
	    * a valid index. 
	    * 
	    * @param row
	    * @param col
	    * @throws IndexOutOfBoundsException
	    */
	   public void open(int row, int col) throws java.lang.IllegalArgumentException {		   
		   
		   if(isOpen(row, col)) { //If its already open, exit
			   return;
		   }
		   
		   this.openSite[convertGridToArray(row, col)] = true;
		   
		   //Connect to top site, if it valid and open
		   if(isValidIndex(row-1,col)) {
			   if(isOpen(row-1,col))
				   this.grid.union(convertGridToArray(row,col), convertGridToArray(row-1,col));
		   }
		   
		   //Connect to bottom site, if it valid and open
		   if(isValidIndex(row+1,col)) {
			   if(isOpen(row+1,col))
				   this.grid.union(convertGridToArray(row,col), convertGridToArray(row+1,col));
		   }
		   
		   //Connect to left site, if it valid and open
		   if(isValidIndex(row,col-1)) {
			   if(isOpen(row,col-1))
				   this.grid.union(convertGridToArray(row,col), convertGridToArray(row,col-1));
		   }
		   
		   //Connect to right site, if it valid and open
		   if(isValidIndex(row,col+1)) {
			   if(isOpen(row,col+1))
				   this.grid.union(convertGridToArray(row,col), convertGridToArray(row,col+1));
		   }
		     
	   }  
	   
	   /**
	    * Check if a site is open or not
	    * @param row
	    * @param col
	    * @return
	    */
	   public boolean isOpen(int row, int col) throws java.lang.IllegalArgumentException {
		   
		   if(!isValidIndex(row,col))
			   throw new java.lang.IllegalArgumentException("Row Index: " + row + 
					   " or Col Index: " + col + " is not between 1 and " + this.gridSize);	
		   
		   return this.openSite[convertGridToArray(row, col)];
	   } 
	   
	   /**
	    * Check if a site inside grid is full or not
	    * @param row
	    * @param col
	    * @return
	    */
	   public boolean isFull(int row, int col) throws java.lang.IllegalArgumentException {
		   
		   if(!isValidIndex(row,col))
			   throw new java.lang.IllegalArgumentException("Row Index: " + row + 
					   " or Col Index: " + col + " is not between 1 and " + this.gridSize);
		   if(isOpen(row,col))
				   return this.grid.connected(convertGridToArray(row,col), this.vTopSite); 
		   return false;
		   //Check if site is connect to vTop
	   }
	   
	   /**
	    * Check for number of open sites 
	    * @return
	    */
	   public  int numberOfOpenSites() {
		   int count = 0;
		   for(int i=0; i < this.arraySize; i++) {
			   if(this.openSite[i] == true)
				   count++;
		   }
		   
		   return count;
	   }    
	   
	   /**
	    * Check if the system percolates i.e. open sites are connected
	    * 
	    * @return false if the system does not percolate
	    */
	   public boolean percolates() {
		   if(this.grid.connected(vTopSite, vBottomSite))
			   return true;
		   else
			   return false;
	   }       
	   
	   /**
	    *  Validate if the row and column index are within bounds. If it is between one and 
	    *  grid size, return true, else return false.
	    * 
	    * @param gridSize
	    * @return true - if the index is within bounds
	    */
	   private boolean isValidIndex (int row, int col) {
		   if(row > 0 && row <= this.gridSize && col > 0 && col <= this.gridSize)
				   return true;
		   else
			   return false;
	   }
	   
	   /**
	    * Converts the grid representation of rows and columns into a 1-Dimensional Array
	    * representation. 
	    * 
	    * @param row - Row number
	    * @param column - Column number
	    * @return row * column -> Converted to 1d Array
	    */
	   private int convertGridToArray(int row, int column) {
		   return (row * (this.gridSize + 1)) + column;
	   }
	   
	   public static void main(String[] args) {}

}
