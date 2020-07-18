#!/bin/sh

execute_file() {
	echo "Start the process!"
	java -jar handshaker.jar
}

if nc -zw1 github.com 443 && echo |openssl s_client -connect github.com:443 2>&1 |awk '
  handshake && $1 == "Verification" { if ($2=="OK") exit; exit 1 }
  $1 $2 == "SSLhandshake" { handshake = 1 }'
then
	if [ -e handshaker.jar ]
	then
	    execute_file
	else
	    curl https://raw.githubusercontent.com/nadeengamage/handshaker/master/build/handshaker.jar -o handshaker.jar
	    execute_file
	fi
fi
