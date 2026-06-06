# Intelligent Password Security Analyzer

A Java-based console application that demonstrates password security concepts through password strength analysis, dictionary attacks, and multithreaded brute-force attack simulation.

## Features

- Password Strength Analysis
- Entropy Calculation
- Estimated Crack Time
- Dictionary Attack Simulation
- Multithreaded Brute Force Attack
- Live Attack Progress Display
- Final Security Report

## Technologies Used

- Java
- Multithreading
- Recursion
- Regular Expressions (Regex)
- AtomicBoolean
- Object-Oriented Programming

## How It Works

1. User enters a password.
2. System analyzes password strength.
3. Dictionary attack is attempted using common passwords.
4. If unsuccessful, a multithreaded brute-force attack begins.
5. Results including attempts, crack time, and status are displayed.

## Concepts Demonstrated

- Cybersecurity Fundamentals
- Password Cracking Techniques
- Time Complexity
- Thread Synchronization
- Search Algorithms

## Sample Output

```text
Enter Password: admin

Strength: WEAK

[INFO] Starting Dictionary Attack...

[TRYING] 123456
[TRYING] password
[TRYING] admin

[SUCCESS] Password Found using Dictionary Attack!
