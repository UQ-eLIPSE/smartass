package au.edu.uq.smartass.web.composer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by uqpwalle on 11/01/2016.
 */
public class AssignmentEditorTest {

    private AssignmentEditor editor = new AssignmentEditor();


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitAssignment() throws Exception {

    }

    @Test
    public void testPreprocessCode() throws Exception {
        AssignmentConstruct constuct = new AssignmentConstruct();

        String defaultCode = "\\input{smartass.tex}\\begin{document}\n\\end{document}";
        constuct.setDecorateWithLatex(false);
        assertEquals(defaultCode, constuct.getCode());
        assertEquals(defaultCode, editor.preprocessCode(constuct));

    }
}