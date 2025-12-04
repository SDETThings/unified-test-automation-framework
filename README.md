Unified Test Automation Framework

A scalable, modular, and unified automation framework designed for Web UI (Selenium & Playwright), API (REST Assured), and Hybrid testing. The framework is built using Java + TestNG, supports parallel execution, integrates with BrowserStack,
and includes a pluggable core library for code reuse across multiple projects.

🚀 Features
🔹 1. Multi-Channel Automation Support

Selenium WebDriver (local, remote, BrowserStack)

Playwright for Java (fast, reliable UI automation)

REST Assured for API testing

Support for hybrid API + UI workflows

🔹 2. Modular Architecture

Framework is divided into well-defined modules:

Module	Responsibility
core	Low-level wrappers, driver utilities, BrowserManager, REST utilities, TestNG listeners
web	Playwright/Selenium page objects, actions, locators
api	REST Assured templates, request/response specifications
tests	TestNG test cases for UI/API/Hybrid
resources	Configuration files (config.properties, capabilities, test data)

🔹 3. Thread-Safe Execution

Uses ThreadLocal to isolate browser instances

Supports parallel execution without collision

Clean lifecycle management via BrowserManager.closeBrowser()
