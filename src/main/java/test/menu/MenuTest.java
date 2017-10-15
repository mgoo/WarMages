package test.menu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import main.Main;
import main.menu.Menu;
import org.junit.Test;

/**
 * @author Andrew McGhie
 */
public class MenuTest {

  @Test
  public void test_onLoadOnExitCalls() {
    Menu menu = mock(Menu.class);
    Main main = mock(Main.class);
    when(main.loadMenu(menu)).thenCallRealMethod();

    main.loadMenu(menu);

    verify(menu, times(1)).onLoad();
    verify(menu, times(0)).onExit();
    verify(menu, times(1)).getHtml();

    main.loadMenu(menu);

    verify(menu, times(2)).onLoad();
    verify(menu, times(1)).onExit();
    verify(menu, times(2)).getHtml();

  }

}
