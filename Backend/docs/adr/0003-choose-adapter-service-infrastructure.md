# Architecture Decision Record (ADR) - Choice of Database: PostgreSQL

## Status

- **Accepted**

by:
**Dimitar Stoyanov** (GitHub - dimitarstoyanov95) and **Anton Urdzhanov** (GitHub - AntonUrdzhanov)

## Context

In the design of the Event Management System (EMS),
an adapter service is required to fetch event data from an external API and transform it into an internal format to be processed by the backend service.
The decision needs to be made on how to handle the data once it is transformed by the adapter service.

There are several possible approaches:

Directly send the data to the backend after transformation.
Share the same database between the adapter and backend service.
Use separate databases for the adapter and backend services.
Some other pattern for communication.

## Decision

The decision has been made to transfer the transformed data directly to the backend service, rather than storing it in the adapter service's own database.

The adapter will act as a data transformation layer only, which will:

Fetch data from the external event API.

Transform the data into the internal event format required by the backend.

Transfer the transformed data directly to the backend service via an HTTP API or message queue.

The backend service will be responsible for persisting the data into its own database after receiving the transformed data.


## Consequences

**Benefits**

- Simplicity: By not introducing a separate database for the adapter, we reduce the complexity of the infrastructure, avoiding the need to manage and synchronize two databases.
- Lower Latency: By directly sending data to the backend service, the data transfer process is needs less time for data retrieval.
- Avoiding Data Duplication: There is no need for an additional database layer, which reduces the risk of data duplication.
- Extensibility: You can add custom functions, custom types, or more advanced behavior.

**Trade-offs**

- Tight Coupling: The adapter service becomes tightly coupled with the backend service, meaning that if the backend service is unavailable, the adapter service cannot complete its task of transferring data.
- Data Loss: The direct communication between the services is typically done via an HTTP API.  If the backend service becomes unavailable (network issues, or other disruptions),
the adapter service may fail to complete its task of transferring data. This could result in data not being transferred.

Transferring the data directly from the adapter service to the backend service has been chosen as approach for this microservice architecture.
It provides simplicity and aligns with the microservice principle of clear service responsibilities, without adding unnecessary complexity or infrastructure overhead.
