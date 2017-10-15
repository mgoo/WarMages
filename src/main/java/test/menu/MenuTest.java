package test.menu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import main.Main;
import main.menu.Menu;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test Menu class.
 * Since the menus are just integration between java and javascript there was not much to test
 *
 * @author Andrew McGhie
 */
public class MenuTest {


  /**
   * Would work but javafx skrews this up and will fail if run by itself.
   */
  @Ignore
  @Test
  public void test_onLoadOnExitCalls() {
    Menu menu = mock(Menu.class);
    Main main = mock(Main.class);
    when(main.loadMenu(menu)).thenCallRealMethod();

    main.loadMenu(menu);

    verify(menu, times(0)).onExit();
    verify(menu, times(1)).onLoad();
    verify(menu, times(1)).getHtml();

    main.loadMenu(menu);

    verify(menu, times(2)).onLoad();
    verify(menu, times(1)).onExit();
    verify(menu, times(2)).getHtml();

  }

}
