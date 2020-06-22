package com.github.shatteredsuite.scrolls.data.scroll.commands

import com.github.shatteredsuite.core.commands.WrappedCommand
import com.github.shatteredsuite.scrolls.ShatteredScrolls

class WarpCommand(instance: ShatteredScrolls?, parent: BaseCommand?) : WrappedCommand(instance, parent, "warp", "shatteredscrolls.command.warp", "command.warp") 