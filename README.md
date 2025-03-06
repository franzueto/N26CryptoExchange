# CryptoExchange

**CryptoExchange** is an Android application that provides real-time cryptocurrency exchange information. It's built using modern Android development practices, including Jetpack Compose for the UI, and follows a clean architecture approach.

## Getting Started

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Build and run the application.

## Contributing

Contributions are welcome! Please feel free to open a pull request or submit an issue.

## Main Functionality

* **Real-time Data:** The app fetches and displays up-to-date cryptocurrency exchange rates.
* **User-Friendly Interface:** The UI is designed with Jetpack Compose, providing a smooth and responsive user experience.
* **Data Display:** The app presents the data in a clear and concise manner, allowing users to easily track their favorite cryptocurrencies.

### App experience
| Price List Screenshot | Video|
| -------- | ------- |
|<img src="https://github.com/user-attachments/assets/9e6b16e5-5d34-4a64-afab-e318fe7a83aa" width="300" /> | [Screen_recording_20250305_011540.webm](https://github.com/user-attachments/assets/78a6b383-3636-4740-8c86-425db62d83bf) |

## API

The application interacts with a remote API to retrieve cryptocurrency data from [developers.coindesk.com](https://developers.coindesk.com/) API.
The specific API endpoint and structure are defined within the network layer of the project inside the `data/remote` folder.

## Libraries

This project leverages several key libraries to ensure robustness and maintainability:

* **Retrofit:** Used for type-safe HTTP client communication with the API. It simplifies the process of making network requests and handling responses.
* **Gson:** Employed for serializing and deserializing JSON data, facilitating the conversion of API responses into Kotlin data objects.
* **Jetpack Compose:** Modern toolkit for building native Android UI.
* **Navigation Compose:** Used for navigation between screens.
* **Material 3:** Used for the UI components.
* **Lifecycle:** Used for managing the lifecycle of the app.

## Testing

The project emphasizes a strong testing culture, incorporating various testing strategies:

* **Mocking:** Mockk is used to create mock objects, isolating the code under test from external dependencies.
* **Mock Server:** A mock server is used to simulate API responses during testing, ensuring that the app behaves correctly in different network scenarios.
* **Coroutines Test:** Used for testing coroutines.
* **Unit Tests:** JUnit is used for writing unit tests to verify the behavior of individual components.
* **Espresso:** UI tests are implemented using Espresso to ensure the UI behaves as expected. (An example of an Espresso test is included to demonstrate how to test UI interactions.)

## Code Quality

* **Jacoco:** Code coverage is measured using Jacoco. The project is configured to generate reports and enforce a minimum coverage threshold of 80% for lines.
* **Ktlint:** Code style is enforced using Ktlint, ensuring a consistent and readable codebase.

## CI/CD

* **GitHub Actions:** A GitHub Actions workflow is configured to run the following checks:
    * **Unit Tests:** All unit tests are executed.
    * **UI Tests:** All UI tests are executed.
    * **Code Coverage:** Jacoco reports are generated and verified.
    * **Ktlint:** Code style is checked.
*   **PR Verification:** These checks are automatically triggered on every pull request to ensure code quality and prevent regressions.
*   **Merge to Main:** The same checks are also run when code is merged into the `main` branch, guaranteeing the stability of the main codebase.
