package com.github.shatteredsuite.scrolls.data.scroll.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.ext.placeholders

class VersionCommand(val instance: ShatteredScrolls, parent: BaseCommand) : LeafCommand(instance, parent, "load", "shatteredscrolls.load", "command.load") {
    init {
        addAlias("v")
    }
    override fun execute(ctx: CommandContext) {
        val updateStatus: String =
                if (instance.isUpdateAvailable) {
                    instance.messenger.getMessage("update-available", mapOf("version" to instance.latestVersion))
                }
                else {
                    instance.messenger.getMessage("up-to-date", null)
                }
        ctx.contextMessages["update-status"] = updateStatus
        ctx.contextMessages.putAll(instance.description.placeholders)
        ctx.messenger.sendMessage(ctx.sender, "version", ctx.contextMessages, true)
    }
}