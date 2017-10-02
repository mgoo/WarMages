package test.menu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * @author Andrew McGhie
 */
public class MenuTest {
    
    @Test
    public void testMenu() {
        LoadMenu loadMenu = new LoadMenu(null, null, null);
        assertTrue(loadMenu.getHtml().contains("<html>"));
    }
}
