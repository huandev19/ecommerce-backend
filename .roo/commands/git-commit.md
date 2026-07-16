# Git Commit

Commit code changes with a conventional commit message.

**Skill:** `git-commit` (defined in `.roo/skills/git-commit/SKILL.md`)

**Flow:**
1. Show current git status
2. Display a summary of changes (`git diff --stat`)
3. Prompt for a commit message
4. Confirm before executing `git add . && git commit -m "<message>"`

---

**Steps**

1. **Check git status**

   Run:
   ```bash
   git status --short
   ```
   If the output is empty (no changes to commit), inform the user and stop.

2. **Show diff summary**

   Run:
   ```bash
   git diff --stat
   ```
   This shows a concise summary of what files changed.

   If there are untracked files, also show them:
   ```bash
   git diff --stat --cached
   ```

3. **Generate a suggested commit message (optional)**

   Suggest a conventional commit prefix based on the types of files changed:

   | File patterns detected | Suggested prefix |
   |---|---|
   | `src/main/java/` changes | `feat:` or `fix:` |
   | `src/test/java/` changes | `test:` |
   | `build.gradle` / `settings.gradle` | `build:` |
   | `.md` / `README` | `docs:` |
   | `.yml` / `.yaml` / `.properties` | `chore:` |
   | Mix of types | Ask the user or use `chore:` |

4. **Ask for commit message**

   Prompt the user with:
   ```
   Enter commit message (suggested: <suggested-prefix> <short description>):
   ```
   Allow the user to type their message. If the message is empty, abort.

5. **Confirm and commit**

   Display what will be committed:
   ```
   Files to commit:
   <list of changed files from git status>

   Commit message: <message>
   Proceed? (Y/n):
   ```
   - If `Y` (or Enter): run `git add . && git commit -m "<message>"`
   - If `n`: abort, inform the user.

6. **Show result**

   After committing, show the commit output and the author/commit hash.

**Output Example**

```
## Git Commit

### Changes detected
 M src/main/java/com/v8n/.../SomeFile.java
 M src/test/java/com/v8n/.../SomeTest.java
?? src/main/java/com/v8n/.../NewFile.java

### Diff summary
 src/main/java/.../SomeFile.java | 10 +++++++---
 src/test/java/.../SomeTest.java | 15 +++++++++++++++
 2 files changed, 22 insertions(+), 3 deletions(-)

### Commit message
feat: add new feature xyz

### Result
[main a1b2c3d] feat: add new feature xyz
 2 files changed, 22 insertions(+), 3 deletions(-)
 create mode 100644 src/main/java/com/v8n/.../NewFile.java
```
