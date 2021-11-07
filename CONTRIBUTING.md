# Contributing to TotalFreedomMod

> :warning: **Content Re-work In Progress**: We are currently re-working the content that makes up this page. Please see the legacy documentation for contributing to TotalFreedomMod here: [contributing guidelines](https://github.com/TotalFreedom/TotalFreedomMod/wiki/Contributing). 

## Dependency Management
We are aware that dependencies are ultimately inevitable and that at some point or another you will need to introduce new dependencies as part of writing code towards this repository, but we would ask that you consider the following prior to doing so:

1) Is the dependency actively maintained, ideally by someone with no relation to the TotalFreedom community?
2) Does the dependency already exist in a mirror or proxy repository that is supported by the project? If not, we will have to do some additional work.
3) Are there similar, better-supported dependencies out there that could fulfill the same purpose? 

There will be a separate page coming shortly which outlines the process for registering new repositories, as we would ask you do not add them into the pom.xml file due to the way Maven natively handles dependencies. 