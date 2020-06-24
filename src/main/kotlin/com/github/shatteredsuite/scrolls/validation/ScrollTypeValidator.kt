package com.github.shatteredsuite.scrolls.validation

import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.core.validation.Validator
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType

object ScrollTypeValidator : Validator<ScrollType> {
    override fun validate(param: String): ScrollType = ShatteredScrolls.getInstance().scrolls()[param]
            ?: throw ArgumentValidationException("Could not find a ScrollType with id ${param}.",
                    ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-scroll",
                    param, ShatteredScrolls.getInstance().scrolls().all.joinToString { it.id })
}
