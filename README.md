# Web Traffic Light Controller

## Overview

This project simulates a simple traffic light controller using a Harel Statechart in Java.
A real controller manages the states of traffic lights at an intersection, ensuring safe and efficient traffic flow.

It is intended to be used together with the paper "Applying Test Modeling Techniques: A Case Study Using A Statechart-Based System",
by Adail Muniz Retamal, to demonstrate the use of:
- Model-Based Testing (MBT)
- Automated Acceptance Testing (AAT)
- Screenplay pattern

There are two main parts in this project:
1. **Statechart**: A modified version of the [itemis CREATE Traffic Light example](https://www.itemis.com/en/products/itemis-create/documentation/examples/itemis-create-examples-trafficlight-java),
   with a Harel Statechart that defines the states and transitions of the traffic light controller.
   - **Model-Based Testing**: The tests are generated based on the statechart model and the Each Choice Coverage (ECC), Pair-Wise Coverage (PWC) and DT (Decision Table) techniques,
   ensuring comprehensive coverage of the traffic light controller's behavior.
2. **Web Application**: A simple web application that simulates a traffic light controller and its control panel.
   - **Automated Acceptance Testing**: The tests are designed to validate the acceptance criteria of the traffic light controller, using Serenity BDD, Playwright, and the Screenplay pattern,
   ensuring it meets the requirements.

## Requirements
- Java 21 or higher
- Maven
- JUnit5
- Playwright
- Serenity BDD
- [itemis CREATE](https://www.itemis.com/en/products/itemis-create)

## Getting Started
First, clone the repository:
```bash
   git clone https://github.com/TheAdail/paper-mbt-statechart
   cd paper-mbt-statechart
```
You can open the project in your favorite IDE, such as IntelliJ IDEA or Eclipse.

### Web Traffic Light Controller
To get started with the Web Traffic Light Controller project, follow these steps:
1. Move into the `web-traffic-controller` directory:
   ```bash
   cd web-traffic-controller
   ```
2. Build the project using Maven:
   ```bash
   mvn compile
   ```
3. Run the web application:
   ```bash
   mvn spring-boot:run
   ```
4. Open your web browser and navigate to `http://localhost:8081` to access the traffic light controller interface.
   - You can control it using the buttons provided in the interface.
5. To run the tests:
   - Ensure the web application is running and the traffic light controller is in the OFF mode.
   - Run the tests using Maven:
      ```bash
       mvn test
       ```
   - If some tests fail, it may be due to the initial state of the traffic light controller.
   Ensure it is in the OFF mode before running the tests.
6. To generate the Serenity report:
   ```bash
    mvn serenity:aggregate
    ```
7. To view the Serenity report, open the generated HTML file in your browser:
   ```bash
   target/site/serenity/index.html
   ```

### itemis CREATE Traffic Light Example
To get started with the itemis CREATE Traffic Light example, follow these steps (after cloning the repository):

1. Download and install the itemis CREATE application from the [itemis CREATE website](https://www.itemis.com/en/products/itemis-create).
2. Import the project into itemis CREATE:
   - Open itemis CREATE.
   - Go to `File` -> `Import...`.
   - Select `Existing Projects into Workspace` and click `Next`.
   - Browse to the cloned repository and select the `itemis.create.examples.trafficlight.java` folder.
   - Click `Finish`.
3. Open the `TrafficLightCtrl` statechart file:
   - In the `Project Explorer`, expand the `itemis.create.examples.trafficlight.java` folder.
   - Open the `model/TrafficLightCtrl.ysc` file.
   - If you want to simulate the statechart, right-click on the file and choose `Run As` -> `Statechart Simulation`.
4. Run the MBT tests:
   - Right-click on the `tests/MBT_StatechartTest.sctunit` file.
   - Select `Run As` -> `SCT Unit`.

## License
These projects are licensed under the MIT License - see the [LICENSE](LICENSE) file for details.