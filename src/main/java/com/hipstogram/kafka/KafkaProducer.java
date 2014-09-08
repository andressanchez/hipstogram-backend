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

package com.hipstogram.kafka;

import com.google.gson.Gson;
import com.hipstogram.context.HipstogramContext;
import com.hipstogram.context.config.sections.KafkaSection;
import com.hipstogram.context.config.sections.ZookeeperSection;
import com.hipstogram.model.Event;
import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Kafka message producer
 * @author Andres Sanchez
 */
public class KafkaProducer
{
    private static KafkaProducer instance; // Singleton
    private static final String TOPIC = "topic-1";

    private ZookeeperSection zookeeperConfig;
    private KafkaSection kafkaConfig;
    private Producer<String, String> producer;
    private Gson gson;

    /**
     *
     */
    private KafkaProducer()
    {
        zookeeperConfig = HipstogramContext.getInstance().getConfiguration().getZookeeperSection();
        kafkaConfig = HipstogramContext.getInstance().getConfiguration().getKafkaSection();
        gson = new Gson();

        Properties props = new Properties();
        props.put("zk.connect", zookeeperConfig.host + ":" + zookeeperConfig.port);
        props.put("metadata.broker.list", kafkaConfig.host + ":" + kafkaConfig.port);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<String, String>(config);
    }

    /**
     *
     * @param events
     * @return
     */
    public boolean produce(Event[] events)
    {
        List<KeyedMessage<String, String>> keyedMessages = new ArrayList<KeyedMessage<String, String>>();

        for (Event e: events)
            keyedMessages.add(new KeyedMessage<String, String>(TOPIC, null, gson.toJson(e)));

        producer.send(keyedMessages);
        return true;
    }

    /**
     *
     * @return
     */
    public static KafkaProducer getInstance()
    {
        if (instance == null) instance = new KafkaProducer();
        return instance;
    }
}
