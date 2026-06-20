#!/bin/bash
echo "Đang tiến hành build project (bỏ qua bước test)..."
./gradlew clean build -x test

if [ $? -eq 0 ]; then
    echo "✅ Build thành công! File jar nằm ở thư mục build/libs/"
else
    echo "❌ Build thất bại! Vui lòng kiểm tra lại code."
fi
