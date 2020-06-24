package com.github.shatteredsuite.scrolls.validation

import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.core.validation.Validator
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingType

object BindingTypeValidator : Validator<BindingType> {
    override fun validate(param: String): BindingType = ShatteredScrolls.getInstance().bindingTypes()[param]
        ?: throw ArgumentValidationException("Could not find a BindingType with id ${param}.",
                ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-binding-type",
                param, ShatteredScrolls.getInstance().bindingTypes().all.joinToString { it.id })
}
