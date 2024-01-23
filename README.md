# Trellolo

## Overview

This repository contains the source code for Trellolo, a project aimed at providing a streamlined task management system. The project is built using Java 17, Spring Boot, and utilizes a PostgreSQL database. To contribute or run the project locally, please follow the instructions below.

## Prerequisites

Before you begin, ensure you have the following software installed:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven](https://maven.apache.org/download.cgi)

## Getting Started

1. **Clone the repository:**

    ```bash
    git clone https://github.com/trydmii/trellolo.git
    cd trellolo
    ```

2. **Set up PostgreSQL:**

    - Create a database named `trellolo`.
    - Update `application.yml` with your database credentials.

3. **Launch the project using IntelliJ IDEA:**

    - Open IntelliJ IDEA.
    - Navigate to File > Open and select the cloned project.
    - Build the project using Maven.
    - Run the `Trelolo` class.

4. **Alternatively, you can launch the project using Maven from the command line:**

    ```bash
    cd trellolo
    mvn spring-boot:run
    ```

Now, Trellolo should be up and running locally. Access the application at [http://localhost:8080](http://localhost:8080).

## Contributors

- Dmytro Pushar
- trydmi.hf@gmail.com
