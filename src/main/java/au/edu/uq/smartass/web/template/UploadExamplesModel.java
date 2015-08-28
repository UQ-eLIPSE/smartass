/* This file is part of SmartAss and contains the UploadExamplesModel class that is
 * used as the model to upload pdf files with template execution examples. 
 * 
 * Copyright (C) 2008 The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package au.edu.uq.smartass.web.template;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * The UploadExamplesModel class that is
 * used as the model to upload pdf files with template execution examples.
 */
public class UploadExamplesModel implements Serializable {
	byte[] questions;
	byte[] solutions;
	byte[] shortanswers;

	public byte[] getQuestions() {
		return questions;
	}
	
	public byte[] getSolutions() {
		return solutions;
	}
	
	public byte[] getShortanswers() {
		return shortanswers;
	}
	
	public void setQuestionsMultipart(MultipartFile data) throws IOException {
		this.questions = data.getBytes();
	}
	
	public void setSolutionsMultipart(MultipartFile data) throws IOException {
		this.solutions = data.getBytes();
	}
	
	public void setShortanswersMultipart(MultipartFile data) throws IOException {
		this.shortanswers= data.getBytes();
	}

}
