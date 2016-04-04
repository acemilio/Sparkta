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

package com.stratio.models.benchmark.generator.models

import java.util.UUID

import com.stratio.benchmark.generator.models.{BaseModel, CommonData}

case class DefaultRawModel(order_id: String,
  timestamp: String,
  client_id: Integer,
  latitude: Double,
  longitude: Double,
  payment_method: String,
  credit_card: String,
  shopping_center: String,
  employee: Integer) extends BaseModel {
}

object DefaultRawModel extends CommonData {

  def apply(): DefaultRawModel = {
    val id = UUID.randomUUID.toString
    val timestamp = generateTimestamp
    val clientId = generateRandomInt(Range_client_id._1, Range_client_id._2)
    val latitude = clientIdGeo.get(clientId).get._1
    val longitude = clientIdGeo.get(clientId).get._2
    val paymentMethod = generatePaymentMethod()
    val creditCard = clientIdCreditCard.get(clientId).get
    val shoppingCenter = generateShoppingCenter()
    val employee = generateRandomInt(Range_employee._1, Range_employee._2)

    DefaultRawModel(
      id,
      timestamp,
      clientId,
      latitude,
      longitude,
      paymentMethod,
      creditCard,
      shoppingCenter,
      employee)
  }
}
