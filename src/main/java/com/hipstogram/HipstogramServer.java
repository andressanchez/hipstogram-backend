/**
 *  Copyright 2014 Andrés Sánchez Pascual
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hipstogram;

import com.hipstogram.context.config.sections.ServerSection;
import com.hipstogram.rest.*;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HipstogramServer
{
    private ResteasyDeployment resteasyDeployment;
    private NettyJaxrsServer netty;

    public HipstogramServer(ServerSection serverSection)
    {
        this.resteasyDeployment = new ResteasyDeployment();
        resteasyDeployment.setResources(getCreatedResources());
        netty = new NettyJaxrsServer();
        netty.setDeployment(resteasyDeployment);
        netty.setPort(serverSection.port);
    }

    public List<Object> getCreatedResources()
    {
        List<Object> resources = new ArrayList<Object>();
        resources.add(new HelloWorldResource());
        resources.add(new EventsResource());
        resources.add(new HistogramResource());
        return resources;
    }

    public void start() {
        netty.start();
    }
}
