/** 
 * @(#)Matrix.java
 * 
 * This file is part of SmartAss and describes class Matrix.
 * Matrix is a MathsOp for typesetting matrices.
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * @version 1.00 2007/08/14
 */

package au.edu.uq.smartass.maths;

/**
* Class Matrix describes MathsOp for typesetting
* on matrices. Elements of Matrix are MathsOps and are stored  
* in private 2-dimensional array MathsOp[][].
*
*/

public class Matrix extends MathsOp {
	private String name;
    private MathsOp[][] matrixArray;
    private String[] texString={"\\left(","\\begin{array}","{r","r","r}\n"," & ", " \\\\ \n","\\end{array}", "\\right)" }; //default tex

/**
 * Constructor Matrix, sets dimensions.
 *
 * @param  number_of_rows	
 * @param  number_of_columns 
 */  
    public Matrix (int number_of_rows, int number_of_columns) {
    	super();
    	if (getTex()==null)
  			setTex(texString);
    	matrixArray=new MathsOp[number_of_rows][number_of_columns];
    }

/**
 * Constructor Matrix, initialises name.
 *
 * @param  matrix_name	name of this matrix 
 */  
    public Matrix (String matrix_name) {
    	super();
    	if (getTex()==null)
  			setTex(texString);
    	setName(matrix_name);
    }
    
 /**
 * Constructor Matrix initialises name and array of elements.
 *
 * @param  String matrix_name	name of this matrix 
 * @param  MathsOp[][] elements	array of elements 
 */     
    public Matrix (String matrix_name, MathsOp[][] elements){
    	super();
    	if (getTex()==null)
  			setTex(texString);
    	setName(matrix_name);
    	setMatrix(elements);
    }
  
 /**
 * Constructor Matrix initialises array of elements.
 *
 * @param  MathsOp[][] elements	array of elements 
 */     
    public Matrix (MathsOp[][] elements){
    	super();
    	if (getTex()==null)
  			setTex(texString);
    	setMatrix(elements);
    }   
    
 /**
 * Method setName set a name of this matrix.
 * 
 * @param  String new_name  new name of this matrix 
 */         
    public void setName(String new_name) {
    	name = new_name;
    }

/**
 * Method getName() returns a name of this matrix.
 * 
 * @return  String  name of this matrix 
 */     
    public String getName() {
    	return name;
    }
/**
 * Method setMatrix set an array of elements of this matrix.
 * Method really sets a reference to the passing array.
 * matrixArray=new_elements
 * @param  MathsOp[][] elements  new array of elements of this matrix 
 */         
    public void setMatrix(MathsOp[][] new_elements){
    	//int height=new_elements.length;   //number of rows
    	//int width=new_elements[0].length; //number of columns
    
    	matrixArray=new_elements;
    
 /*   	for (int i=0; i<height; i++)
    		for (int j=0; j<width; j++)
    			matrixArray[i][j]=new_elements[i][j].clone();*/
    }
      
/**
 * Method getArray() returns an array of the elements of this matrix.
 * 
 * @return  MathOp[][]   elements of this matrix 
 */    
    public MathsOp[][] getArray(){
    /*	if (MatrixArray==null)
    		return null;
    	else{
    	int height=matrixArray.length;   //number of rows
    	int width=matrixArray[0].length; //number of columns	
    	MathsOp[][] returnArray=new MathsOp[height][width]; 
    	for (int i=0; i<height; i++)
    		for (int j=0; j<width; j++)
    			returnArray[i][j]=matrixArray[i][j].clone();	*/
    	return matrixArray;	 
    }
    
 /**
 * Method setElement sets a specific element of this matrix.
 * It really sets a reference to MathsOp object, i.e.
 * matrixArray[row][col] = new_element;
 *
 * @param  row  row of the element
 * @param  col  column of the element 
 * @param  new_element
 */         
    public void setElement(int row, int col, MathsOp new_element) {
    	matrixArray[row][col] = new_element;
    }

/**
 * Method getElement returns a specific element of this matrix.
 * Method really returns the reference to MathsOp object - 
 * the element of the matrix. 
 *
 * @param  row  row of the element
 * @param  col  column of the element 
 * @return  MathsOp  clone of the elementname of this matrix 
 */     
    public MathsOp getElement(int row, int col) {
    	return matrixArray[row][col];
    }
 
 
 
/**
 * Method toString() returns a string representation of this matrix.
 * Returned string depends on tex, set for Matrix. By default -  
 * texString={"\\left(","\\begin{array}","{r","r","r} \n"," & ", " \\\\ \n","\\end{array}", "\\right)" };
 * If there is one element - returns an element without any additional formatting
 * @return  String  string representation of this Matrix 
 */     
	public String toString() {
		if ((matrixArray.length==1) && (matrixArray[0].length==1)) //one element matrix, no brackets
			return matrixArray[0][0].toString();
				//getLocalTex()[0]+matrixArray[0][0].toString()+getLocalTex()[8];
		
		String tex=getLocalTex()[0]+getLocalTex()[1]+getLocalTex()[2];
	
		
		int height=matrixArray.length;   //number of rows
    	int width=matrixArray[0].length; //number of columns
    	
    	for (int i=1; i<width-1; i++)
    		tex+=getLocalTex()[3];
    	tex+=getLocalTex()[4];	
    	for (int i=0; i<height-1; i++){
    		for (int j=0; j<width-1; j++)
    			tex+=matrixArray[i][j].toString()+getLocalTex()[5];	
    		tex+=matrixArray[i][width-1]+getLocalTex()[6];				
    	}			
    	for (int j=0; j<width-1; j++)
    		tex+=matrixArray[height-1][j].toString()+getLocalTex()[5];		
    	tex+=matrixArray[height-1][width-1]+getLocalTex()[7]+getLocalTex()[8];	
		return tex; 
	}
	
/**
 * Method clone() returns a clone of this Matrix.
 *
 * @return  Object   clone of this Matrix  
 */     
    public Object clone() {
		Matrix res = (Matrix) super.clone();
		if(matrixArray!=null) {
			int height=matrixArray.length;   //number of rows
    		int width=matrixArray[0].length; //number of columns	
    		MathsOp[][] tmpArray=new MathsOp[height][width]; 
    		for (int i=0; i<height; i++)
    			for (int j=0; j<width; j++)
    				tmpArray[i][j]=(MathsOp)(matrixArray[i][j].clone());
			res.setMatrix(tmpArray);
		}
		return res;
	} 
 
}
