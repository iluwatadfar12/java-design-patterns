package com.iluwatar.servicetoworker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The type Dispatcher test.
 */
public class DispatcherTest {

  /**
   * Test perform action.
   */
  @Test
  public void testPerformAction() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    GiantView giantView = new GiantView();
    Dispatcher dispatcher = new Dispatcher(giantView);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    dispatcher.addAction(action);
    for (final var nourishment : Nourishment.values()) {
      for (final var fatigue : Fatigue.values()) {
        for (final var health : Health.values()) {
          Command cmd = new Command(fatigue, health, nourishment);
          dispatcher.performAction(cmd, 0);
          assertEquals(nourishment, model.getNourishment());
          assertEquals(fatigue, model.getFatigue());
          assertEquals(health, model.getHealth());
        }
      }
    }
  }

  @Test
  public void testUpdateView() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    GiantView giantView = new GiantView();
    Dispatcher dispatcher = new Dispatcher(giantView);
    assertDoesNotThrow(() -> dispatcher.updateView(model));
  }
}



