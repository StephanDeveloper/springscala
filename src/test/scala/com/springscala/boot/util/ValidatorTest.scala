package com.springscala.boot.util

import com.springscala.boot.util.validators.Validator
import org.scalatest.FunSpec

class ValidatorTest extends FunSpec {

  describe("Validator") {
    val validator = new Validator

    describe("NotNull") {
      it("should return false") {
        assert(validator.NotNull.isValid(null) === false, "not null test of Nil")
      }

      it("should return true") {
        assert(validator.NotNull.isValid("test") === true)
      }
    }

    describe("Minlength") {
      it("should return true if length/size >= min") {
        assert(validator.Minlength(2).isValid("test") === true, "test.length >= 2")
        assert(validator.Minlength(2).isValid(List(1,2,3)) === true, "List(1,2,3).size >= 2")
      }
    }
  }
}
