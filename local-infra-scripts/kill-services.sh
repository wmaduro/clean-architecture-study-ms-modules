ps aux | grep 'clean-architecture-study-ms-modules/cas-ms' |  awk '{print $2}' | xargs kill -9
