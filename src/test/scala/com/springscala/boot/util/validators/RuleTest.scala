package com.springscala.boot.util.validators

import org.scalatest.FunSpec
import org.scalatest.PrivateMethodTester._
import org.scalatest.mock.MockitoSugar

class RuleTest extends FunSpec with MockitoSugar {

  object RuleMock extends Rule {
    override def isValid(v: Any) = true

    override def errorMessage = "test"
  }

  describe("isEmpty") {
    val isEmpty = PrivateMethod[Boolean]('isEmpty)

    it("should return true") {
      val resultEmptyString = RuleMock invokePrivate isEmpty("")
      assert(resultEmptyString === true)

      // @TODO: privateMethodTester is buggy with null param
      //      val resultNull = RuleMock invokePrivate isEmpty(null)
      //      assert(resultNull === true)
    }

    it("should return false") {
      val resultString = RuleMock invokePrivate isEmpty("test")
      assert(resultString === false)
    }
  }

  describe("isNumeric") {
    val isNumeric = PrivateMethod[Boolean]('isNumeric)

    it("should return true") {
      val resultNumericString = RuleMock invokePrivate isNumeric("12")
      assert(resultNumericString === true)

      val resultNumericInt = RuleMock invokePrivate isNumeric(12)
      assert(resultNumericInt === true)

      val resultNumericWithPrecision = RuleMock invokePrivate isNumeric(12.12)
      assert(resultNumericWithPrecision === true)
    }

    it("should return false") {
      val resultString = RuleMock invokePrivate isNumeric("test")
      assert(resultString === false)
    }
  }

  describe("isDouble") {
    val isDouble = PrivateMethod[Boolean]('isDouble)

    it("should return true") {
      val resultDouble = RuleMock invokePrivate isDouble(12.12)
      assert(resultDouble === true)

      val resultInt = RuleMock invokePrivate isDouble(12)
      assert(resultInt === true)
    }

    it("should return false") {
      val resultString = RuleMock invokePrivate isDouble("test")
      assert(resultString === false)
    }
  }
}
