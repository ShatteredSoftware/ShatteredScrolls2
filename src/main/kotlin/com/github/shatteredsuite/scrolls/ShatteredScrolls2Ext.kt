package com.github.shatteredsuite.scrolls

import com.github.shatteredsuite.scrolls.api.BindingTypeAPI
import com.github.shatteredsuite.scrolls.api.CostTypeAPI
import com.github.shatteredsuite.scrolls.api.ScrollAPI
import com.github.shatteredsuite.scrolls.api.WarpAPI
import com.github.shatteredsuite.scrolls.data.ScrollConfig

val ShatteredScrolls2.warps : WarpAPI
    get() = this.warps()

val ShatteredScrolls2.scrolls : ScrollAPI
    get() = this.scrolls()

val ShatteredScrolls2.costs : CostTypeAPI
    get() = this.costTypes()

val ShatteredScrolls2.bindings : BindingTypeAPI
    get() = this.bindingTypes()

val ShatteredScrolls2.config : ScrollConfig
    get() = this.config()