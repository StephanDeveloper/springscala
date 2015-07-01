package com.springscala.boot.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonNode}

class OptionDeserializer[T] extends JsonDeserializer[Option[T]] {

  override def deserialize(jp: JsonParser, ctxt: DeserializationContext): Option[T] = {
    val node: JsonNode = jp.getCodec.readTree(jp)
    if(node.isNull) None else Some(node.textValue().asInstanceOf[T])
  }
}
