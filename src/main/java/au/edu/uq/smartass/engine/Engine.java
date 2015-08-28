/* This file is part of SmartAss and describes class Engine - the core of SmartAss.
/* This file is part of SmartAss and describes class Engine - the core of SmartAss.
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
 */
/* This file is part of SmartAss and describes class Engine - the core of SmartAss.
/* This file is part of SmartAss and describes class Engine - the core of SmartAss.
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
 */
/* This file is part of SmartAss and describes class Engine - the core of SmartAss.
/* This file is part of SmartAss and describes class Engine - the core of SmartAss.
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
 */
package au.edu.uq.smartass.engine;

import au.edu.uq.smartass.maths.*;
import au.edu.uq.smartass.script.DataSource;
import au.edu.uq.smartass.templates.*;
import au.edu.uq.smartass.templates.texparser.TexParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Constructor;
import  java.util.prefs.*;

public class Engine {
	Preferences prefs;
	String[] modulesRoot;
	String[] templatesRoot;
	Class<MathsModule> maths_class;
	HashMap<String, Class> modules;
	HashMap<String, Class> readers;
	HashMap<String, DataSource> datasources;

	public Engine() {
		prefs = Preferences.userRoot().node("au/edu/uq/smartass");
		maths_class = MathsModule.class;
		modules = new HashMap<String, Class>();
		readers = new HashMap<String, Class>();
		datasources = new HashMap<String, DataSource>();
		readPreferences();
		for(int i=0;i<modulesRoot.length;i++)
			collectModules(new File(modulesRoot[i]), "");
	}

	/**
	 * Read engine preferences  
	 *
	 */
	void readPreferences() {
		modulesRoot = prefs.get("modules_root", ".").split(";"); 
		templatesRoot = prefs.get("templates_root", ".").split(";");
	}

	public String getPreference(String pref_name) {
		return prefs.get(pref_name, "");
	}
	
	/**
	 *  Collects MathsModules that is installed in the system 
	 */
	void collectModules(File f, String packg) {
		File list[] = f.listFiles();
		if(list==null)
			return;
		for (int i=list.length; --i>=0;) {
			String fname = list[i].getName();
			if(list[i].isDirectory()) 
				collectModules(list[i], packg+fname+'.');
			else if(fname.indexOf(".class")!=-1 && fname.indexOf(".class")==fname.length()-6)
				try {
					//Assume that 
					// a) modulesRoot is a directory included in CLASSPATH; 
					// b) path from modulesRoot forms package name
					// c) any file containing ".class" in its name is a Java class
					//Loading class...

					Class found_class=Class.forName(packg+fname.substring(0,fname.indexOf(".class")));

					//Test if class is a MathsModule descendant
					if(maths_class.isAssignableFrom(found_class)) {
						modules.put(found_class.getSimpleName().toLowerCase(), found_class);
						System.out.println(found_class.getSimpleName().toLowerCase());
					}
				} catch(ClassNotFoundException e)  {
					System.out.println("ClassNotFoundException with "+packg+fname.substring(0,fname.indexOf(".class")));
				} catch (Error el) {
					System.out.println("Error"+el+"with"+packg+fname.substring(0,fname.indexOf(".class")));
				}
		}
	}

	/*This metod creates and returns specific template module by type*/
	public TemplateReader getTemplateReader(String type)
	{
		return new TexReader(this); 
		//At this moment we just create PlainTextReader, whatever value "type" parameter contains
	}

	public MathsModule getMathsModule(String module_name) 
	{
		Class<MathsModule> modc = modules.get(module_name);
		if(modc!=null) 
			try {
				Constructor<MathsModule> constr = modc.getConstructor(Engine.class);
				return constr.newInstance(this);
			} catch(Exception e) {
				System.out.println(e);
				return null;
			}
			else
				return null;
	}

	public MathsModule getMathsModule(String module_name, String[] param) 
	{
		Class<MathsModule> modc = modules.get(module_name);
		if(modc!=null) 
			try {
				if(param.length>0)
					return modc.getConstructor(Engine.class, String[].class).newInstance(this, param);
				else
					return modc.getConstructor(Engine.class).newInstance(this);
			} catch(Exception e) {
				System.out.println(e);
				return null;
			}
			else
				return null;
	}

	/*This method takes the template type and body (not the template file name) as its arguments
	 * This is useful to isolate template processing from file system specifics and so on.
	 * Returns the result of template processing
	 */
	public Map<String, String> processTemplate(InputStream template, String type) throws TemplateParseException
	{
		TemplateReader tr = getTemplateReader(type);

		//----------- it seems that we need no this in engine... ------------
		//Because MathsModule descendants can change TeX representation of Ops   
		//we set them to default before load and execute template
		//to ensure that some
		//MathsOp.clearAllTex();
		tr.loadTemplate(template);
		return tr.execute();
	}

	public Map<String, String> processFile(String file_name) throws TemplateNotFoundException, TemplateParseException
	{
		InputStream stream = getTemplateStream(file_name);
		if(stream!=null)
			return processTemplate(stream, file_name.substring(file_name.length()-3,file_name.length())); 
		return null;
	}
	
	public InputStream getTemplateStream(String name) throws TemplateNotFoundException {
		try {
			System.out.println(name);
			for(int i=0;i<templatesRoot.length;i++) {
				File f = new File(templatesRoot[i],name);
				if(f.exists()) 
					return new FileInputStream(f); 
			}
			
			//not found, check for legacy mode
			File f = new File(name);
			if(name.equals(f.getName())) //yes, we have filename without path
				 // look for file in subdirectories of each templatesRoot directory
				for(int i=0;i<templatesRoot.length;i++) {
					File d = new File(templatesRoot[i]);
					if(d.exists()) {
						InputStream stream = getTemplateStream(d, name);
						if(stream!=null)
							return stream;
					}
				}
		} catch(FileNotFoundException e) {
			throw new TemplateNotFoundException(name); 
		}
		throw new TemplateNotFoundException(name); 
	}
	
	private InputStream getTemplateStream(File dir, String name) {
		try {
			File f;
			File list[] = dir.listFiles();
			if(list==null)
				return null;
	
			for(int i=list.length; --i>=0;) {
				f = new File(list[i], name);
				if(f.exists()) 
					return new FileInputStream(f);
				InputStream stream = getTemplateStream(list[i], name);
				if(stream!=null)
					return stream;
		}
		} catch(FileNotFoundException e) {}
		
		return null;
	}
	
	public void addDataSource(String name, DataSource ds) {
		DataSource oldds = datasources.get(name);
		if(oldds!=null)
			oldds.close();
		datasources.put(name, ds);
	}
	
	public DataSource getDataSource(String name) {
		return datasources.get(name);
	}

	public void close() {
		clearDataSources();
	}
	
	public void clearDataSources() {
		for(DataSource ds : datasources.values()) 
			ds.close();
		datasources.clear();
	}
}
