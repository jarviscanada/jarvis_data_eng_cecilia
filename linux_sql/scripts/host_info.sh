#!/bin/bash

#- assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#save hostname to a variable
hostname=$(hostname -f)

#save number of CPU to a variable
lscpu_out=`lscpu`

#parse host hardware specifications
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk -F':' '{print $2}' | xargs)

cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk -F':' '{print $2}' | xargs)

cpu_model=$(echo "$lscpu_out" | egrep "^Model name:" | awk -F':' '{print $2}' | xargs)

cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz:" | awk -F ':' '{print $2}' | xargs)

l2_cache=$(echo "$lscpu_out" | egrep "^L2\scache:" | awk '{print $3}' | grep -o '[0-9]\+')

total_mem=$(grep MemTotal /proc/meminfo | awk '{print $2}')

timestamp=$(date '+%Y-%m-%d %H:%M:%S') 

#insert information 
export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user \
-c "INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp)
VALUES('${hostname}',${cpu_number},'${cpu_architecture}','${cpu_model}',${cpu_mhz}, ${l2_cache},${total_mem},'${timestamp}');"

exit 0
