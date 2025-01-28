# Tomasulo Algorithm Simulation

## Overview

This project is a graphical simulator for the Tomasulo algorithm, designed to execute MIPS assembly instructions step-by-step. It simulates the inner workings of dynamic instruction scheduling, showcasing how instructions are executed, system states are updated cycle-by-cycle, and how data hazards (RAW, WAR, WAW) are resolved effectively.

---

## Features

- **Dynamic Instruction Input**:
  - Enter instructions manually (e.g., `DIV R1 R2 R2`) or upload a `.txt` file containing multiple instructions.
  
- **Cycle-by-Cycle Simulation**:
  - Visualizes instruction execution with updates for each cycle.

- **Graphical User Interface (GUI)**:
  - Displays reservation stations, register file, cache, and instruction queues in a tabular format using JavaFX.

- **Instruction Support**:
  - ALU Operations: Floating-point add, subtract, multiply, divide; integer operations (e.g., ADDI, SUBI).
  - Load/Store Operations: Includes `LW`, `LD`, `L.S`, and `L.D`.
  - Branch Instructions: Supports basic branching without prediction.

- **Customizable Parameters**:
  - Configure instruction latency, cache settings (size, block size, penalties), and reservation station/buffer sizes.
  - Pre-load or define initial register values.

- **Hazard Detection and Management**:
  - Identifies and resolves RAW, WAR, and WAW hazards.
  - Manages concurrent result publication on the Common Data Bus (CDB).

- **Cache Simulation**:
  - Simulates data cache misses (ignoring address clashes and instruction cache misses).

---

## Getting Started

### Prerequisites

Ensure the following tools are installed on your system:
- **Java Development Kit (JDK)** 8 or higher
- **Maven** (for building the project)

### Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Malek74/Tomasulo-Algorithm-Simulation.git
   cd Tomasulo-Algorithm-Simulation
