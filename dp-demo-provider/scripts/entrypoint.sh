#!/bin/bash

cmd='java '

if [[ -n SW_SERVICE_NAME && -n SW_SERVICE_ADDR ]]; then
    cmd=${cmd}'
        -javaagent:agent/skywalking/skywalking-agent.jar
        -Dskywalking.agent.service_name='${SW_SERVICE_NAME:-project-center}'
        -Dskywalking.collector.backend_service='${SW_SERVICE_ADDR}
fi

    
if [[ -n ${DEBUG} ]]; then
    cmd=${cmd}'
        -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=9999
    '
fi

cmd=${cmd}'
            -cp resources/:classes/:libs/*
                com.kennywgx.StartupApplication $@
'

echo ${cmd}
eval ${cmd}


