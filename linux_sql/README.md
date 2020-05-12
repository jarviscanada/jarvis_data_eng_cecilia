# Linux Cluster Monitoring Agent

## Introduction

The Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. These servers are internally connected through a switch and able to communicate through internal IPv4 addresses.The LCA team needs to record the hardware specifications of each node and monitor node resource usages (e.g. CPU/Memory) in realtime.

The collected data will be stored in an RDBMS database. LCA team will use the data to generate some reports for future resource planning purposes.

## Architecture & Design

![](/images/design.png)

## Database & Table 
- `psql_docker.sh` - This script contains 3 basic functionality which are `create` new instance, `start` and `stop`containers to manage the PostgreSQL instance. 
- `ddl.sql` - This script create `host_agent` database and two tables `host_info` and `host_usage`
    -  `host_info.sh` collects the host hardware info and insert it into the database. It will be run only once at the installation time.
        * `id` - Unique ID number for each server, auto-increament
        * `hostname` - Name of host system
        * `cpu_number` - Host system core count of CPU
        * `cpu_architecture` - Host CPU architecture 
        * `cpu_model` - Host CPU model name 
        * `cpu_mhz` - Host CPU speed (in Mhz)
        * `L2_cache` - Host machine L2 cache size (in KB)
        * `total_mem` - Host machine total usable memory (in KB)
        * `timestamp` - Capture current time 
    - `host_usage.sh` collects the current host usage (CPU and Memory) and then insert into the database. It will be triggered by the `crontab` job every minute.
        * `timestamp` - Capture current time 
        * `host_id` - Unique primary key relating to host machine in host_info
        * `memory_free` - Total free memory when capture 
        * `cpu_idle` - Percentage of time spend when CPU load idling
        * `cpu_kernel` - Percentage of time spend when CPU load operating on kernel
        * `disk_io` - Number of current I/O operations in disk
        * `disk_available` - Amount of available disk space (in MB)
-`queries.sql` - Contain two Queries to help business select data information
    * Group hosts by CPU number and sort by their memory size in descending order
    * Average used memory in percentage over 5 mins interval for each host
        
    
## Usage
- `psql_docker.sh` usage: There are 3 input option for this, create,start and stop.
    * Create : `./psql_docker.sh create [username][password]`
    * Start: `./psql_docker.sh start `
    * Stop: `./psql_docker.sh stop`

- `ddl.sql` usage: create datebase host_agent and two tables host_info and host_usage

- `host_info.sh` usage: `./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
eg: ./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"`

-  `host_usage.sh` usage: `bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password`
eg:`bash scripts/host_usage.sh localhost 5432 host_agent postgres password`

- `queries.sql`:get specific data from two tables

- In order to collect the usage information every minute, use `crontab -e` and enter the following code: `* * * * * bash [path to host_usage.sh]/host_usage.sh "host name" "port number" "database name" "user name "password" >> [path to store log file]/host_usage.log`. (eg:`* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log`) 

## Improvement
1. Create a script that containes all the commands in every step and can set up all the processes at once 
2. Add more nodes and insert more data information 
3. Add alter if the nodes didn't get a lot usage left
