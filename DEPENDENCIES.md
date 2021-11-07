# Dependency Structure & Repository Management

This GitHub project has been configured to pull all dependencies from the ATLAS Core Nexus Service, aside from exceptions noted in pom.xml. This significantly improves build times and ensures redundancy against a sudden loss of artifacts, which might otherwise lead to build failures. 

This document outlines which repositories are configured, their purposes, retention info, and anything else deemed relevant. 

There are 3 types of repositories in Maven you will need to be aware of:

| Repository Type  | Repository Description                                                                                                                                                                                                                                                                |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Hosted           | A Hosted Maven Repository serves artifacts where the primary source of said artifact is stored and published to the Nexus server. In our use-cases this will be for TotalFreedom / ATLAS Media Group Ltd.-owned projects.                                                             |
| Proxy            | A Proxy Maven Repository serves to proxy requests and cache the responses. This allows us to only provide Maven with a single endpoint, and to store all of the artifacts within the Nexus server, ensuring we don't suddenly have failing builds as a result of artifact deletion.   |
| Group            | A Group Maven Repository allows us to collate a set of hosted and/or proxied repositories into a single endpoint resolvable by Nexus. This allows us to provide the project with one URL, from which Nexus will handle all resolution from there.                                     |

## Current Group Repositories 

Currently there is a single group repository called totalfreedom-development which contains all other repositories documented on this page. This section will be expanded as appropriate in the future. 

## Current Hosted Repositories 

| Nexus Repository Name     | Repository ID | Repository URL                                                                | Retention Policy | Repository Purpose                                                                                                                             |
|---------------------------|---------------|-------------------------------------------------------------------------------|------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| totalfreedom-hosted-deps  | N/A           | https://nexus-01.core.atlas-media.co.uk/repository/totalfreedom-hosted-deps/  | Indefinitely     | Stores dependencies that we have had to manually restore due to previous repositories having deleted them. This is a **temporary** repository. |
| totalfreedom-pre-releases | N/A           | https://nexus-01.core.atlas-media.co.uk/repository/totalfreedom-pre-releases/ | Indefinitely     | Stores pre-release versions of our releases.                                                                                                   |
| totalfreedom-releases     | N/A           | https://nexus-01.core.atlas-media.co.uk/repository/totalfreedom-releases/     | Indefinitely     | Stores release versions of our code to be used by our projects or others'.                                                                     |

## Current Proxied Repositories 

| Nexus Repository Name    | Repository ID    | Repository URL                                                 | Retention Policy           |
|--------------------------|------------------|----------------------------------------------------------------|----------------------------|
| elmakers-maven           | elmakers-repo    | https://maven.elmakers.com/repository/                         | 90 days from last download |
| enginehub-maven          | enginehub        | https://maven.enginehub.org/repo/                              | 90 days from last download |
| essentialsx-repo         | essentialsx-repo | https://repo.essentialsx.net/releases/                         | 90 days from last download |
| jitpack-maven            | jitpack.io       | https://jitpack.io                                             | 90 days from last download |
| m2-dv8tion-maven         | dv8tion          | https://m2.dv8tion.net/releases/                               | 90 days from last download |
| maven-central            |                  | https://repo1.maven.org/maven2/                                | 90 days from last download |
| md_5-public-maven        | md_5-public      | https://repo.md-5.net/content/groups/public/                   | 90 days from last download |
| papermc-maven-public     | papermc          | https://papermc.io/repo/repository/maven-public/               | 90 days from last download |
| playrepo-maven           | playpro          | https://maven.playpro.com/                                     | 90 days from last download |
| sk89q-repo-maven         | sk89q-repo       | https://maven.sk89q.com/repo/                                  | 90 days from last download |
| sk89q-snapshots-maven    | sk89q-snapshots  | https://maven.sk89q.com/artifactory/repo                       | 90 days from last download |
| spigotmc-snapshots-maven | spigot-repo      | https://hub.spigotmc.org/nexus/content/repositories/snapshots/ | 90 days from last download |