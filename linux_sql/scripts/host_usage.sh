#!/bin/bash

#- assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#parse host hardware specifications
hostname=$(hostname -f)

memory_free=$(vmstat -t | awk 'NR==3 {print $4}' | xargs) 

cpu_idel=$(vmstat -t | tail -1 | awk '{print $15}' | xargs)

cpu_kernel=$(vmstat -d | awk 'NR ==3 {print $2}' | xargs)

disk_io=$(vmstat --unit M | awk 'NR==3 {print $9}' | xargs) 

disk_available=$(df -BM / | awk '{print $4}' | sed 's/[A-Za-z]*//g' | xargs)

timestamp=$(date '+%Y-%m-%d %H:%M:%S') 

#insert value in table host_usage

export PGPASSWORD=$psql_password
subquery="(SELECT id FROM host_info WHERE hostname='${hostname}')"

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user \
-c"INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idel, cpu_kernel, disk_io, disk_available)
VALUES ('${timestamp}',${subquery},'${memory_free}','${cpu_idel}',${cpu_kernel},${disk_io},${disk_available});"

exit 0
