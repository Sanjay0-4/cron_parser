# Cron Expression Parser

This project provides a Java implementation for parsing and validating cron expressions. It breaks down a cron expression into its constituent fields and validates each according to specified rules. The main classes include `CronExpression`, `CronField`, `CronFieldType`, and utility classes for error handling and testing.

## Features

- **Parse Cron Expressions:** Split and interpret cron expressions into minute, hour, day of month, month, and day of week fields.
- **Validation:** Ensure that each field's values fall within valid ranges and follow correct formats.
- **Custom Exceptions:** Handle parsing errors gracefully with descriptive messages.
- **Unit Testing:** Comprehensive tests to validate the parsing and validation logic.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Maven (for building the project and running tests)

### General Assumptions
1. **Standard Cron Format**
The input cron string follows the standard 5-field format:

    minute (0–59)
    hour (0–23)
    day of month (1–31)
    month (1–12)
    day of week (0–6)

2. **Command is the 6th Field Onward**

Everything after the first five space-separated fields is considered the shell command to execute.

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/Sanjay0-4/cron-parser.git

### Build the project using Maven

- mvn clean install

### Running the script

```bash
$ java -jar cron-parser.jar "0 0 1,2,3,15 * 1-5 /usr/bin/find"
  minute        0
  hour          0
  day of month  1 2 3 15
  month         1 2 3 4 5 6 7 8 9 10 11 12
  day of week   1 2 3 4 5
  command       /usr/bin/find
