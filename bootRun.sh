#!/bin/bash

# Clear the log file before every run
> error-build.log

# Bắt sự kiện Ctrl+C (SIGINT) và gửi tín hiệu tắt (SIGTERM) tới toàn bộ process, sau đó xoá nội dung file log
trap '> error-build.log; kill -TERM 0' SIGINT

if [ "$1" == "log" ]; then
    echo "Running with log redirection to error-build.log..."
    ./gradlew bootRun 2>&1 | tee error-build.log
else
    ./gradlew bootRun
fi
