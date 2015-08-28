/**
 * @(#)MatrixAlgebra.java
 * This file is part of SmartAss maths package and describes class MatrixAlgebra for simpe operations on matrices.
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
 *
 *
 * @author A.Kvyatkovskyy
 * @version 1.00 2007/8/23
 */

package au.edu.uq.smartass.maths;
import java.math.*;

public class MatrixAlgebra {

    //returns double[][] - result of multiplication, working - matrix with working (multiplication and addition of the elements)
 public static double[][] multiplyTwoDecimalMatrices(double [][] firstMatrix, double[][] secondMatrix, Matrix working, 
 																				int mscale, RoundingMode rmode, boolean trailingZeros){
 	int height=firstMatrix.length;
 	int width=firstMatrix[0].length;
 	int resWidth=secondMatrix[0].length;
 	MathsOp[][] ops=new MathsOp[height][resWidth];
 	double[][] res=new double[height][resWidth];
 	MathsOp tmpOp;
 	
 	for (int i=0; i<height; i++) //multiply
 		for (int j=0; j<resWidth; j++){
 			res[i][j]=firstMatrix[i][0]*secondMatrix[0][j];	
 			tmpOp=new Multiplication(
 				(trailingZeros) ? new DecimalNumber(firstMatrix[i][0], mscale, rmode) : 
 						(new DecimalNumber(new BigDecimal(firstMatrix[i][0]).setScale(mscale,rmode))).trimZeros(),
 				(trailingZeros) ? new DecimalNumber(secondMatrix[0][j], mscale, rmode) : 
 						(new DecimalNumber(new BigDecimal(secondMatrix[0][j]).setScale(mscale,rmode))).trimZeros()
 				);
 			for (int k=1; k<width; k++){
 				res[i][j]+=firstMatrix[i][k]*secondMatrix[k][j];	
 				tmpOp=new Addition(tmpOp, new Multiplication(
 					(trailingZeros) ? new DecimalNumber(firstMatrix[i][k], mscale, rmode) : 
 						(new DecimalNumber(new BigDecimal(firstMatrix[i][k]).setScale(mscale,rmode))).trimZeros(),
 				(trailingZeros) ? new DecimalNumber(secondMatrix[k][j], mscale, rmode) : 
 						(new DecimalNumber(new BigDecimal(secondMatrix[k][j]).setScale(mscale,rmode))).trimZeros()
 					));				
 			}
 			ops[i][j]=tmpOp;		
 		}		 		
 	working.setMatrix(ops);
 	return res;		
 }	 
 	
  //returns int[][] - result of multiplication, working - matrix with working (multiplication and addition of the elements)
 public static int[][] multiplyTwoIntegerMatrices(int [][] firstMatrix, int[][] secondMatrix, Matrix working){
 	int height=firstMatrix.length;
 	int width=firstMatrix[0].length;
 	int resWidth=secondMatrix[0].length;
 	MathsOp[][] ops=new MathsOp[height][resWidth];
 	int[][] res=new int[height][resWidth];
 	MathsOp tmpOp;
 	
 	for (int i=0; i<height; i++) //multiply
 		for (int j=0; j<resWidth; j++){
 			res[i][j]=firstMatrix[i][0]*secondMatrix[0][j];	
 			tmpOp=new Multiplication(new IntegerNumber(firstMatrix[i][0]),
 				new IntegerNumber(secondMatrix[0][j]));
 			for (int k=1; k<width; k++){
 				res[i][j]+=firstMatrix[i][k]*secondMatrix[k][j];	
 				tmpOp=new Addition(tmpOp, new Multiplication(
 					new IntegerNumber(firstMatrix[i][k]), new IntegerNumber(secondMatrix[k][j]))); 
 			}
 			ops[i][j]=tmpOp;		
 		}		 		
 	working.setMatrix(ops);
 	return res;		
 }	 
 		
 //makes and returns Matrix of elements - DecimalNumbers, from double[][]	 array 
  public static Matrix makeDecimalMatrix(double[][] numbers, int mscale, RoundingMode rmode, boolean trailingZeros){
 	int height=numbers.length;
 	int width=numbers[0].length;
 	MathsOp[][] ops=new MathsOp[height][width];
 	for (int i=0; i<height; i++)
 		for (int j=0; j<width; j++)
 			ops[i][j]= (trailingZeros) ? new DecimalNumber(numbers[i][j], mscale, rmode) : 
 				(new DecimalNumber(new BigDecimal(numbers[i][j]).setScale(mscale,rmode))).trimZeros();
 	return new Matrix(ops);		
 }  
    
 //makes and returns Matrix of elements - IntegerNumbers, from int[][]	 array 
  public static Matrix makeIntegerMatrix(int[][] numbers){
 	int height=numbers.length;
 	int width=numbers[0].length;
 	MathsOp[][] ops=new MathsOp[height][width];
 	for (int i=0; i<height; i++)
 		for (int j=0; j<width; j++)
 			ops[i][j]=  new IntegerNumber(numbers[i][j]); 
 	return new Matrix(ops);		
 }     
 	
  //returns int[][] - result of multiplication of integer number and integer matrix, working - matrix with working (multiplications in place of the elements)
 public static int[][] multiplyMatrixByNumber(int number, int [][] matrixToMultiply, Matrix working){
 	int height=matrixToMultiply.length;
 	int width=matrixToMultiply[0].length;
 	MathsOp[][] ops=new MathsOp[height][width];
 	int[][] res=new int[height][width];
 	MathsOp intNum=new IntegerNumber(number);
 	for (int i=0; i<height; i++) //multiply
 		for (int j=0; j<width; j++){
 			res[i][j]=number*matrixToMultiply[i][j];
 			ops[i][j]=new Multiplication(intNum, new IntegerNumber(matrixToMultiply[i][j]));	
 	}		 			
 	working.setMatrix(ops);
 	return res;		
 }	    
}