#!/bin/bash
# script usage
#./scripts/psql_docker.sh start|stop|create [db_username][db_password]

enter_cmd=$1
db_username=$2
db_password=$3

#if user enter create option
if [ "$1" == "create" ] && [ "$#" = "3" ];
then
	#check whether container already created
	if [ "$(docker ps -f name= "jrvs-psql" | wc -l)" = "2" ];
	then
		echo "container is already created"
       	 	exit 1
		
	fi

        docker volume create pgdata 

	#create container
	if [ "$(docker container ls -a -f name= "jrvs-psql" | wc -l)" != "2" ];
   	then 
		docker run --name jrvs-psql -e POSTGRES_USER=${db_username} -e POSTGRES_PASSWORD=${db_password} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
		echo "container created"
		exit 0
	fi



	

fi


#if user didn't enter username or password
if [ "$1" == "create" ] && [ "$#"!=3 ];
then 
	echo "Invalid argument, please enter username and password"
        exit 1
fi


#if user enter start option 
if [ "$1" == "start" ]; 
then 
    #start docker 
    	systemctl status docker && systemctl start docker
    	echo "docker is running"
    #check if container is created  
    	if [ "$(docker container ls -a | egrep "jrvs-psql$" | wc -l)" == 0 ];
   	then 
		echo "container is not created, please enter create first"
		exit 1
	else
		#if container is created, run container 
		docker container start jrvs-psql
       	 	echo "container is running"
       		exit 0
	fi
fi


#if user enter stop option 
if [ "$1" == "stop" ];
then 
    	#stop docker 
    	systemctl status docker && docker stop jrvs-psql
	echo "docker stop"
	exit 0
fi




#if user enter wrong argument 
if [ "$1" != "create" ] && [ "$1" != "start" ] && [ "$1" != "stop" ];
then 
	echo "Invalid argument, argument shoule be create|start|stop"
    	exit 1
fi





    
	


	





