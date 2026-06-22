#!/bin/bash

# Clear the log file before every run
> error-build.log

# Bắt sự kiện Ctrl+C (SIGINT) và gửi tín hiệu tắt (SIGTERM) tới toàn bộ process, sau đó xoá nội dung file log
trap '> error-build.log; kill -TERM 0' SIGINT

# ---- Đọc server port từ application.yml ----
APP_YML="src/main/resources/application.yml"
SERVER_PORT=$(sed -n '/^server:/,/^[a-z]/p' "$APP_YML" | grep -E '^\s+port:\s+[0-9]+' | awk '{print $2}')

if [ -z "$SERVER_PORT" ]; then
    echo "WARNING: Không tìm thấy port trong $APP_YML, mặc định dùng port 8080"
    SERVER_PORT=8080
fi

echo "Port được cấu hình: $SERVER_PORT"

# ---- Kill tiến trình đang chiếm port (nếu có) ----
PID=$(lsof -ti:$SERVER_PORT 2>/dev/null)
if [ -n "$PID" ]; then
    echo "Port $SERVER_PORT đang bị chiếm bởi PID $PID. Đang kill..."
    kill -15 "$PID" 2>/dev/null
    sleep 2
    # Kiểm tra lại, nếu chưa chết thì force kill
    if kill -0 "$PID" 2>/dev/null; then
        echo "PID $PID chưa tắt, force kill..."
        kill -9 "$PID" 2>/dev/null
    fi
    echo "Đã giải phóng port $SERVER_PORT"
else
    echo "Port $SERVER_PORT đang trống."
fi

# ---- Run ----
if [ "$1" == "log" ]; then
    echo "Running with log redirection to error-build.log..."
    ./gradlew bootRun 2>&1 | tee error-build.log
else
    ./gradlew bootRun
fi
