package test.menu.generators;

import static org.junit.Assert.assertEquals;

import main.menu.generators.ScriptFileGenerator;
import org.junit.Test;

/**
 * The script generators were verified with openJML so they dont require as many tests.
 *
 * @author Andrew McGhie
 */
public class ScriptFileGeneratorTest {

  @Test
  public void testLoadFile() {
    ScriptFileGenerator generator = new ScriptFileGenerator();
    generator.setFile("resources/fixtures/html/testfile.txt");
    assertEquals("testfile", generator.getScript());
  }
}
