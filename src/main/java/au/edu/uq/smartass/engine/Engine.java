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

import java.lang.reflect.Constructor;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Engine {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( Engine.class );

        /** */
	private static Preferences preferences = Preferences.userRoot().node("au/edu/uq/smartass");

	
	/** Map system installed MathsModule class name to MathsModule class. */
	Map<String, Class<MathsModule> > mathModuleMap = new HashMap<String, Class<MathsModule> >();

	/** */
	HashMap<String, Class> readers = new HashMap<String, Class>();

	/** */
	HashMap<String, DataSource> datasources = new HashMap<String, DataSource>();

	/**
	 * Default Constructor
	 */
	public Engine() {
		LOG.info( "::Engine() initialisation:");
		LOG.info( "::Engine()[ preferences=>{}] ", preferences.toString() );
                initialiseModules();
	}

        /**
         *
         */
	public String getPreference(String key) { return getPreference(key, ""); }
	public String getPreference(String key, String def) { return preferences.get(key, def); }
	
        /**
         *
         */
        private void initialiseModules() {
                String[] moduleRoots = preferences.get("modules_root", ".").split(";"); 
		LOG.info( "::initialiseModules()[ moduleRoots=>{}] ", Arrays.toString(moduleRoots) );
		for (String modRoot : moduleRoots) {
                        File modRootDir = new File(modRoot);
                        if (modRootDir.exists() && modRootDir.isDirectory()) 
                                        collectModules(modRootDir);
                        else
                                        LOG.warn( 
                                                        "::initialiseModules() >> NOT a module root => {}", 
                                                        modRootDir.getAbsolutePath() 
                                                );
                }
        }

	/**
	 * Populates map with installed MathsModules.
	 *
	 * Recursively search directory structure looking for class files. If class files represent 
	 * <code>MathsModule</code> assignable objects they are added to the collection.
	 *
	 * <p> It is assumed that:
	 * <p> 		1) module root is a directory in CLASSPATH
	 * <p> 		2) the path from the module root specifies package name
	 * <p> 		3) file names ending with '.class' are Java class files.
	 *
	 * @param 	dir 	module root directory to search.
	 * @param 	pkg 	package name.
	 */
	private void collectModules(File rootDir) { collectModules(rootDir, ""); }

	private void collectModules(File dir, String pkg) {
		LOG.info("::collectModules()[ dir=>{}, pkg=>{} ]", dir.getAbsolutePath(), pkg);

		File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File pathname) { 
					return pathname.isDirectory() || pathname.getName().endsWith(".class"); 
				}
			});
		for (File file : files) {
			String pkgname = pkg + file.getName().replaceFirst("\\.class$", "");

			if (file.isDirectory()) {
				collectModules(file, pkgname + '.');
			}else {
				LOG.debug("::collectModules()[ class=>{} ]", pkgname);
				try {
					Class<?> unknown_class = Class.forName(pkgname);
					if (MathsModule.class.isAssignableFrom(unknown_class)) {
						LOG.info("::collectModules()[ MathsModule=>{} ]", unknown_class.getName());
						@SuppressWarnings("unchecked")
						Class<MathsModule> mathModCls = (Class<MathsModule>)unknown_class;
						mathModuleMap.put(
								mathModCls.getSimpleName().toLowerCase(), 
								mathModCls
							);
					}
				} catch (ClassNotFoundException ex) { 
					LOG.debug("::collectModules(), Problem collecting module => {}", ex.getMessage());
				}
			}
		}
	}

	/*This metod creates and returns specific template module by type*/
	public TemplateReader getTemplateReader(String type)
	{
		return new TexReader(this); 
		//At this moment we just create PlainTextReader, whatever value "type" parameter contains
	}

	/**
	 * Retrieve a pre-registered MathsModule by name.
	 *
	 * @see <code>getMathsModule</code>
	 */
	public MathsModule getMathsModule(String module_name) {
		return getMathsModule(module_name, new String[0] );
	}

	/**
	 * Retrieve a MathsModule by name which has been pre-registered with the Engine.
	 *
	 * @param 	module_name 	name to search for
	 * @param 	params 		array of string parameters passed to constructor
	 * @return 	MathsModule 	A <code>MathsModule</code> identifed by name or NULL if not found.
	 */
	public MathsModule getMathsModule(String module_name, String[] params) {
		LOG.info( "::getMathsModule( {}, {} )", module_name, Arrays.toString(params) );
		Class<MathsModule> modc = mathModuleMap.get(module_name);
		if (null == modc) return null; 		// @TODO: Better to pass Exception up the stack rather than force a 'check for NULL'. 

		try {
			return 0 == params.length
					? modc.getConstructor(Engine.class).newInstance(this)
					: modc.getConstructor(Engine.class, String[].class).newInstance(this, params)
				;
		} catch (Exception ex) {
			LOG.error( "Could not retrieve a MathsModule registered in the Engine! [ {} ]", ex.getMessage() );
			LOG.debug( "{}", Arrays.toString(ex.getStackTrace()) );
			return null;
		}
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

                String[] templateRoots = preferences.get("templates_root", ".").split(";");
		LOG.info( "::getTemplateStream()[ name=>{}, templateRoots=>{} ] ", name, Arrays.toString(templateRoots) );

                File template;
		try {
                        for (String templateRoot : templateRoots) {
                                template = new File(templateRoot, name);
                                if (template.exists()) return new FileInputStream(template);
                        }
			
			//not found, check for legacy mode
			template = new File(name);
			if (name.equals(template.getName())) //yes, we have filename without path
				 // look for file in subdirectories of each templateRoots directory
				for(int i=0;i<templateRoots.length;i++) {
					File d = new File(templateRoots[i]);
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
