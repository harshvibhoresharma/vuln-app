# 🔐 Vulnerable App Dependency Check (Java + Maven + Multi CI/CD)

This project demonstrates how to identify and manage vulnerable dependencies in a Java application using **OWASP Dependency-Check** with report and chart generation.

## 📌 What was added

- ✅ **Automated report generation** (`target/security-summary.md`)
- ✅ **Chart generation** (Mermaid pie chart in the markdown report)
- ✅ **Multiple CI/CD integrations**:
  - Jenkins (`Jenkinsfile`)
  - GitHub Actions (`.github/workflows/ci.yml`)
  - GitLab CI (`.gitlab-ci.yml`)
- ✅ **Database usage clarification** (see below)

## 🧰 Tech Stack

- Java 17
- Maven
- OWASP Dependency-Check
- Jenkins + GitHub Actions + GitLab CI

## 🗄️ Database Used

This application itself does **not use an application database** (no MySQL/PostgreSQL/MongoDB integration in runtime code).

For security scanning, OWASP Dependency-Check maintains a **local vulnerability data cache** (NVD and advisory data) during scans.

## 📁 Project Structure

```text
vuln-app/
├── src/main/java/com/demo/App.java
├── src/main/java/com/demo/SecurityReportGenerator.java
├── src/test/java/com/demo/AppTest.java
├── pom.xml
├── Jenkinsfile
├── .github/workflows/ci.yml
├── .gitlab-ci.yml
└── target/ (build + generated reports)
```

## 🚀 Run Locally

### 1) Build + test

```bash
mvn clean verify
```

### 2) Run dependency security scan

```bash
mvn org.owasp:dependency-check-maven:check
```

### 3) Generate summary report + chart

```bash
mvn -DskipTests exec:java -Dexec.mainClass=com.demo.SecurityReportGenerator
```

## 📊 Generated Outputs

After running scan + report generation:

- `target/dependency-check-report.html`
- `target/dependency-check-report.xml`
- `target/security-summary.md` ← contains table + Mermaid pie chart

## ⚙️ CI/CD Pipelines

### Jenkins

`Jenkinsfile` runs: build → test → dependency-check → summary generation → package, then archives reports.

### GitHub Actions

`.github/workflows/ci.yml` runs on push/PR and uploads security report artifacts.

### GitLab CI

`.gitlab-ci.yml` defines `build`, `test`, `security_scan`, and `security_summary` stages with artifacts.
