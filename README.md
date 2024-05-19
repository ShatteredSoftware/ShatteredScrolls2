<p align="center"><img src="https://raw.githubusercontent.com/ShatteredSuite/ShatteredScrolls2/master/header.png" alt=""/></p>

-----
<p align="center">
<a href="https://github.com/ShatteredSuite/ShatteredScrolls2/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/github/license/ShatteredSuite/ShatteredScrolls2?style=for-the-badge&logo=github" /></a>
<a href="https://github.com/ShatteredSuite/ShatteredScrolls2/issues"><img alt="GitHub Issues" src="https://img.shields.io/github/issues/ShatteredSuite/ShatteredScrolls2?style=for-the-badge&logo=github" /></a>
<img alt="GitHub Release Status" src="https://img.shields.io/github/actions/workflow/status/ShatteredSuite/ShatteredScrolls2/release.yml?label=Release&style=for-the-badge">
<img alt="GitHub Prerelease Status" src="https://img.shields.io/github/actions/workflow/status/ShatteredSuite/ShatteredScrolls2/prerelase.yml?label=Prerelease&style=for-the-badge">
<a href="https://github.com/ShatteredSuite/ShatteredScrolls2/releases"><img alt="GitHub Version" src="https://img.shields.io/github/release/ShatteredSuite/ShatteredScrolls2?label=Github%20Version&style=for-the-badge&logo=github" /></a>
<a href="https://discord.gg/zUbNX9t"><img alt="Discord" src="https://img.shields.io/badge/Get%20Help-On%20Discord-%237289DA?style=for-the-badge&logo=discord" /></a>
<a href="ko-fi.com/uberpilot"><img alt="Ko-Fi" src="https://img.shields.io/badge/Support-on%20Ko--fi-%23F16061?style=for-the-badge&logo=ko-fi" /></a>
</p>

## For Server Owners

### Installation
1. Get a reasonably up-to-date version of Bukkit (We've tested 1.15 and 1.16). We recommend 
[Tuinity](https://github.com/Spottedleaf/Tuinity) if possible, [Paper](https://papermc.io) otherwise.
2. Grab the latest version of 
[ShatteredCore](https://github.com/ShatteredSuite/ShatteredCore/releases/latest). If you're using a 
development version of this plugin, grab a development version of ShatteredCore 
[here](https://github.com/ShatteredSuite/ShatteredCore/releases/tag/latest). The jar you want is the 
one ending in `-dist.jar`. Drop it into your plugins folder.
3. Grab the latest version of this plugin 
[here](https://github.com/ShatteredSuite/ShatteredScrolls2/releases/latest). If you want a development 
version, look [here](https://github.com/ShatteredSuite/ShatteredScrolls2/releases/tag/latest). Drop it
into your plugins folder.
4. Stop your server if it was running. Start your server.
5. You're done!

## For Developers

### Installation

First, add this to your `pom.xml` or `build.gradle`
#### Maven
Add the following to your `pom.xml`:
```xml
<repositories> 
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- Recommended, but not required. -->
    <dependency>
        <groupId>com.github.ShatteredSuite</groupId>
        <artifactId>ShatteredCore</artifactId>
        <version>Tag</version>
    </dependency>
    <dependency>
        <groupId>com.github.ShatteredSuite</groupId>
        <artifactId>ShatteredScrolls2</artifactId>
        <version>Tag</version>
    </dependency>
</dependencies>
```

#### Gradle
Add the following to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ShatteredSuite:ShatteredCore:Tag' // Recommended, but not required.
    implementation 'com.github.ShatteredSuite:ShatteredScrolls2:Tag'
}
```

Next, add a dependency in your `plugin.yml`:
```yaml
depend:
- ShatteredScrolls
```

Finally, use any of the features you like!
