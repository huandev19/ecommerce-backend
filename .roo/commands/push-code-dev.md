# Push Code Dev

Push code lên nhánh `dev` với output rõ ràng, verbose bằng tiếng Việt.

**Skill:** `push-code-dev` (defined in `.roo/skills/push-code-dev/SKILL.md`)

**Flow:**
1. Kiểm tra branch hiện tại
2. Fetch remote để cập nhật trạng thái
3. Hiển thị danh sách commit sắp push
4. Push lên `origin/dev`
5. Hiển thị kết quả chi tiết

---

**Steps**

1. **Kiểm tra branch hiện tại**

   Run:
   ```bash
   git branch --show-current
   ```
   - Nếu không phải branch `dev`: cảnh báo và hỏi người dùng có muốn tiếp tục không.
   - Nếu đang ở `dev`: tiếp tục.

2. **Kiểm tra trạng thái working directory**

   Run:
   ```bash
   git status --short
   ```
   - Nếu có thay đổi chưa commit: cảnh báo người dùng rằng có file chưa được commit, hỏi có muốn push không (các thay đổi chưa commit sẽ không được push).
   - Nếu sạch: tiếp tục.

3. **Fetch remote để đồng bộ**

   Run:
   ```bash
   git fetch origin dev
   ```
   Để đảm bảo thông tin về remote là mới nhất.

4. **Hiển thị danh sách commit sắp push**

   Run:
   ```bash
   git log origin/dev..HEAD --oneline --no-merges
   ```
   
   Nếu không có commit nào để push: thông báo "Không có commit nào cần push, branch dev đã đồng bộ với origin/dev" và dừng.

   Nếu có commit: hiển thị danh sách rõ ràng:
   ```
   📋 DANH SÁCH COMMIT SẮP PUSH (X commit):
   abc1234 feat(identity): merge users into customers
   def5678 docs(openspec): add customer-identity specs
   ghi9012 chore: add database connection script
   ```

5. **Hiển thị thông tin remote**

   Run:
   ```bash
   git remote get-url origin
   ```

6. **Push lên origin/dev**

   Run:
   ```bash
   git push -v origin dev 2>&1
   ```
   
   Sử dụng `-v` (verbose) để có output chi tiết.

7. **Hiển thị kết quả**

   Parse output từ lệnh push và hiển thị bằng tiếng Việt:

   Thành công:
   ```
   ╔══════════════════════════════════════════════════════╗
   ║              ✅ PUSH THÀNH CÔNG                      ║
   ╠══════════════════════════════════════════════════════╣
   ║ Remote  : https://github.com/.../repo.git           ║
   ║ Branch  : dev → origin/dev                          ║
   ║ Range   : a241783..9d6751b                          ║
   ║ Commits : 3 commit đã được đẩy lên                  ║
   ╚══════════════════════════════════════════════════════╝
   ```

   Thất bại:
   ```
   ╔══════════════════════════════════════════════════════╗
   ║              ❌ PUSH THẤT BẠI                       ║
   ╠══════════════════════════════════════════════════════╣
   ║ Lỗi: <error message>                                ║
   ╚══════════════════════════════════════════════════════╝
   ```

   Sau đó hiển thị các bước xử lý gợi ý nếu thất bại:
   - `git pull origin dev` nếu bị rejected do remote có commit mới
   - Giải quyết conflict nếu có

**Output Example (Thành công)**

```
## 🚀 PUSH CODE LÊN DEV

### 📍 Branch hiện tại: dev

### 📋 Danh sách commit sắp push (3 commit):
   d34edcf feat(identity): merge users into customers
   187ebf0 docs(openspec): add customer-identity specs
   9d6751b chore: add database connection script

### 🔗 Remote: https://github.com/huandev19/ecommerce-backend.git

### ⏳ Đang push lên origin/dev...

### Kết quả:
╔══════════════════════════════════════════════════════╗
║              ✅ PUSH THÀNH CÔNG                      ║
╠══════════════════════════════════════════════════════╣
║ Remote  : https://github.com/huandev19/ecommerce-backend.git
║ Branch  : dev → origin/dev                          ║
║ Range   : a241783..9d6751b                          ║
║ Commits : 3 commit đã được đẩy lên                  ║
╚══════════════════════════════════════════════════════╝
```

**Output Example (Không có gì để push)**

```
## 🚀 PUSH CODE LÊN DEV

### 📍 Branch hiện tại: dev

### Kết quả:
✅ Branch dev đã đồng bộ với origin/dev. Không có commit nào cần push.
```
