<p align="center">
  <img src="banner.svg" alt="Bump for DrakesCraft" width="100%">
</p>

# Bump for DrakesCraft

A maintained Java 21 port of Bump for the DrakesCraft Slimefun ecosystem on Paper/Purpur 1.21.11.

## Features

- Equipment appraisal with persistent quality and attribute data.
- Appraisal Instrument, Attribute Grindstone and identification tools.
- Magic weapons, utility tools and consumable items.
- Localized names, lore, menus and configurable sounds.
- Native compatibility with the repackaged DrakesCraft Slimefun core.
- No telemetry, automatic downloads or runtime update checks.

## Compatibility

| Component | Target |
| --- | --- |
| Java | 21 |
| Server | Paper/Purpur 1.21.11 |
| Slimefun | `slimefun-core:11.0-Drake-1.21.11-SNAPSHOT` |
| GuizhanLib | DrakesCraft port, shaded |
| SefiLib | DrakesCraft port, shaded |

The release JAR is self-contained. GuizhanLib and SefiLib are build-time ports and are shaded under Bump's namespace, so production does not need extra library plugins.

All Slimefun item identifiers are registered under the `BUMP_` prefix. Localization keeps the original keys while the namespace prevents collisions with other DrakesCraft addons.

## Build

```bash
./gradlew clean shadowJar
```

The artifact is generated under `build/libs/`.

## Player guide

The server-specific guide is available at [web.drakescraft.cl/guia-slimefun.html#bump](https://web.drakescraft.cl/guia-slimefun.html#bump).

## Credits and license

Bump was originally created by bxx2004 and maintained by the SlimefunGuguProject community. This port preserves the original GPL-3.0 license and credits while adapting the code to DrakesCraft's maintained runtime.
