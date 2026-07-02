# Hướng dẫn: Tạo Full Image Path cho Avatar URL

**Ngày tạo:** 2026-07-02 (UTC+7)
**Module:** Identity (UserAdmin)

---

## Mục tiêu

Xây dựng tính năng upload avatar cho UserAdmin lên MinIO/S3 và sinh ra full public URL của avatar.

Có 2 case:
- **Case 1 (Local):** Avatar lưu local, URL = `base-url` + relative path.
- **Case 2 (Cloud - MinIO/S3):** Avatar upload lên MinIO, URL = `public-url-prefix` + `/` + `bucket` + `/` + object key.

---

## Danh sách file cần tạo/sửa

| STT | File | Hành động |
|-----|------|-----------|
| 1 | `build.gradle` (root) | Sửa — Thêm dependency `io.minio:minio:8.5.7` |
| 2 | `src/main/resources/application.yml` | ✅ Đã có config `app.storage` (không cần sửa) |
| 3 | `src/main/resources/application-prod.yml` | Sửa — Bỏ fallback localhost ở MinIO section |
| 4 | `modules/identity/.../infrastructure/storage/StorageProperties.java` | Tạo mới |
| 5 | `modules/identity/.../infrastructure/config/MinioConfig.java` | Tạo mới |
| 6 | `modules/identity/.../infrastructure/storage/StorageService.java` | Tạo mới |
| 7 | `modules/identity/.../infrastructure/storage/MinioStorageService.java` | Tạo mới |
| 8 | `modules/identity/.../domain/entity/UserAdmin.java` | Sửa — Thay thế 2 method `getAvatarUrl()` và `getAvatarUrlS3()` |

---

## Bước 1: Thêm dependency MinIO vào `build.gradle` (root)

Mở file `build.gradle` ở thư mục gốc project.

Thêm dòng sau vào trong block `dependencies { ... }` (ví dụ sau dòng 53 `spring-boot-starter-mail`):

```groovy
implementation 'io.minio:minio:8.5.7'
```

---

## Bước 2: Sửa `application-prod.yml`

Mở `src/main/resources/application-prod.yml`. Sửa block `app.storage.minio` — **bỏ tất cả fallback** để production fail-fast nếu thiếu biến môi trường:

**Trước khi sửa:**
```yaml
app:
  storage:
    base-url: ${STORAGE_BASE_URL}
    minio:
      endpoint: ${MINIO_ENDPOINT:http://localhost:9000}
      access-key: ${MINIO_ACCESS_KEY:minioadmin}
      secret-key: ${MINIO_SECRET_KEY:minioadmin}
      bucket: ${MINIO_BUCKET:avatars}
      region: ${MINIO_REGION:us-east-1}
      public-url-prefix: ${MINIO_PUBLIC_URL:http://localhost:9000}
```

**Sau khi sửa:**
```yaml
app:
  storage:
    base-url: ${STORAGE_BASE_URL}
    minio:
      endpoint: ${MINIO_ENDPOINT}
      access-key: ${MINIO_ACCESS_KEY}
      secret-key: ${MINIO_SECRET_KEY}
      bucket: ${MINIO_BUCKET:avatars}
      region: ${MINIO_REGION:us-east-1}
      public-url-prefix: ${MINIO_PUBLIC_URL}
```

---

## Bước 3: Tạo `StorageProperties.java`

**Đường dẫn:**
`modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/storage/StorageProperties.java`

Tạo thư mục `storage` nếu chưa có trong `infrastructure`.

**Nội dung file:**

```java
package com.v8n.modules.identity.infrastructure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {

    /**
     * Base URL của ứng dụng (dùng cho case 1: local storage).
     * Ví dụ: http://localhost:8080
     */
    private String baseUrl;

    /**
     * Cấu hình MinIO / S3 (case 2: cloud storage).
     */
    private MinioProperties minio = new MinioProperties();

    @Getter
    @Setter
    public static class MinioProperties {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;
        private String region;
        private String publicUrlPrefix;
    }
}
```

**Giải thích:**
- `@ConfigurationProperties(prefix = "app.storage")` sẽ tự động map các giá trị từ `application.yml` vào object này.
- Lớp con `MinioProperties` map vào block `app.storage.minio`.

---

## Bước 4: Tạo `MinioConfig.java`

**Đường dẫn:**
`modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/config/MinioConfig.java`

**Nội dung file:**

```java
package com.v8n.modules.identity.infrastructure.config;

import com.v8n.modules.identity.infrastructure.storage.StorageProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    private final StorageProperties storageProperties;

    @Bean
    public MinioClient minioClient() {
        StorageProperties.MinioProperties minio = storageProperties.getMinio();
        return MinioClient.builder()
                .endpoint(minio.getEndpoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .region(minio.getRegion())
                .build();
    }
}
```

**Giải thích:**
- `@Bean` tạo ra `MinioClient` singleton để dùng trong toàn bộ app.
- Inject `StorageProperties` để lấy endpoint, access key, secret key, region.

---

## Bước 5: Tạo `StorageService.java` (Interface)

**Đường dẫn:**
`modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/storage/StorageService.java`

**Nội dung file:**

```java
package com.v8n.modules.identity.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface thống nhất cho việc lưu trữ file (avatar).
 * Hỗ trợ cả local storage và cloud storage (MinIO/S3).
 */
public interface StorageService {

    /**
     * Upload file và trả về object key (relative path).
     *
     * @param file   file được upload
     * @param folder thư mục đích (ví dụ: "avatars")
     * @return object key (ví dụ: "avatars/uuid-xxx.jpg")
     */
    String upload(MultipartFile file, String folder);

    /**
     * Trả về full public URL từ object key.
     *
     * @param objectKey key của object đã upload (ví dụ: "avatars/uuid-xxx.jpg")
     * @return full public URL (ví dụ: "http://localhost:9000/avatars/avatars/uuid-xxx.jpg")
     */
    String getPublicUrl(String objectKey);

    /**
     * Trả về presigned URL (có thời hạn) để truy cập object private.
     *
     * @param objectKey key của object
     * @param expirySeconds thời gian hết hạn (giây)
     * @return presigned URL
     */
    String getPresignedUrl(String objectKey, int expirySeconds);

    /**
     * Xóa object khỏi storage.
     *
     * @param objectKey key của object cần xóa
     */
    void delete(String objectKey);
}
```

---

## Bước 6: Tạo `MinioStorageService.java` (Implement)

**Đường dẫn:**
`modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/storage/MinioStorageService.java`

**Nội dung file:**

```java
package com.v8n.modules.identity.infrastructure.storage;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    @Override
    public String upload(MultipartFile file, String folder) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String objectName = folder + "/" + UUID.randomUUID() + extension;

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(storageProperties.getMinio().getBucket())
                                .object(objectName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            log.info("Uploaded file to MinIO: {}", objectName);
            return objectName;
        } catch (Exception e) {
            log.error("Failed to upload file to MinIO", e);
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public String getPublicUrl(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            return null;
        }

        // Nếu đã là full URL thì trả về nguyên bản
        if (objectKey.startsWith("http://") || objectKey.startsWith("https://")) {
            return objectKey;
        }

        StorageProperties.MinioProperties minio = storageProperties.getMinio();
        String prefix = minio.getPublicUrlPrefix();

        // Bỏ trailing slash nếu có
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }

        return prefix + "/" + minio.getBucket() + "/" + objectKey;
    }

    @Override
    public String getPresignedUrl(String objectKey, int expirySeconds) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(storageProperties.getMinio().getBucket())
                            .object(objectKey)
                            .expiry(expirySeconds, TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for: {}", objectKey, e);
            throw new RuntimeException("Failed to generate presigned URL", e);
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(storageProperties.getMinio().getBucket())
                            .object(objectKey)
                            .build()
            );
            log.info("Deleted object from MinIO: {}", objectKey);
        } catch (Exception e) {
            log.error("Failed to delete object from MinIO: {}", objectKey, e);
            throw new RuntimeException("Failed to delete object", e);
        }
    }
}
```

**Giải thích:**
- `upload()`: Nhận `MultipartFile` + folder name → sinh UUID filename → upload lên MinIO bucket → trả về object key.
- `getPublicUrl()`: Ghép `publicUrlPrefix + "/" + bucket + "/" + objectKey` → full URL.
- `getPresignedUrl()`: Tạo URL tạm thời có thời hạn (dùng khi bucket private).
- `delete()`: Xóa object khỏi bucket.

---

## Bước 7: Sửa `UserAdmin.java` — Thay thế 2 method avatar

Mở `modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdmin.java`.

### 7a. Xóa 2 method cũ (dòng 130-138)

Xóa 2 method tạm này:
```java
// XÓA ĐOẠN NÀY:
    public String getAvatarUrl() {
        return "http://localhost:9000";
    }

    public String getAvatarUrlS3() {
        String avatarUrl = this.getAvatarUrl();
        return avatarUrl + "/S3";
    }
```

> ⚠️ **Lưu ý:** Field `avatarUrl` (dòng 49-50) đã có sẵn Lombok `@Getter` `@Setter`, nên getter `getAvatarUrl()` sẽ được Lombok tự sinh ra. Method custom `getAvatarUrl()` ở dòng 130-132 ghi đè lên Lombok getter, trả về hardcode `"http://localhost:9000"` → **Sai logic**, cần xóa bỏ.

### 7b. Thêm method mới `getFullAvatarUrl()` (không annotation, dùng ở Service layer)

Thêm method này vào cuối class, trước dấu `}` đóng class:

```java
    /**
     * Trả về full public URL của avatar bằng cách ghép public URL prefix với object key.
     * 
     * Cách dùng: Gọi từ Service layer, truyền StorageService vào để resolve URL.
     * Không đặt @Transient + static dependency trong Entity.
     * 
     * @param publicUrlPrefix prefix từ MinIO public URL (hoặc base-url cho local)
     * @param bucket bucket name từ config
     * @return full URL hoặc null nếu avatarUrl null/blank
     */
    public String resolveAvatarUrl(String publicUrlPrefix, String bucket) {
        if (avatarUrl == null || avatarUrl.isBlank()) {
            return null;
        }
        // Nếu đã là full URL (http/https) thì trả về nguyên bản
        if (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://")) {
            return avatarUrl;
        }
        // Bỏ trailing slash nếu có
        String prefix = publicUrlPrefix.endsWith("/")
                ? publicUrlPrefix.substring(0, publicUrlPrefix.length() - 1)
                : publicUrlPrefix;
        return prefix + "/" + bucket + "/" + avatarUrl;
    }
```

### 7c. Cách dùng ở Service layer

Trong `AdminAuthService.java` hoặc bất kỳ service nào trả về thông tin user admin, sau khi lấy `UserAdmin` entity từ database, gọi:

```java

// Inject StorageProperties vào Service
private final StorageProperties storageProperties;

// ...trong method trả về response:
String fullAvatarUrl = userAdmin.resolveAvatarUrl(
    storageProperties.getMinio().getPublicUrlPrefix(),
    storageProperties.getMinio().getBucket()
);
userAdminResponse.setAvatarUrl(fullAvatarUrl);

```

---

## Cách dùng trong controller upload avatar

```java
@RestController
@RequestMapping("/api/admin/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final StorageService storageService;  // Inject MinioStorageService
    private final UserAdminRepository userAdminRepository;

    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        // 1. Upload file lên MinIO → nhận object key
        String objectKey = storageService.upload(file, "avatars");

        // 2. Lấy user hiện tại
        UserAdmin user = userAdminRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Lưu object key vào DB
        user.setAvatarUrl(objectKey);
        userAdminRepository.save(user);

        // 4. Trả về full public URL
        String fullUrl = storageService.getPublicUrl(objectKey);
        return ResponseEntity.ok(fullUrl);
    }
}
```

---

## Cấu trúc thư mục sau khi hoàn thành

```
modules/identity/src/main/java/com/v8n/modules/identity/
├── infrastructure/
│   ├── config/
│   │   └── MinioConfig.java          ← TẠO MỚI
│   ├── storage/
│   │   ├── StorageProperties.java    ← TẠO MỚI
│   │   ├── StorageService.java       ← TẠO MỚI
│   │   └── MinioStorageService.java  ← TẠO MỚI
│   └── security/
│       └── ...
├── domain/
│   └── entity/
│       └── UserAdmin.java            ← SỬA (xóa method cũ, thêm resolveAvatarUrl)
└── application/
    └── service/
        └── AdminAuthService.java     ← SỬA (dùng StorageProperties để resolve URL)
```

---

## Tổng kết

| STT | Việc cần làm | File |
|-----|-------------|------|
| 1 | Thêm `io.minio:minio:8.5.7` | `build.gradle` (root) |
| 2 | Bỏ fallback localhost MinIO | `src/main/resources/application-prod.yml` |
| 3 | Tạo `StorageProperties` | `modules/identity/.../infrastructure/storage/StorageProperties.java` |
| 4 | Tạo `MinioConfig` | `modules/identity/.../infrastructure/config/MinioConfig.java` |
| 5 | Tạo `StorageService` interface | `modules/identity/.../infrastructure/storage/StorageService.java` |
| 6 | Tạo `MinioStorageService` implement | `modules/identity/.../infrastructure/storage/MinioStorageService.java` |
| 7a | XÓA 2 method `getAvatarUrl()` và `getAvatarUrlS3()` | `UserAdmin.java` (dòng 130-138) |
| 7b | THÊM method `resolveAvatarUrl(prefix, bucket)` | `UserAdmin.java` |
| 7c | Gọi `resolveAvatarUrl()` trong Service | `AdminAuthService.java` hoặc service liên quan |