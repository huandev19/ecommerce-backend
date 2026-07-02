# V8N-ECOMMERCE BACKEND AI RULES

## Mandatory: Read All Rule Files
- **CRITICAL**: Before starting any task, you MUST read ALL files in the `.agents/rules/` directory.
- Current rule files:
  - `rules/design_patten.md` — Mandatory design patterns & architecture of the project
  - `rules/implementation-workflow.md` — Mandatory workflow: Plan → User Approval → Implement (DO NOT implement without user approval)
  - `rules/repomix-index-guide.md` — Index map of all files in repomix-output.md (~180 files), quick lookup guide by line number, ABSOLUTELY DO NOT read all 9465 lines

## Single Source of Truth for Codebase Context
- **CRITICAL**: Before starting to write code, search for logic flows, or analyze the project architecture, you MUST read and extract information from the file `/Volumes/Hdev/SkillLearn/StudyJava/SourceCode/Backend/v8n-ecommerce/repomix-output.md`.
- **Reason**: This file is a packed version of the entire codebase, providing a comprehensive overview of the project without the need to guess or blindly search for file names/paths.
- **How to execute**: 
  1. Use the `grep_search` or `view_file` tool on `repomix-output.md` to search for class, interface, function, or DTO definitions related to the task.
  2. Based on the packed content, map out the necessary changes.
  3. Only when you are absolutely certain about which physical file needs to be modified should you perform direct edit operations on that file in the actual source code directory.