package com.springscala.boot.util.validators

import scala.language.reflectiveCalls

class Validator {

  object NotNull extends Rule {
    override def isValid(v: Any) = v != null
    override def errorMessage = "Value is null"
  }

  case class Minlength(min: Int) extends Rule {
    override def isValid(v: Any) = isEmpty(v) || {
      hasSizeMethod(v).map(x => x.size >= min)
        .getOrElse(v.toString.length >= min)
    }
    override def errorMessage = s"Value can not be shorter then $min"
  }

  case class Maxlength(max: Int) extends Rule {
    override def isValid(v: Any) = isEmpty(v) || {
      hasSizeMethod(v).map(x => x.size <= max)
        .getOrElse(v.toString.length <= max)
    }
    override def errorMessage = s"Value can not be longer than $max"
  }

  private def hasSizeMethod(v: Any): Option[ {def size: Int}] = {
    // structural typing, a type with a size method but with no name
    val structuralType = v.asInstanceOf[ {def size: Int}]
    try {
      structuralType.size
      Option(structuralType)
    } catch {
      case e: NoSuchMethodException => None
    }
  }
}
