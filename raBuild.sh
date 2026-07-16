#!/bin/bash

# === Kiểm tra và giải phóng port nếu đang được sử dụng ===
APP_PORT=8080

# Tìm PID của tiến trình đang chiếm port
PID_USING_PORT=$(lsof -ti :$APP_PORT 2>/dev/null)

if [ -n "$PID_USING_PORT" ]; then
    echo "⚠️  Port $APP_PORT đang được sử dụng bởi PID: $PID_USING_PORT"
    echo "🔄 Đang dừng tiến trình cũ..."
    kill -15 $PID_USING_PORT 2>/dev/null
    sleep 2
    
    # Kiểm tra lại nếu tiến trình vẫn còn sống, force kill
    if kill -0 $PID_USING_PORT 2>/dev/null; then
        echo "⚠️  Tiến trình vẫn chưa dừng, đang force kill..."
        kill -9 $PID_USING_PORT 2>/dev/null
        sleep 1
    fi
    
    echo "✅ Đã giải phóng port $APP_PORT"
fi

echo "Đang tiến hành build project (bỏ qua bước test)..."
./gradlew clean build -x test --no-parallel

if [ $? -eq 0 ]; then
    echo "✅ Build thành công!"
    JAR_FILE=$(find build/libs -name "*.jar" ! -name "*plain.jar" | head -n 1)
    
    if [ -n "$JAR_FILE" ]; then
        echo "🚀 Đang chạy ứng dụng từ file: $JAR_FILE"
        java -jar "$JAR_FILE" &
        PID=$!
        echo "📌 Đã lưu PID ứng dụng: $PID"
        
        # Bắt sự kiện Ctrl+C (SIGINT) hoặc kill (SIGTERM)
        cleanup() {
            echo ""
            echo "🛑 Đã nhận tín hiệu dừng! Đang kill PID: $PID..."
            kill -9 $PID 2>/dev/null
            echo "✅ Đã dừng ứng dụng."
            exit 0
        }
        
        trap cleanup SIGINT SIGTERM
        
        # Đợi tiến trình hoàn thành
        wait $PID
    else
        echo "❌ Lỗi: Không tìm thấy file JAR nào trong thư mục build/libs/."
    fi
else
    echo "❌ Build thất bại! Vui lòng kiểm tra lại code."
    exit 1
fi