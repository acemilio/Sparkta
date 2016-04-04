/**
 * Copyright (C) 2015 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.benchmark.generator.threads

import java.util.Date

import akka.event.slf4j.SLF4JLogging
import com.typesafe.config.Config
import kafka.producer.Producer
import org.json4s.native.Serialization._
import org.json4s.{DefaultFormats, Formats}

import com.stratio.benchmark.generator.models.{BaseModel, FiveNestedObjectsModel}
import com.stratio.benchmark.generator.runners.StoppedThreads
import com.stratio.kafka.benchmark.generator.kafka.KafkaProducer
import com.stratio.models.benchmark.generator.models.DefaultRawModel

class GeneratorThread(
  producer: Producer[String, String],
  timeout: Long,
  stoppedThreads: StoppedThreads,
  topic: String,
  config: Config)
  extends Runnable with SLF4JLogging {

  implicit val formats: Formats = DefaultFormats

  var numberOfEvents = 0

  def getModel(config: Config): BaseModel = {
    val model = config.getString("model")
    model match {
      case "DefaultRawModel" => DefaultRawModel.apply()
      case "FiveNestedObjectsModel" => FiveNestedObjectsModel.apply
      case _ => DefaultRawModel.apply()
    }
    //    val model = Class.forName(config.getString("model")).newInstance().asInstanceOf[BaseModel]
    //    val clazz = Class.forName(config.getString("model") ++ "$")
    //    clazz.getField("MODULE$").get(classOf[BaseModel]).asInstanceOf[BaseModel]
    //    DefaultRawModel.apply()
  }
  override def run: Unit = {
    val startTimeInMillis = new Date().getTime
    while (((startTimeInMillis + timeout) - new Date().getTime) > 0) {
      KafkaProducer.send(producer, topic, write(getModel(config)))
      numberOfEvents = numberOfEvents + 1
    }
    producer.close()

    stoppedThreads.incrementNumberOfEvents(numberOfEvents)
    stoppedThreads.incrementNumberOfThreads
  }
}