package com.github.shatteredsuite.scrolls.validation

import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.core.validation.Validator
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.warp.Warp

object WarpValidator : Validator<Warp> {
    override fun validate(param: String): Warp = ShatteredScrolls.getInstance().warps()[param]
            ?: throw ArgumentValidationException("Could not find a Warp with id ${param}.",
                    ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-warp",
                    param, ShatteredScrolls.getInstance().warps().all.joinToString { it.id })
}