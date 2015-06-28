package automatabuilder;

import automatabuilder.parser.AutomataParser;
import interfaces.IAutomaton;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by oliv on 6/28/15.
 */
public class BatchTestTest {


    @Test
    public void testFile() {
        IAutomaton dfa = AutomataParser.parseXmlFile("./odd-ones.xml");
        File f = new File("./testfile");
        String result = dfa.testFile(f);
        System.out.println(result);
        String expected = "aa                  Invalid word\nbbaaa               Invalid word\n00000000000000000.. Accepted\n111000              Accepted\n";
        Assert.assertEquals(expected,result);
    }
}
