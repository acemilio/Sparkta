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

package com.stratio.benchmark.generator.models

import java.util.UUID
import scala.io.Source

case class FiveNestedObjectsModel(order_id: String,
  timestamp: String,
  client_id: Integer,
  city: City) extends BaseModel {
}

object FiveNestedObjectsModel extends CommonData {

  val Cities = Source.fromInputStream(
    this.getClass.getClassLoader.getResourceAsStream("cities.csv")).getLines().toSeq
  val Countries = Source.fromInputStream(
    this.getClass.getClassLoader.getResourceAsStream("countries.csv")).getLines().toSeq
  val Continents = Source.fromInputStream(
    this.getClass.getClassLoader.getResourceAsStream("continents.csv")).getLines().toSeq
  val Planets = Source.fromInputStream(
    this.getClass.getClassLoader.getResourceAsStream("planets.csv")).getLines().toSeq
  val Galaxies = Source.fromInputStream(
    this.getClass.getClassLoader.getResourceAsStream("galaxies.csv")).getLines().toSeq

  def apply(): FiveNestedObjectsModel = {
    val id = UUID.randomUUID.toString
    val timestamp = generateTimestamp
    val clientId = generateRandomInt(Range_client_id._1, Range_client_id._2)
    val galaxy = Galaxy(Galaxies(generateRandomInt(0, Galaxies.length - 1)))
    val planet = Planet(Planets(generateRandomInt(0, Planets.length - 1)), galaxy)
    val continent = Continent(Continents(generateRandomInt(0, Continents.length - 1)), planet)
    val country = Country(Countries(generateRandomInt(0, Countries.length - 1)), continent)
    val city = City(Cities(generateRandomInt(0, Cities.length - 1)), country)

    FiveNestedObjectsModel(
      id,
      timestamp,
      clientId,
      city)
  }
}


