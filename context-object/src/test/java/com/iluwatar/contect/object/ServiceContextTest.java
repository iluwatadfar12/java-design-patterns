/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.contect.object;

import com.iluwatar.context.object.LayerA;
import com.iluwatar.context.object.LayerB;
import com.iluwatar.context.object.LayerC;
import com.iluwatar.context.object.ServiceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Date: 10/24/2022 - 3:18
 *
 * @author Chak Chan
 */
public class ServiceContextTest {

  private static final String SERVICE = "SERVICE";

  private LayerA layerA;

  @BeforeEach
  void initiateLayerA() {
    this.layerA = new LayerA();
  }

  @Test
  void testSameContextPassedBetweenLayers() {
    ServiceContext context1 = layerA.getContext();
    var layerB = new LayerB(layerA);
    ServiceContext context2 = layerB.getContext();
    var layerC = new LayerC(layerB);
    ServiceContext context3 = layerC.getContext();

    assertSame(context1, context2);
    assertSame(context2, context3);
    assertSame(context3, context1);
  }

  @Test
  void testScopedDataPassedBetweenLayers() {
    layerA.addAccountInfo(SERVICE);
    var layerB = new LayerB(layerA);
    var layerC = new LayerC(layerB);
    layerC.addSearchInfo(SERVICE);
    ServiceContext context = layerC.getContext();

    assertEquals(SERVICE, context.getAccountService());
    assertNull(context.getSessionService());
    assertEquals(SERVICE, context.getSearchService());
  }

  @Test
  void testToString() {
    assertEquals(layerA.getContext().toString(),
        "ServiceContext(accountService=null, sessionService=null, searchService=null)");
    layerA.addAccountInfo(SERVICE);
    assertEquals(layerA.getContext().toString(),
        "ServiceContext(accountService=SERVICE, sessionService=null, searchService=null)");
    var layerB = new LayerB(layerA);
    layerB.addSessionInfo(SERVICE);
    var layerC = new LayerC(layerB);
    assertEquals(layerC.getContext().toString(),
        "ServiceContext(accountService=SERVICE, sessionService=SERVICE, searchService=null)");
  }
}
