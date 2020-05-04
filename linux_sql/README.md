# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the master branch after Team Code Team.
## **Introduction**

The Jarvis Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. These servers are internally connected through a switch and able to communicate through internal IPv4 addresses.

- Switch vs router

The LCA team needs to record the hardware specifications of each node and monitor node resource usages (e.g. CPU/Memory) in realtime (see appendix A). The collected data should be stored in an RDBMS database. LCA team will use the data to generate some reports for future resource planning purposes (e.g. add/remove servers).
As a developer, you and your dev team are responsible to design and implement a [MVP](https://en.wikipedia.org/wiki/Minimum_viable_product) that helps the LCA team to meet their business needs. You and your team will also need to demonstrate the MVP at the end of the project.

**Note**: There are a lot of commercial cluster monitoring solutions out there. This is a practice project that helps you understand Linux and RDBMS. In reality, you will use a vendor solution.

## Scope

Since it's an MVP, you are allowed to test your program on a single machine instead of a Linux cluster. However, your program should work in a cluster environment assuming connection and firewalls are set up properly (done by other teams).

You will need to implement the program using bash scripts and the PostgreSQL database.

You will write SQL queries to answer some business questions 
(e.g. `show average memory usage in percentage over 1 mins interval for each node`).

## **Deliverables**

1. Manage your source code in the provided Github repo (ask your instructor for a repo and see [Git, Github, GitFlow Guidelines](https://www.notion.so/Git-Github-GitFlow-Guidelines-4254192fe01e41bbbb2cce3f85185cda) ) 
    - Bash scripts
    - SQL queries
    - README.md

## **Architecture**
- The `bash agent` gathers server usage data, and then insert into the psql instance. The `agent` will be installed on every host/server/node. The `agent` consists of two bash scripts
    - [ ]  `host_info.sh` collects the host hardware info and insert it into the database. It will be run only once at the installation time.
    - `host_usage.sh` collects the current host usage (CPU and Memory) and then insert into the database. It will be triggered by the `crontab` job every minute.
