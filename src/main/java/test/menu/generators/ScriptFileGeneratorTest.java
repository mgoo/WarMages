package test.menu.generators;

import static org.junit.Assert.assertEquals;

import main.menu.generators.ScriptFileGenerator;
import org.junit.Test;

/**
 * Created by mgoo on 1/10/17.
 */
public class ScriptFileGeneratorTest {

  @Test
  public void testLoadFile() {
    ScriptFileGenerator generator = new ScriptFileGenerator();
    generator.setFile("resources/fixtures/html/testfile.txt");
    assertEquals("testfile", generator.getScript());
  }
}
