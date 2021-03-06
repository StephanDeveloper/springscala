package com.springscala.boot.util

import com.springscala.boot.util.validators.Validator
import org.scalatest.FunSpec

class ValidatorTest extends FunSpec {

  describe("Validator") {
    val validator = new Validator

    describe("NotNull") {
      it("should return true") {
        assert(validator.NotNull.isValid("test") === true)
      }

      it("should return false") {
        assert(validator.NotNull.isValid(null) === false, "not null test of Nil")
      }
    }

    describe("Minlength") {
      it("should return true if length/size >= min") {
        assert(validator.Minlength(2).isValid("test") === true, "test.length >= 2")
        assert(validator.Minlength(2).isValid(List(1, 2, 3)) === true, "List(1,2,3).size >= 2")

        assert(validator.Minlength(2).errorMessage === "Value can not be shorter then 2")
      }

      it("should return false") {
        assert(validator.Minlength(2).isValid("t") === false)
        assert(validator.Minlength(2).isValid(List(1)) === false)
      }
    }

    describe("Maxlength") {
      it("should return true if length/size >= max") {
        assert(validator.Maxlength(3).isValid("day") === true, "day.length <= 3")
        assert(validator.Maxlength(2).isValid(List(1, 2)) === true, "List(1,2).size <= 2")

        assert(validator.Maxlength(2).errorMessage === "Value can not be longer than 2")
      }

      it("should return false") {
        assert(validator.Maxlength(2).isValid("test") === false)
        assert(validator.Maxlength(2).isValid(List(1,2,3)) === false)
      }
    }

  }
}
