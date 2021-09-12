# Dependency Structure & Repository Management

This Github project has been configured to pull all dependencies (Other than where exceptions have been documented and approved into the pom.xml) from the ATLAS Core Nexus Service. This is to help improve our build times significantly and to ensure that artifacts are not removed and cause build failures in the long term. 

The purpose of this document is to outline what repositories are configured, their purpose, retention data and anything else that is deemed relevant. 

There are 3 types of repository in Maven you will need to be aware of and these are as follows:

| Repository Type: | Repository Description                                                                                                                                                                                                                                                                |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Hosted           | A Hosted Maven Repository serves artefacts where the primary source of said artefact is stored and published to the Nexus server. In our use-cases this will be for TotalFreedom / ATLAS Media Group Ltd owned projects.                                                              |
| Proxy            | A Proxy Maven Repository serves to proxy requests and cache the responses. This allows us to only provide Maven with a single end-point, and to store all of the artefacts within the nexus server, ensuring we don't suddenly have failing builds as a result of artefact deletion.  |
| Group            | A Group Maven Repository allows us to collate a set of hosted and/or proxied repositories into a single endpoint resolvable by Nexus, this allows us to provide the project with one URL and Nexus then will handle all resolution from there.                                        |

## Current Group Repositories 

Currently there is a single group repository called totalfreedom-development which contains all other repositories documented on this page. This section will be expanded as appropriate in the future. 

## Current Hosted Repositories 

| Nexus Repository Name     | Repository ID | Repository URL                                                                | Retention Policy | Repository Purpose                                                                                                                            |
|---------------------------|---------------|-------------------------------------------------------------------------------|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| totalfreedom-hosted-deps  | NaN           | https://nexus-01.core.atlas-media.co.uk/repository/totalfreedom-hosted-deps/  | Retain Indef     | Stores dependencies that we have had to manually restore due to previous repositories having deleted them. This is a **Temporary** Repository |
| totalfreedom-pre-releases | NaN           | https://nexus-01.core.atlas-media.co.uk/repository/totalfreedom-pre-releases/ | Retain Indef     | Stores pre-release versions of our releases.                                                                                                  |
| totalfreedom-releases     | NaN           | https://nexus-01.core.atlas-media.co.uk/repository/totalfreedom-releases/     | Retain Indef     | Stores release versions of our code to be used by our projects or others.                                                                     |

## Current Proxied Repositories 

| Nexus Repository Name    | Repository ID   | Repository URL                                                 | Retention Policy           |
|--------------------------|-----------------|----------------------------------------------------------------|----------------------------|
| elmakers-maven           | elmakers-repo   | https://maven.elmakers.com/repository/                         | 90 Days from last download |
| enginehub-maven          | enginehub       | https://maven.enginehub.org/repo/                              | 90 Days from last download |
| essentialsx-repo         | esentialsx-repo | https://repo.essentialsx.net/releases/                         | 90 Days from last download |
| jitpack-maven            | jitpack.io      | https://jitpack.io                                             | 90 Days from last download |
| m2-dv8tion-maven         | dv8tion         | https://m2.dv8tion.net/releases/                               | 90 Days from last download |
| maven-central            |                 | https://repo1.maven.org/maven2/                                | 90 Days from last download |
| md_5-public-maven        | md_5-public     | https://repo.md-5.net/content/groups/public/                   | 90 Days from last download |
| papermc-maven-public     | papermc         | https://papermc.io/repo/repository/maven-public/               | 90 Days from last download |
| playrepo-maven           | playpro         | https://maven.playpro.com/                                     | 90 Days from last download |
| sk89q-repo-maven         | sk89q-repo      | https://maven.sk89q.com/repo/                                  | 90 Days from last download |
| sk89q-snapshots-maven    | sk89q-snapshots | https://maven.sk89q.com/artifactory/repo                       | 90 Days from last download |
| spigotmc-snapshots-maven | spigot-repo     | https://hub.spigotmc.org/nexus/content/repositories/snapshots/ | 90 Days from last download |