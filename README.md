# ZyneticAssignmentApp

A modern product catalog Android app built using **Jetpack Compose**, **Ktor**, **Koin**, and **Clean Architecture**. Designed to demonstrate clean code practices, robust UI/UX, and scalable architecture — this app fetches product data from a public API and presents it in a beautifully responsive interface with smooth state handling and graceful error recovery.

---

## ✨ Features

- 🛍 Product List Screen
    - Displays title, description, and product image.
    - Skeleton shimmer loading for image placeholders.

- 📦 Product Details Screen
    - Full product image carousel.
    - Descriptive details: title, category, rating, price, and description.
    - Error handling with animated Lottie screen and Snackbar retry.

- 🧭 Navigation
    - Jetpack Compose Navigation with typed route arguments.
    - Seamless back navigation and view transition.

- 🎨 UI Polish
    - Compose Material3, custom accent colors, and smooth animations.
    - Time-based greetings and adaptive layouts.

---

## 🧱 Tech Stack

| Layer              | Stack                                      |
|-------------------|---------------------------------------------|
| Language           | Kotlin                                      |
| UI                 | Jetpack Compose + Material3                 |
| State Management   | ViewModel + StateFlow                       |
| Navigation         | Jetpack Compose Navigation                  |
| Network            | [Ktor Client](https://ktor.io)              |
| DI                 | [Koin](https://insert-koin.io)              |
| Serialization      | Kotlinx Serialization                       |
| Image Loading      | Coil with `rememberAsyncImagePainter`       |
| Animations         | [Lottie for Compose](https://airbnb.io/lottie/) |
| Architecture       | MVVM + Clean Architecture                   |

---

## 📁 Project Structure

```
tech.abhranilnxt.zyneticassignmentapp/
├── core/
│   ├── data.networking/
│   │   ├── constructUrl.kt
│   │   ├── HttpClientFactory.kt
│   │   ├── responseToResult.kt
│   │   └── safeCall.kt
│   ├── domain.util/
│   │   ├── Error.kt
│   │   ├── NetworkError.kt
│   │   └── Result.kt
│   └── presentation.util/
│       └── NetworkErrorToString.kt
├── di/
│   └── AppModule.kt
├── products/
│   ├── data/
│   │   ├── mappers/
│   │   └── networking/
│   ├── domain/
│   │   ├── Products.kt
│   │   └── ProductsDataSource.kt
│   └── presentation/
│       ├── core/
│       ├── navigation/
│       ├── product_details/
│       └── product_list/
├── ui.theme/
├── MainActivity.kt
└── ZyneticAssignmentApp.kt
```

---

## 🚀 Getting Started

### 🛠 Requirements

- Android Studio Hedgehog or newer
- Kotlin 2+
- Gradle 8.8.8+
- API 28+ Android device or emulator
- Internet connection

### 📦 Installation

```bash
git clone https://github.com/AbhranilNXT/ZyneticAssignmentApp.git
cd ZyneticAssignmentApp
```

1. Open in Android Studio
2. Let Gradle sync and build
3. Run the app on emulator or device

---

## 🌐 API

This app consumes the [DummyJSON Products API](https://dummyjson.com/docs/products)

- `GET /products`: List of products
- `GET /products/{id}`: Product details

All requests are handled via Ktor with manual query filtering for optimized payloads.

---

## 🎯 Notable Implementations

- **Custom Result Wrapper**: Clean handling of network errors with `Result<Success, Error>`.
- **Retry via Snackbar**: On error, users get a retryable Snackbar with localized messages.
- **Skeleton Shimmer**: Used while images load with graceful fallback.
- **Lifecycle-aware Collection**: `collectAsStateWithLifecycle()` ensures proper state updates.
- **Lottie Animations**: Used in both loading and error states for modern UI.

---

## ✅ Bonus Features

- [x] Responsive & polished UI with proper spacing, scaling
- [x] Retry logic and graceful error recovery
- [x] Proper handling of loading or error states

---

## 📸 Screenshots

![Product List Screen](https://i.postimg.cc/bY2F3TGw/Screenshot-20250409-225520.png)
![Product Details Screen P1](https://i.postimg.cc/yxB9hsg9/Screenshot-20250409-225639.png)
![Product Details Screen P2](https://i.postimg.cc/RVSb7PQs/Screenshot-20250409-225657.png)
![Loading Screen](https://i.postimg.cc/yNGTwH5x/Screenshot-20250409-225728.png)
![Error Screen](https://i.postimg.cc/Z52Gx1cP/Screenshot-20250409-225747.png)

---

