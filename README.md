# 🔐 Vulnerable App Dependency Check (Java + Maven + Jenkins)

This project demonstrates how to identify and manage vulnerable dependencies in a Java application using **OWASP Dependency-Check** integrated with **Maven** and **Jenkins CI/CD**.

---

## 📌 Overview

Modern applications rely heavily on third-party libraries, which may contain known vulnerabilities. This project scans dependencies defined in `pom.xml` and generates a vulnerability report to help developers fix security issues early.

---

## 🧰 Tech Stack

* ☕ Java
* 📦 Maven
* 🔍 OWASP Dependency-Check
* ⚙️ Jenkins (CI/CD)

---

## 📁 Project Structure

```
vuln-app/
│── src/
│   ├── main/java/com/demo/App.java
│   └── test/java/com/demo/AppTest.java
│
│── target/                         # Build output
│── pom.xml                         # Maven dependencies
│── dependency-check-report.xml     # Vulnerability report
│── Jenkinsfile                     # CI/CD pipeline
│── .idea/                          # IDE config
```

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/vuln-app.git
cd vuln-app
```

---

### 2️⃣ Build the Project

```bash
mvn clean install
```

---

### 3️⃣ Run Dependency Check

```bash
mvn org.owasp:dependency-check-maven:check
```

This will:

* Scan all dependencies in `pom.xml`
* Compare them against known vulnerability databases (NVD)
* Generate a report in the `target/` directory

---

## 📊 Vulnerability Report

After scanning, check:

```
target/dependency-check-report.html
```

or

```
dependency-check-report.xml
```

### Example Finding

```
Dependency: commons-collections:3.2.1
Severity: HIGH
CVE: CVE-2015-7501
Fix: Upgrade to a secure version
```

---

## ⚙️ Jenkins Integration

This project includes a `Jenkinsfile` to automate scanning.

### Pipeline Stages:

1. Checkout Code
2. Build with Maven
3. Run Dependency Check
4. Archive Report

### Sample Jenkins Step

```groovy
stage('Dependency Check') {
    steps {
        sh 'mvn org.owasp:dependency-check-maven:check'
    }
}
```

---

## 🔒 Why This Matters

* Prevents shipping vulnerable code
* Helps comply with security standards
* Improves overall software quality

---

## 🛠️ Customization

You can configure Dependency-Check in `pom.xml`:

```xml
<configuration>
    <failBuildOnCVSS>7</failBuildOnCVSS>
</configuration>
```

This fails the build if vulnerabilities exceed a severity threshold.

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📌 Future Improvements

* [ ] Add Docker support
* [ ] Integrate with GitHub Actions
* [ ] Add Slack/Email alerts
* [ ] Centralized vulnerability dashboard

---

## 🤝 Contributing

Feel free to fork the project and submit pull requests.

---

## 📜 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Harsh Vibhore Sharma**
**23FE10SE00047**

---

## ⭐ Acknowledgements

* OWASP Dependency-Check
* National Vulnerability Database (NVD)

---
