# camel-ibmmq-poc

1. Docker Pull IBM MQ image
   cmd : docker pull ibmcom/mq:latest
2. Create mount 
   cmd : docker volume create qm1data
3. Run container
   cmd : docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest
4. Check MQ version
   cmd : dspmqver
   
