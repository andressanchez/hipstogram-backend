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

package com.hipstogram.context.config;

import com.hipstogram.context.config.sections.CassandraSection;
import com.hipstogram.context.config.sections.KafkaSection;
import com.hipstogram.context.config.sections.ServerSection;
import com.hipstogram.context.config.sections.ZookeeperSection;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;

import java.io.File;

/**
 * Config reads the configuration file and create an object for each section.
 * @author Andrés Sánchez
 */
public class Config
{
    private KafkaSection kafkaSection;
    private ServerSection serverSection;
    private ZookeeperSection zookeeperSection;
    private CassandraSection cassandraSection;

    public Config(File configFile, File... otherConfigFile) throws ConfigurationException {
        HierarchicalINIConfiguration iniConfig = new HierarchicalINIConfiguration(configFile);
        this.kafkaSection = new KafkaSection(iniConfig.getSection("kafka"));
        this.serverSection = new ServerSection(iniConfig.getSection("server"));
        this.zookeeperSection = new ZookeeperSection(iniConfig.getSection("zookeeper"));
        this.cassandraSection = new CassandraSection(iniConfig.getSection("cassandra"));
    }

    public KafkaSection getKafkaSection() { return kafkaSection; }

    public ServerSection getServerSection() {
        return serverSection;
    }

    public ZookeeperSection getZookeeperSection() {
        return zookeeperSection;
    }

    public CassandraSection getCassandraSection() {
        return cassandraSection;
    }
}
