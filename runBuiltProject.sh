#!/bin/bash
JAR_FILE=$(find build/libs -name "*.jar" ! -name "*plain.jar" | head -n 1)

if [ -n "$JAR_FILE" ]; then
    echo "🚀 Đang chạy ứng dụng từ file: $JAR_FILE"
    java -jar "$JAR_FILE"
else
    echo "❌ Lỗi: Không tìm thấy file JAR nào trong thư mục build/libs/."
    echo "💡 Vui lòng chạy script ./buildProject.sh trước để đóng gói project!"
fi
