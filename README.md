# TotalFreedomMod
[![Maven-Build](https://github.com/AtlasMediaGroup/TotalFreedomMod/actions/workflows/maven.yml/badge.svg)](https://github.com/AtlasMediaGroup/TotalFreedomMod/actions/workflows/maven.yml) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/29c0f964da304666bd654bc7b1d556db)](https://www.codacy.com/gh/AtlasMediaGroup/TotalFreedomMod/dashboard) [![CodeQL](https://github.com/AtlasMediaGroup/TotalFreedomMod/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/AtlasMediaGroup/TotalFreedomMod/actions/workflows/codeql-analysis.yml) [![Discord](https://img.shields.io/discord/769659653096472627?label=Discord&logo=discord)](https://discord.com/invite/PW4savJR9a)

TotalFreedomMod (TFM) is a Spigot server plugin, designed primarily to support the [TotalFreedom Minecraft Server](https://totalfreedom.me/). However, you are more than welcome to adapt the source for your own server.

TotalFreedomMod consists of over 175 custom-coded commands, and a huge variety of unique features. For the better part of a decade, TFM has been trusted to provide TF with the administration tools, player utilities, community integration, and fun surprises that define the TotalFreedom experience. TFM is always evolving to fulfill the community's wants and needs.

Originally coded by Steven Lawson (Madgeek1450) and Jerom van der Sar (Prozza), TotalFreedomMod is a community effort, with a dedicated and passionate developer base.

## Contributing
Please see [CONTRIBUTING.md](CONTRIBUTING.md) if you are interested in developing TotalFreedomMod. Pull requests welcome!

Please see [LICENSE.md](LICENSE.md) for information on how TotalFreedomMod is licensed.

Please see [SECURITY.md](SECURITY.md) for information on our security policy and how to report an issue.

## Compiling
1) Clone this repository. If you would rather not use Git on the command line, GitHub Desktop [(Windows/macOS)](https://desktop.github.com/) [(Linux)](https://github.com/shiftkey/desktop) makes contributing easy.
2) You will need an [IDE](https://en.wikipedia.org/wiki/Comparison_of_integrated_development_environments#Java) for developing plugins. This plugin is built using Maven. Your IDE may include it - if not, you'll need to [manually install](https://maven.apache.org/install.html) it.
3) Install the latest [OpenJDK 11 (LTS)](https://adoptopenjdk.net/) and configure your IDE to use it.

> **Note:** As of an upcoming update, TotalFreedomMod will require JDK 17 or newer going forward.

4) If one was not automatically created for you, create a new Maven Configuration to run `mvn clean package`
5) Run your Maven configuration. If all goes well, your compiled jar should appear in the `target` directory.
  
If you'd like to start submitting pull requests, please make sure to read  [CONTRIBUTING.md](CONTRIBUTING.md)!
