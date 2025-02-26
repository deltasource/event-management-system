# Architecture Decision Record (ADR) - Choice of Database: PostgreSQL

## Status

- **Proposed**

by:
**Anton Urdzhanov** (GitHub - AntonUrdzhanov)

## Context

For the Event Management System (EMS) project, the database needs to handle large sets of transactional data
and scale as the application grows. The choice of database is essential to ensure the application’s robustness,
flexibility, and ability to support complex queries and transactions.

## Decision

PostgreSQL has been chosen as the database for the application.
It is a highly reliable and feature-rich relational database management system (RDBMS) that supports ACID properties,
advanced SQL features, and scalability. PostgreSQL is known for its robustness
and comprehensive set of tools, making it a perfect fit for our application's needs.

## Consequences

**Benefits**

- ACID: PostgreSQL ensures full ACID compliance, providing strong guarantees for transactional integrity.
- Advanced SQL Features: Window Functions, JSON, JSONB and XML support, Full-text search.
- Multi-Version Concurrency Control on transactions: Meaning that multiple transactions can be processed simultaneously without blocking each other,
which leads to better performance.
- Extensibility: You can add custom functions, custom types, or more advanced behavior.

**Trade-offs**

- Complexity: PostgreSQL can be more complex to set up, configure, and maintain compared to simpler databases like MySQL or SQLite.
It has a wide array of advanced features, which can be overwhelming.
- Performance for Simple Read-Heavy Workloads: It may not be as fast as MySQL for simple, read-heavy workloads or basic operations.
- Memory and Disk Usage: PostgreSQL can be more memory and disk-intensive than other databases like MySQL,
especially when using features like indexing, full-text search, or advanced data types.

PostgreSQL has been chosen as the database for the Event Management System project due to its robustness,
ACID compliance, and advanced SQL features. It supports complex queries, JSON, custom types
and multi-version concurrency control (MVCC) to handle high transactional workloads.
