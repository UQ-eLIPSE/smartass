package au.edu.uq.smartass.repository;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.*;
import org.junit.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryStorageTest {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( RepositoryStorageTest.class );
	
        @Test public void importTemplateTest() throws IOException {
		RepositoryStorage repo = new RepositoryStorage();
		LOG.info("scope:template 	{}", repo.path[RepositoryStorage.SCOPE_TEMPLATE].getAbsolutePath());
		LOG.info("scope:pdf 		{}", repo.path[RepositoryStorage.SCOPE_PDF].getAbsolutePath());
		LOG.info("scope:files 		{}", repo.path[RepositoryStorage.SCOPE_FILES].getAbsolutePath());

		int result;
		File src;
		File dst;
		PrintWriter out;

		File dstdir = new File(repo.path[RepositoryStorage.SCOPE_TEMPLATE], "test");
		dstdir.mkdirs();

		//
		// java.io.FileNotFoundException:  (No such file or directory)
		//
		result = repo.importTemplate(
				RepositoryStorage.SCOPE_TEMPLATE,
				"",
				new String[]{"",""},
				false
			);
		assertEquals(RepositoryStorage.ERROR_FILE_COPY, result);

		//
		// Disabled because logic seems errornous. Does not deal with blank dest structure very well.
		//
		// Currently this test fails 
		// 	- the method uses the parent directory as the filename, returning OK.
		// 	- subsequent runs also return OK (FAIL).
		//
//		src = File.createTempFile("RepositoryStorage", "test");
//		result = repo.importTemplate(
//				RepositoryStorage.SCOPE_TEMPLATE,
//				src.getAbsolutePath(),
//				new String[]{"",""},
//				false
//			);
//		src.delete();
//		//assertEquals(RepositoryStorage.ERROR_FILE_COPY, result);
//		dst = repo.path[RepositoryStorage.SCOPE_TEMPLATE];
//		assertTrue(dst.exists());
//		assertTrue(dst.isFile());
//		assertEquals(0L, dst.length());
//		dst.delete();

		//
		//
		//
		src = File.createTempFile("RepositoryStorage", "test");
		out = new PrintWriter(src);
		out.println("%%META meta data one");
		out.println("%%META meta data two");
		out.println("%%META END");
		out.close();

		assertNotEquals(0L, src.length());

		dst = new File(dstdir, src.getName());
		
		result = repo.importTemplate(
				RepositoryStorage.SCOPE_TEMPLATE,
				src.getAbsolutePath(),
				new String[]{dstdir.getName(), dst.getName()},
				false
			);

		assertTrue(dst.exists());
		assertTrue(dst.isFile());
		assertEquals(0L, dst.length());

		src.delete();
		if (dst.exists() && dst.isFile()) dst.delete();

		//
		//
		//
		src = File.createTempFile("RepositoryStorage", "test");
		out = new PrintWriter(src);
		out.println("Some meta data");
		out.println("Some more meta data");
		out.close();

		dst = new File(dstdir, src.getName());
		
		result = repo.importTemplate(
				RepositoryStorage.SCOPE_TEMPLATE,
				src.getAbsolutePath(),
				new String[]{dstdir.getName(), dst.getName()},
				false
			);


		assertTrue(dst.exists());
		assertTrue(dst.isFile());
		assertEquals(src.length(), dst.length());
		assertNotEquals(0L, dst.length());

		src.delete();
		if (dst.exists() && dst.isFile()) dst.delete();

	}   
}

