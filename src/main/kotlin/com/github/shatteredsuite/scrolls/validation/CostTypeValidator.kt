package com.github.shatteredsuite.scrolls.validation

import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.core.validation.Validator
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostType

object CostTypeValidator : Validator<CostType> {
    override fun validate(param: String): CostType = ShatteredScrolls.getInstance().costTypes()[param]
            ?: throw ArgumentValidationException("Could not find a CostType with id ${param}.",
                    ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-cost-type",
                    param, ShatteredScrolls.getInstance().costTypes().all.joinToString { it.id })
}