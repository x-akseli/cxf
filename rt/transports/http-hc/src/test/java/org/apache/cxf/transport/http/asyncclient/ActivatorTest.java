/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.transport.http.asyncclient;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import org.apache.cxf.Bus;
import org.osgi.framework.BundleContext;

import org.easymock.EasyMock;
import org.junit.Test;

public class ActivatorTest {

    @Test
    public void testConduitConfigurerUpdates() throws NoSuchMethodException {
        Bus bus = EasyMock.createMock(Bus.class);
        AsyncHTTPConduitFactory conduitFactory = EasyMock.createMock(AsyncHTTPConduitFactory.class);
        Activator.ConduitConfigurer configurer =
            EasyMock.partialMockBuilder(Activator.ConduitConfigurer.class)
                .withConstructor(EasyMock.mock(BundleContext.class))
                .addMockedMethod(Activator.ConduitConfigurer.class.getMethod("getServices"))
                .createMock();
        Object[] buses = {bus};
        EasyMock.expect(configurer.getServices()).andReturn(buses);
        EasyMock.expect(bus.getExtension(AsyncHTTPConduitFactory.class)).andReturn(conduitFactory);
        Map<String, Object> properties = Collections.singletonMap("foo", "bar");
        conduitFactory.update(properties);
        EasyMock.replay(configurer, bus, conduitFactory);
        configurer.updated(new Hashtable<>(properties));
        EasyMock.verify(conduitFactory);
    }
}
