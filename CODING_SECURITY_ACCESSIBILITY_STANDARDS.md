CODING, SECURITY & ACCESSIBILITY STANDARDS (JAVA PROJECTS)

This document defines all coding, security, and accessibility guidelines used for automated PR reviews, static analysis, and AI-based code review agents.

1. JAVA CODING STANDARDS

1.1 Naming Conventions
- Classes → PascalCase
- Variables → camelCase
- Methods → camelCase
- Constants → UPPER_SNAKE_CASE
- Avoid abbreviations (e.g., custNo → customerNumber)
- Meaningful and descriptive names always required

1.2 Package & Import Rules
- Package format: com.company.project.module
- Do not use wildcard imports (import java.util.*;)
- Remove unused imports
- Keep import ordering consistent: Java → Third-party → Project

1.3 Class Structure & Formatting
- Line length ≤ 120 characters
- Class member order:
  1. Static variables
  2. Instance variables
  3. Constructors
  4. Public methods
  5. Private methods
- One top-level class per file
- Avoid deeply nested if/else logic; refactor when needed

1.4 Dependency Injection
- Prefer constructor injection
- Using Lombok? → Use @RequiredArgsConstructor
- Avoid field injection (@Autowired on attributes)

1.5 Logging Standards
- No System.out.println statements
- Use project-approved logger (Logger, Slf4j, etc.)
- Logging rules:
  - No sensitive info: passwords, tokens, PII
  - Use consistent log levels
  - Each error must log context-rich info

1.6 Error Handling
- Never swallow exceptions:
  try { ... } catch (Exception e) { }
  Never allowed
- Always log or rethrow
- Prefer custom exceptions
- Avoid catching generic Exception unless required
- Include meaningful error messages

1.7 Collections, Streams & Java Best Practices
- Use Streams for transformations, not complex business logic
- Prefer immutability where possible
- Avoid large methods (>30 lines)
- Avoid deeply nested streams

2. SECURITY STANDARDS (OWASP + JAVA BACKEND)

2.1 Input Validation
- Validate all external input:
  - Request params
  - JSON bodies
  - Cookies / headers
- Use whitelist validation where possible
- Reject unknown or unexpected fields

2.2 SQL & Persistence Layer Security
- Always use Prepared Statements or Spring Data JPA
- Never concatenate SQL strings
- Avoid exposing DB errors in API responses
- Use parameterized queries only

2.3 Authentication & Authorization
- Every protected endpoint must require authentication
- Use Spring Security:
  - @PreAuthorize("hasRole('ADMIN')")
  - @RolesAllowed({"USER"})
- Sensitive endpoints require additional authorization
- Do not expose user roles/tokens in logs

2.4 Sensitive Data Handling
- Do not log:
  - Passwords
  - Tokens
  - API keys
  - Credit card data
  - Personal identifiers
- Store secrets securely using environment variables or Vault
- Always use HTTPS communication
- Mask sensitive data before logging

2.5 API Security Rules
- Validate request Content-Type
- Use standardized error responses
- Prevent:
  - SQL Injection
  - XSS
  - CSRF
  - SSRF
  - Path Traversal
  - Insecure Direct Object Reference (IDOR)
- Rate-limit sensitive endpoints

2.6 File Upload Security
- Validate:
  - MIME type
  - Extension
  - File size
- Store uploaded files outside the classpath
- Normalize filenames to prevent path traversal
- Generate safe random filenames for storage

2.7 Secrets & Configuration
- No hardcoded credentials
- Never commit:
  - .env
  - .pem
  - .p12
  - Credentials files
- Use application properties only for non-sensitive config

3. ACCESSIBILITY (WCAG-Lite FOR JAVA WEB APPS)

3.1 Semantic HTML
- Use semantic elements:
  - <button>, <header>, <footer>, <nav>, <main>
- Avoid using <div> for clickable elements
- Ensure headings follow correct order (h1 → h2 → h3 …)

3.2 Form Labeling Standards
- Each input must have an associated <label>
- Use:
  <label for="email"></label>
  <input id="email">
- Required fields must be marked in UI and programmatically
- Inputs must have accessible names via:
  - aria-label
  - aria-labelledby

3.3 Image Accessibility
- All meaningful images require descriptive alt text
- Decorative images must use: alt=""

3.4 Keyboard Accessibility
- All UI elements must be usable via keyboard alone
- No custom components that trap focus
- Avoid tabindex values greater than 0

3.5 Color & Contrast
- Text contrast:
  - Normal text: 4.5:1
  - Large text: 3:1
- Error states must not rely on color alone
- Include icons or text indicators

3.6 Accessible Error Messaging
- Errors must:
  - Clearly describe the issue
  - Link to the input field
  - Be screen-reader readable

Example:
<div role="alert">Email format is invalid</div>

3.7 ARIA Guidelines
- Use ARIA roles only when semantic HTML cannot achieve the same
- Avoid redundant ARIA attributes
- Never misuse ARIA roles (role="button" on a div without keyboard support)

4. SEVERITY LEVEL DEFINITIONS

Critical — Must Fix Before Merge
- Security vulnerabilities
- Leaking sensitive data
- Hardcoded secrets
- Unvalidated user input
- SQL injection possibility
- Broken authentication/authorization

Major — Should Fix
- Incorrect naming conventions
- Inconsistent structure
- Misused DI
- Poor readability
- Missing logs or wrong log levels

Minor — Nice to Have
- Code style improvements
- Small refactoring
- Optimizing loops/streams

Positive Feedback
- Clean abstraction
- Good exception handling
- Secure coding awareness
- Helpful logs
