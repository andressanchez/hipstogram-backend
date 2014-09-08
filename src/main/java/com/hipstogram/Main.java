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

import com.hipstogram.context.HipstogramContext;
import com.hipstogram.context.config.sections.ServerSection;

public class Main
{
    public static void main(String[] args)
    {
        ServerSection serverSection = HipstogramContext.getInstance().getConfiguration().getServerSection();
        HipstogramServer hipstogramServer = new HipstogramServer(serverSection);
        hipstogramServer.start();
        System.out.println("Server listening on port " + serverSection.port);
    }
}
