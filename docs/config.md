# Plugin Configuration

## config.json
Default `config.json`
```json
{
  "cancelMode": "UNBIND",
  "defaultType": "BindingScroll",
  "safetyCheck": false,
  "cooldown": 1000,
  "allowedWorlds": [
    "world",
    "world_nether",
    "world_the_end"
  ]
}
```

This is an automatically generated file based on your server's setup, and it contains a few of the most important 
options.

**cancelMode** determines what happens when a scroll is found that we can't figure out what to do with. Normally this 
means it's bound to a location that doesn't exist anymore, or a the binding type that it used to have no longer exists.
If someone tries to use one of these scrolls, it will reference the **defaultType**.

**defaultType** determines what type scrolls should inherit their binding data from if they can't be found, such as in
the above circumstances. This references a type name in `scrolls.json`.

**safetyCheck** is currently not implemented, but will allow locations to be checked for dangerous blocks before a 
teleport is done, optionally with a search radius for a safe location.

**cooldown** determines how long someone has to wait between uses of scrolls. This prevents people from accidentally 
using multiple charges on one scroll.

**allowedWorlds** allows for a whitelist to be set up where scrolls can be used. 

## scrolls.json
An expanded version of the default `scrolls.json`:
```json
[
  {
    "id": "BindingScroll",
    "name": "Unbound Scroll",
    "material": "PAPER",
    "bindingData": {
      "type": "unbound",
      "data": {}
    },
    "displays": {
      "unbound":  {
        "name":  "Unbound Scroll",
        "preserveName":  false,
        "lore":  [
          "\u0026f%charges% \u00267charges.",
          "\u00267Right click to bind to your location."
        ],
        "glow":  false,
        "customModelData":  2
      },
      "location":  {
        "name":  "Teleportation Scroll",
        "preserveName":  false,
        "lore":  [
          "\u00267It goes to \u0026f%x% %y% %z% \u00267 in \u0026f%world%\u00267.",
          "\u0026f%charges% \u00267charges.",
          "\u00267Right click to teleport."
        ],
        "glow":  false,
        "customModelData":  4
      },
      "warp":  {
        "name":  "Warp Scroll",
        "preserveName":  false,
        "lore":  [
          "\u0026f%charges% \u00267charges.",
          "\u00267Right click to warp to \u0026f%warp%\u00267."
        ],
        "glow":  false,
        "customModelData":  2
      }
    },
    "crafting":  {
      "recipe":  {
        "items":  [
          " E ",
          "EPE",
          " E "
        ],
        "mapping":  {
          "P":  "PAPER",
          "E":  "ENDER_PEARL"
        }
      },
      "repairMaterial":  "ENDER_PEARL",
      "repairAmount":  2,
      "craftAmount":  1
    },
    "cost":  {
      "type":  "none",
      "data":  {}
    },
    "infinite":  false,
    "defaultCharges":  5
  },
  {
    "id":  "TownScroll",
    "name":  "Unbound Scroll",
    "material":  "PAPER",
    "bindingData":  {
      "type":  "warp",
      "data":  {
        "warp-id":  "town"
      }
    },
    "displays":  {
      "unbound":  {
        "name":  "Unbound Scroll",
        "preserveName":  false,
        "lore":  [
          "\u0026f%charges% \u00267charges.",
          "\u00267Right click to bind to your location."
        ],
        "glow":  false,
        "customModelData":  2
      },
      "location":  {
        "name":  "Teleportation Scroll",
        "preserveName":  false,
        "lore":  [
          "\u00267It goes to \u0026f%x% %y% %z% \u00267 in \u0026f%world%\u00267.",
          "\u0026f%charges% \u00267charges.",
          "\u00267Right click to teleport."
        ],
        "glow":  false,
        "customModelData":  4
      },
      "warp":  {
        "name":  "Warp Scroll",
        "preserveName":  false,
        "lore":  [
          "\u0026f%charges% \u00267charges.",
          "\u00267Right click to warp to \u0026f%warp%\u00267."
        ],
        "glow":  false,
        "customModelData":  2
      }
    },
    "crafting":  {
      "recipe":  {
        "items":  [
          " S ",
          "SPS",
          " S "
        ],
        "mapping":  {
          "P":  "PAPER",
          "S":  "STONE"
        }
      },
      "repairMaterial":  "COBBLESTONE",
      "repairAmount":  1,
      "craftAmount":  1
    },
    "cost":  {
      "type":  "none",
      "data":  {}
    },
    "infinite":  false,
    "defaultCharges":  5
  }
]
```

Here we define a list of scroll types that players can use. There is a collection of settings attached to these.
### General Settings
**id** is just a way to reference this scroll in commands and the config.

**name** is the name of the scroll that's shown in messages.

**material** is the item that the scroll should be. 

**infinite** determines if charges are consumed when this scroll is created under default conditions (crafting,
commands without parameters, etc.)

**defaultCharges** determines how many charges are in this scroll when created under default conditions.

### Binding Data
Binding data determines where this scroll will go when created under default conditions. This can either be a location,
unbound, or a warp. I recommend using the commands in-game to edit and create these, as it's much more user friendly and 
less likely to break between updates.

### Displays
Displays determines how this scroll appears based on where it's bound. Placeholders vary by how the scroll is bound.
I also recommend editing these using the commands in-game.

### Cost
Cost determines if there are additional consequences of using this scroll.


### Crafting
Crafting determines the way that this scroll can be acquired without using commands.

**items** determines the shape of the recipe, based on the ingredients in the mapping.

**mapping** determines which item goes to which in the recipe.

**repairMaterial** determines which item it can be repaired with, if any.

**craftAmount** determines how many scrolls should be created by this recipe.

**repairAmount** determines how many charges are restored by each repair item it's crafted with.

## warps.json
`warps.json` is an automatically generated file, and generally should not be edited by hand. This is a place where warps
created and managed by this plugin are stored. Most functions on these warps can be accomplished in-game without editing 
the file.

## messages.yml
This is where most of the text from the plugin comes from. An always-up-to-date version can be found on the github or in 
the jar, and it loads messages first from the jar, then overwrites them from a version of the messages file in the plugin 
folder.