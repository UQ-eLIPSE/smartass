/* @(#)MatrixSymmetricModule.java
 *
 * This file is part of SmartAss and describes class MatrixSymmetricModule for 
 * question on symmetric matrices. Given a matrix calculates a symmetric matrix from it.
 * Copyright (C) 2007 Department of Mathematics, The University of Queensland
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
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import au.edu.uq.smartass.maths.*;
import java.util.*;
import java.math.*;

/**
* Class MatrixSymmetricModule describes
* questions on calculating symmetric matrices.
*
* @version 1.0 31.3.2008
*/
public class MatrixSymmetricModule extends MathsModule
{
	private String[] matrices;
	private String[] matricesT;
	
	private String[] symmetric;
	private String[] symmetricWorking;


	/**
	* Constructor MatrixSymmetricModule generates the question
	* @params  Engine
	*/	
	public MatrixSymmetricModule(Engine engine) 
	{
		super(engine);

		// Now generate the solution
		generate();
		
	} //constructor

	/**
	 * getSection method typesets a question and solution 
	 * @return a String containing Latex code for the section
	 */		
	public String getSection(String name) 
	{     
		if(name.equals("matrix1")) return matrices[0];
		else if(name.equals("matrix2")) return matrices[1];
		else if(name.equals("matrix3")) return matrices[2];
		else if(name.equals("matrix1t")) return matricesT[0];
		else if(name.equals("matrix2t")) return matricesT[1];
		else if(name.equals("matrix3t")) return matricesT[2];
		else if(name.equals("symmetric1")) return symmetric[0];
		else if(name.equals("symmetric2")) return symmetric[1];
		else if(name.equals("symmetric3")) return symmetric[2];
		else if(name.equals("symmetricworking1")) return symmetricWorking[0];
		else if(name.equals("symmetricworking2")) return symmetricWorking[1];
		else if(name.equals("symmetricworking3")) return symmetricWorking[2];
		
		return super.getSection(name);
	}

	/**
	 * generate method generates the matrices and the solutions
	 */	
	private void generate()
	{
		int i;
		int j;
		
		Matrix Working;
		
		int[][] result;
		
		int[][] matrix23 = new int[2][3];
		int[][] matrix32 = new int[3][2];
		int[][] matrix33 = new int[3][3];
		
		int[][] matrix23T = new int[3][2];
		int[][] matrix32T = new int[2][3];
		int[][] matrix33T = new int[3][3];
		
		matrices = new String[3];
		matricesT = new String[3];
		symmetric = new String[3];
		symmetricWorking = new String[3];
		
		// Generate the matrices
		for(i=0;i<2;i++)
			for(j=0;j<3;j++)
				matrix23[i][j] = RandomChoice.randInt(-9,9);
		
		for(i=0;i<3;i++)
			for(j=0;j<2;j++)
				matrix32[i][j] = RandomChoice.randInt(-9,9);
		
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				matrix33[i][j] = RandomChoice.randInt(-9,9);
		
		matrices[0] = (MatrixAlgebra.makeIntegerMatrix( matrix23 )).toString();
		matrices[1] = (MatrixAlgebra.makeIntegerMatrix( matrix32 )).toString();
		matrices[2] = (MatrixAlgebra.makeIntegerMatrix( matrix33 )).toString();
		
		// Calculate the transposes
		for(i=0;i<2;i++)
			for(j=0;j<3;j++)
				matrix23T[j][i] = matrix23[i][j];
		
		for(i=0;i<3;i++)
			for(j=0;j<2;j++)
				matrix32T[j][i] = matrix32[i][j];
		
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				matrix33T[j][i] = matrix33[i][j];
		
		matricesT[0] = (MatrixAlgebra.makeIntegerMatrix( matrix23T )).toString();
		matricesT[1] = (MatrixAlgebra.makeIntegerMatrix( matrix32T )).toString();
		matricesT[2] = (MatrixAlgebra.makeIntegerMatrix( matrix33T )).toString();
		
		// Now calculate the symmetric matrices
		Working = new Matrix(3, 3);
		result = MatrixAlgebra.multiplyTwoIntegerMatrices( matrix23T, matrix23, Working );
		
		symmetric[0] = (MatrixAlgebra.makeIntegerMatrix( result )).toString();		
		symmetricWorking[0] = Working.toString();
		
		Working = new Matrix(2, 2);
		result = MatrixAlgebra.multiplyTwoIntegerMatrices( matrix32T, matrix32, Working );
		
		symmetric[1] = (MatrixAlgebra.makeIntegerMatrix( result )).toString();
		symmetricWorking[1] = Working.toString();
		
		Working = new Matrix(3, 3);
		result = MatrixAlgebra.multiplyTwoIntegerMatrices( matrix33T, matrix33, Working );
		
		symmetric[2] = (MatrixAlgebra.makeIntegerMatrix( result )).toString();
		symmetricWorking[2] = Working.toString();
	}
  
 } 