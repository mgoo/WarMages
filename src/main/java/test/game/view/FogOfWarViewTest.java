package test.game.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.view.FogOfWarView;
import main.images.GameImageResource;
import main.util.Event;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the FogOfWarView class.
 *
 * @author Andrew McGhie
 */
public class FogOfWarViewTest extends GameViewTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testFogOfWar_withoutUnit() {
    this.gameView.onTick(0L);

    FogOfWarView fow = this.gameView.getFogOfWarView();
    assertEquals(0, fow.getImagePosition(0).x, 0.001);
    assertEquals(0, fow.getImagePosition(0).y, 0.001);
    assertEquals(this.config.getContextScreenWidth(), fow.getImageSize().width, 0.001);
    assertEquals(this.config.getContextScreenHeight(), fow.getImageSize().height, 0.001);

    // Check center is not see though
    assertNotEquals(0, fow.getImage().getRGB(0 ,0));
    assertNotEquals(0, fow.getImage().getRGB(config.getEntityViewTilePixelsX(), 0));

  }

  @Test
  public void testFogOfWar_withUnit() {
    Unit unit = mock(DefaultUnit.class);
    when(unit.getCentre()).thenReturn(new MapPoint(0, 0));
    when(unit.getSize()).thenReturn(new MapSize(1, 1));
    when(unit.getRemovedEvent()).thenReturn(new Event<Void>());
    when(unit.accept(any(), any())).thenCallRealMethod();
    when(unit.getTeam()).thenReturn(Team.PLAYER);
    when(unit.getLineOfSight()).thenReturn(10D);
    when(unit.getHealedEvent()).thenReturn(new Event<>());
    when(unit.getImage()).thenReturn(GameImageResource.TEST_IMAGE_1_1.getGameImage());

    when(world.recieveRecentlyAddedEntities()).thenReturn(
        Collections.singletonList(unit),
        Collections.emptyList()
    );
    when(gameModel.getWorld()).thenReturn(world);
    this.gameView.onTick(0L);

    FogOfWarView fow = this.gameView.getFogOfWarView();
    assertEquals(0, fow.getImagePosition(0).x, 0.001);
    assertEquals(0, fow.getImagePosition(0).y, 0.001);
    assertEquals(this.config.getContextScreenWidth(), fow.getImageSize().width, 0.001);
    assertEquals(this.config.getContextScreenHeight(), fow.getImageSize().height, 0.001);

    // Check center is see though
    assertEquals(0,
        fow.getImage().getRGB(
            this.config.getContextScreenWidth() / 2 ,
            this.config.getContextScreenHeight() / 2
        )
    );

    // Check LOS
    assertNotEquals(0, fow.getImage().getRGB(config.getEntityViewTilePixelsX(), 0));
  }
}
