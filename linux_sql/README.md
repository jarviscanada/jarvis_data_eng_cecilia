# Linux Cluster Monitoring Agent

## Introduction

The Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. These servers are internally connected through a switch and able to communicate through internal IPv4 addresses.The LCA team needs to record the hardware specifications of each node and monitor node resource usages (e.g. CPU/Memory) in realtime.

The collected data will be stored in an RDBMS database. LCA team will use the data to generate some reports for future resource planning purposes.

## Architecture & Design

- A `psql` instance is used to persist all the data
- The `bash agent` gathers server usage data, and then insert into the psql instance. The `agent` will be installed on every host/server/node. The `agent` consists of two bash scripts
    -  `host_info.sh` collects the host hardware info and insert it into the database. It will be run only once at the installation time.
    - `host_usage.sh` collects the current host usage (CPU and Memory) and then insert into the database. It will be triggered by the `crontab` job every minute.
    
## Usage
- psql_docker.sh usage: There are 3 input option for this, create,start and stop.
Create :./psql_docker.sh create [username][password]
Start: ./psql_docker.sh start 
Stop: ./psql_docker.sh stop

- ddl.sql usage: create datebase host_agent and two tables host_info and host_usage

- host_info.sh usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
eg: ./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"

-  host_usage.sh usage: bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
eg:bash scripts/host_usage.sh localhost 5432 host_agent postgres password

- queries.sql:get specific data from two tables

- In order to collect the usage information every minute, use crontab -e and enter the following code: * * * * * bash [path to host_usage.sh]/host_usage.sh "host name" "port number" "database name" "user name "password" >> [path to store log file]/host_usage.log. (eg:* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log) 

## Improvement
1. Create a script that containes all the commands in every step and can set up all the processes at once 
2. Add more nodes and insert more data information 
3. Add alter if the nodes didn't get a lot usage left
