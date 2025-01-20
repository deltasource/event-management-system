# Architecture Decision Record (ADR) - Choice of Spring Boot as backend framework

## Status
- **Accepted**

## Context
We are building the backend part for Event Management System project, which should be written on Java.
The framework must be easy of setup, have easy integration with databases, provide security and testing frameworks.

## Decision
We have decided to use Spring Boot as the framework for this application.
Spring Boot provides a set of production-ready features that allow us to develop and deploy applications quickly with minimal configuration. 
Its built-in defaults for things like embedded servers (Tomcat, Jetty, etc.), which simplify the setup process.
Spring Boot is a part of the larger Spring ecosystem, which includes a wide variety of tools and libraries. 
This makes it easy to integrate with other Spring modules like Spring Security (for authentication and authorization) and
Spring Data (for database access).

## Consequences
**Benefits**
- Faster development: It provides abstraction which allows us to focus more on building business logic rather than configuration and boilerplate code.
- Easier maintenance: With Spring Boot’s opinionated setup and strong conventions, we will have less configuration to manage, leading to cleaner and more maintainable code.
- Security: Spring Security, which integrates perfect with Spring Boot, provides strong security features such as authentication, authorization, and protection.

**Trade-offs**
- If you're building a lightweight application with minimal requirements, the full power of Spring Boot
may introduce unnecessary complexity and overhead.

