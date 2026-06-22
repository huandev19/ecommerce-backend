#!/bin/bash
echo "Đang tiến hành build project (bỏ qua bước test)..."
./gradlew clean build -x test

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
