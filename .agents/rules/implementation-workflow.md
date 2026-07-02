# MANDATORY IMPLEMENTATION WORKFLOW

## Rule: Plan First, Wait for Approval, Then Implement

**THIS IS A MANDATORY RULE. YOU MUST FOLLOW THIS WORKFLOW FOR ANY TASK THAT INVOLVES IMPLEMENTING CODE.**

### Workflow Steps

#### Step 1: Read Rules & Analyze
- Read ALL rule files in `.agents/rules/` before starting
- Read relevant sections of `repomix-output.md` to understand the current codebase
- Analyze the task thoroughly

#### Step 2: Create a Detailed Plan
- Outline exactly what files will be created/modified
- Specify what classes, methods, or configurations will be added/changed
- Describe the approach and reasoning
- Present the plan to the user

#### Step 3: WAIT FOR USER APPROVAL
- **DO NOT write any code until the user explicitly approves your plan**
- The user may request changes to the plan — revise and present again
- Only proceed to implementation after receiving clear approval (e.g., "approved", "go ahead", "ok", "proceed")

#### Step 4: Implement
- Follow the approved plan exactly
- Do not expand scope beyond what was approved
- If you discover additional changes are needed during implementation, pause and ask the user

### What Requires This Workflow
- Creating new files/classes
- Modifying existing code
- Adding new features
- Refactoring
- Any task that changes the codebase

### What Does NOT Require This Workflow
- Reading/searching files for analysis
- Answering questions about the codebase
- Creating task plans (planning itself is part of the workflow)

### Violation
**Implementing code without user approval is a violation of this rule.** Always wait for the user to explicitly approve your plan before writing any code.