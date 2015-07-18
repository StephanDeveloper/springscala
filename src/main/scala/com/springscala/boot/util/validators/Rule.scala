package com.springscala.boot.util.validators

trait Rule {

  def isValid(value: Any): Boolean
  def errorMessage: String

  protected def isEmpty(v: Any) = v == null || v == ""

  protected def isNumeric(v: Any): Boolean = {
    !isEmpty(v) && """\A((-|\+)?[0-9]+(\.[0-9]+)?)+\z""".r.findFirstIn(v.toString).isDefined
  }

  protected def isDouble(v: Any): Boolean = isEmpty(v) || canBeConverted(v, v.toString.toDouble)

  protected def isFloat(v: Any): Boolean = isEmpty(v) || canBeConverted(v, v.toString.toFloat)

  protected def isInt(v: Any): Boolean = isEmpty(v) || canBeConverted(v, v.toString.toInt)

  protected def isLong(v: Any): Boolean = isEmpty(v) || canBeConverted(v, v.toString.toLong)

  private def canBeConverted(v: Any, callback: => Unit) = {
    try {
      callback
      true
    } catch {
      case e: NullPointerException => false
      case e: NumberFormatException => false
    }
  }
}
