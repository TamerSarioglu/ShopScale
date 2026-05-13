# ShopScale

ShopScale is a modular Android shopping app built with Kotlin, Jetpack Compose, Hilt, Retrofit, Room, DataStore, and an MVI-style presentation layer.

The app uses the Platzi Fake Store API for authentication, user registration, categories, products, product filtering, and product detail data.

## Features

- Login with token persistence
- Register new user
- Splash routing based on authentication state
- Product list with category chips and filters
- Product detail screen
- Settings/profile screen
- Logout
- Offline-backed product list using Room

## Architecture

The project follows a feature-based modular structure. Feature modules are organized around:

- `presentation`: Compose screens, contracts, ViewModels
- `domain`: models, repositories, use cases
- `data`: remote APIs, DTOs, mappers, repository implementations
- `di`: Hilt bindings and providers

ViewModels depend on use cases, not Retrofit APIs or DTOs. Network and persistence exceptions are converted to `Result` in repository implementations, then consumed with `onSuccess` / `onFailure` in ViewModels.

## Modules

```text
app
core:common
core:network
core:datastore
core:database
feature:auth
feature:register
feature:product
feature:productdetail
feature:settings
```

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- Retrofit
- Kotlinx Serialization
- OkHttp
- Room
- DataStore
- Coil
- Coroutines / Flow

## API

Base URL:

```text
https://api.escuelajs.co/api/v1/
```

Main endpoints used:

- `POST /auth/login`
- `POST /auth/refresh-token`
- `POST /users/`
- `GET /auth/profile`
- `GET /products`
- `GET /products/{id}`
- `GET /categories`

Product filters are sent as query parameters to `/products`, including:

- `title`
- `price_min`
- `price_max`
- `categoryId`
- `categorySlug`
- `limit`
- `offset`

## Requirements

- Android Studio
- JDK 21
- Android SDK 36

## Build

Compile the main dev debug variant:

```powershell
.\gradlew.bat :app:compileDevDebugKotlin
```

Build the dev debug APK:

```powershell
.\gradlew.bat :app:assembleDevDebug
```

Run tests:

```powershell
.\gradlew.bat test
```

## Project Notes

- The app uses Gradle convention plugins from `build-logic`.
- Product data is cached in Room after remote sync.
- Auth tokens are stored through `core:datastore`.
- `core:network` owns Retrofit, OkHttp, token injection, and token refresh.
- Feature modules own their own domain/data/presentation boundaries.
