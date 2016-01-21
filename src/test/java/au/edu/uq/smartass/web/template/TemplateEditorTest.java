package au.edu.uq.smartass.web.template;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by uqpwalle on 6/01/2016.
 */
public class TemplateEditorTest {

    private TemplateEditor editor = new TemplateEditor();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPrepareImport() throws Exception {

    }

    @Test
    public void testSplitMetadata() throws Exception {

        helperSplitMetadata(0, "", "", 0);
        helperSplitMetadata(0, "\n", "", 0);

        helperSplitMetadata(1, "before\nafter\n", "before\nafter\n", 0);
        helperSplitMetadata(1, "before\nafter", "before\nafter\n", 0);

        helperSplitMetadata(3, "%%META\n%%META END\n", "", 0);
        helperSplitMetadata(3, "%%META\n%%META END", "", 0);

        helperSplitMetadata(4, "%%META\n--0--\n%%META END\n", "", 1);

        helperSplitMetadata(5, "before\n%%META\n--0--\n--1--\n%%META END\nafter", "before\nafter\n", 2);
        helperSplitMetadata(5, "before\n%%META\n--0--\n--1--\n%%META END\nafter\n", "before\nafter\n", 2);

    }

    private void helperSplitMetadata(
            final long tag,
            final String input,
            final String expected,
            final int metaCount
    ) throws Exception {

        List<String> metadata = new ArrayList<>();
        String result = editor.splitMetadata(input, metadata);

        assertEquals(expected, result);

        assertEquals(metaCount, metadata.size());
        for (int i = 0; i < metadata.size(); ++i) {
            String token = String.format("--%d--", i);
            assertEquals(token, metadata.get(i));
            assertTrue(metadata.contains(token));
        }
    }

    @Test
    public void testPreparseMetadata() throws Exception {

        /* STATES:
                    0   DEFAULT (%%KEYWORDS, %%CREATED,

                    0   [Subsequent tags will overwrite!]
                        %%KEYWORDS keyword001 keyword002 ...

                    1   [Subsequent tags will overwrite!]
                        %%DESCRIPTION Some Description
                        %Some Description*

                    2   %%FILE
                        %Filename*

                    3   %%UPDATE
                    4   %%AUTHOR
                        %AuthorName*

                    5   %%MODULE
                    6       %%PARAMETERS (in %%MODULE)
                            %parameter*

                    7   %%UPDATE AUTHOR
                        %supdauthor

         */

        PreparsedMetadataModel ppmModel;

        List<String> metadata;

        // --0--
        ppmModel = editor.preparseMetadata(null);
        assert(ppmModel.isEmpty());

        metadata = Arrays.asList();
        ppmModel = editor.preparseMetadata(metadata);
        assert(ppmModel.isEmpty());

        // --1--
        metadata = Arrays.asList( "ignore this line" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(ppmModel.isEmpty());

        metadata = Arrays.asList( "(0): ignore this line", "(1): ignore this line" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(ppmModel.isEmpty());

        // --2--
        metadata = Arrays.asList( "%%KEYWORDS" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(ppmModel.isEmpty());

        metadata = Arrays.asList( "%%KEYWORDS keyword001 KEYWORD001 KeyWord001" );
        ppmModel = editor.preparseMetadata(metadata);
        assertEquals("keyword001 KEYWORD001 KeyWord001", ppmModel.getKeywords());

        metadata = Arrays.asList(
                "%%KEYWORDS keyword001 KEYWORD001 KeyWord001",
                "%%KEYWORDS keyword002 KEYWORD002 KeyWord002"
        );
        ppmModel = editor.preparseMetadata(metadata);
        //assertEquals("keyword001 KEYWORD001 KeyWord001 keyword002 KEYWORD002 KeyWord002", ppmModel.getKeywords());
        assertEquals("keyword002 KEYWORD002 KeyWord002", ppmModel.getKeywords());

        // --3--
        metadata = Arrays.asList( "%%DESCRIPTION" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(ppmModel.isEmpty());

        metadata = Arrays.asList( "%%DESCRIPTION 000:Text" );
        ppmModel = editor.preparseMetadata(metadata);
        assertEquals("000:Text", ppmModel.getDescription());

        metadata = Arrays.asList( "%%DESCRIPTION 000:Text", "%001:Text" );
        ppmModel = editor.preparseMetadata(metadata);
        assertEquals("000:Text\n001:Text", ppmModel.getDescription());

        metadata = Arrays.asList( "%%DESCRIPTION 000:Text", "%001:Text", "AND THIS", "%002:Text" );
        ppmModel = editor.preparseMetadata(metadata);
        assertEquals("000:Text\n001:Text\nAND THIS\n002:Text", ppmModel.getDescription());

        metadata = Arrays.asList( "%%DESCRIPTION", "%001:Text", "%002:Text", "AND THIS"  );
        ppmModel = editor.preparseMetadata(metadata);
        assertEquals("\n001:Text\n002:Text\nAND THIS", ppmModel.getDescription());

        metadata = Arrays.asList( "%%DESCRIPTION", "%001:Text", "%%DESCRIPTION AND THIS"  );
        ppmModel = editor.preparseMetadata(metadata);
        assertEquals("AND THIS", ppmModel.getDescription());

        // --4--
        metadata = Arrays.asList( "%%FILE" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getFiles().get(0)[0], "");
        assertEquals(ppmModel.getFiles().get(0)[1], "");

        metadata = Arrays.asList( "%%FILE 000:FileName" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getFiles().get(0)[0], "000:FileName");
        assertEquals(ppmModel.getFiles().get(0)[1], "");

        metadata = Arrays.asList( "%%FILE 000 File Name" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getFiles().get(0)[0], "000 File Name");
        assertEquals(ppmModel.getFiles().get(0)[1], "");

        metadata = Arrays.asList( "%%FILE 000:FileName", "%%FILE 001:FileName" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getFiles().get(0)[0], "000:FileName");
        assertEquals(ppmModel.getFiles().get(1)[0], "001:FileName");
        assertEquals(ppmModel.getFiles().get(0)[1], "");
        assertEquals(ppmModel.getFiles().get(1)[1], "");

        // --X--
        // %%FILE: similar for %%UPDATE, %%UPDATE AUTHOR

        // --5--
        metadata = Arrays.asList( "%%MODULE 000:Module" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getModules().get(0)[0], "000:Module");
        assertEquals(ppmModel.getModules().get(0)[1], "");
        assertEquals(ppmModel.getModules().get(0)[2], "");
        assertEquals(ppmModel.getModules().get(0)[3], "");

        metadata = Arrays.asList( "%%MODULE 000:Module", "%%MODULE 001:Module" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getModules().get(0)[0], "000:Module");
        assertEquals(ppmModel.getModules().get(1)[0], "001:Module");

        metadata = Arrays.asList( "%%MODULE 000:Module", "%001:Module" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getModules().get(0)[0], "000:Module");
        assertEquals(ppmModel.getModules().get(0)[1], "");
        assertEquals(ppmModel.getModules().get(0)[2], "\n001:Module");
        assertEquals(ppmModel.getModules().get(0)[3], "");

        // --5.1--
        metadata = Arrays.asList( "%%PARAMETERS" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(ppmModel.isEmpty());

        metadata = Arrays.asList( "%%MODULE 000:Module", "%%PARAMETERS" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getModules().get(0)[0], "000:Module");
        assertEquals(ppmModel.getModules().get(0)[1], "");
        assertEquals(ppmModel.getModules().get(0)[2], "");
        assertEquals(ppmModel.getModules().get(0)[3], "\n");

        metadata = Arrays.asList( "%%MODULE 000:Module", "%%PARAMETERS 000:Parameter" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getModules().get(0)[0], "000:Module");
        assertEquals(ppmModel.getModules().get(0)[3], "\n000:Parameter");

        metadata = Arrays.asList( "%%MODULE 000:Module", "%%PARAMETERS 000:Parameter", "001:Parameter" );
        ppmModel = editor.preparseMetadata(metadata);
        assert(!ppmModel.isEmpty());
        assertEquals(ppmModel.getModules().get(0)[0], "000:Module");
        assertEquals(ppmModel.getModules().get(0)[3], "\n000:Parameter\n001:Parameter");
    }

    @Test
    public void testParseAuthor() throws Exception {

    }

    @Test
    public void testParseMetadata() throws Exception {

    }

    @Test
    public void testAfterSelectMetadata() throws Exception {

    }

    @Test
    public void testUploadExamples() throws Exception {

    }

    @Test
    public void testPrepareSave() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testRemoveFromList() throws Exception {

    }

    @Test
    public void testRemoveFromListNotLast() throws Exception {

    }

    @Test
    public void testDeleteExample() throws Exception {

    }

    @Test
    public void testCheckTemplateName() throws Exception {

    }

    @Test
    public void testStartRepositoryTransaction() throws Exception {

    }

    @Test
    public void testCommitRepositoryTransaction() throws Exception {

    }

    @Test
    public void testRollbackRepositoryTransaction() throws Exception {

    }

    @Test
    public void testSetAuthorsDao() throws Exception {

    }

    @Test
    public void testSetFilesDao() throws Exception {

    }

    @Test
    public void testSetModulesDao() throws Exception {

    }

    @Test
    public void testSetTemplatesDao() throws Exception {

    }

    @Test
    public void testSetStorage() throws Exception {

    }

    @Test
    public void testSetClassificationsDao() throws Exception {

    }
}