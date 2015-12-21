package com.iluwatar.monostate;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/21/15 - 12:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class LoadBalancerTest {

  @Test
  public void testSameStateAmongstAllInstances() {
    final LoadBalancer firstBalancer = new LoadBalancer();
    final LoadBalancer secondBalancer = new LoadBalancer();
    firstBalancer.addServer(new Server("localhost", 8085, 6));
    // Both should have the same number of servers.
    Assert.assertTrue(firstBalancer.getNoOfServers() == secondBalancer.getNoOfServers());
    // Both Should have the same LastServedId
    Assert.assertTrue(firstBalancer.getLastServedId() == secondBalancer.getLastServedId());
  }

  @Test
  public void testServe() {
    final Server server = mock(Server.class);

    final LoadBalancer loadBalancer = new LoadBalancer();
    loadBalancer.addServer(server);

    verifyZeroInteractions(server);

    final Request request = new Request("test");
    for (int i = 0; i < loadBalancer.getNoOfServers() * 2; i++) {
      loadBalancer.serverRequest(request);
    }

    verify(server, times(2)).serve(request);
    verifyNoMoreInteractions(server);

  }

}
