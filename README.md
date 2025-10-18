# NIT3213 – Assignment 2 (s8140457)

Android app that authenticates to the course API and shows an Art dashboard list.  
Stack: Kotlin, MVVM, Hilt DI, Retrofit/Moshi, Coroutines/LiveData, Navigation (Safe Args), RecyclerView (ListAdapter + DiffUtil), ViewBinding.

## Screens
- **Login** → POST `/sydney/auth` with `username` + `password`  
- **Dashboard** → GET `/dashboard/art` (requires `keypass` from login)  
- **Details** → (wired via Safe Args; shows full item info)

## Architecture
app/
├─ data/
│ ├─ remote/ # Retrofit ApiService + models (Entity, LoginRequest, LoginResponse)
│ └─ repository/ # DashboardRepository + Result<T> sealed class
├─ di/ Hilt modules (NetworkModule, RepositoryModule)
├─ ui/
│ ├─ login/ # LoginFragment + LoginViewModel
│ ├─ dashboard/ # DashboardFragment + DashboardViewModel (+ in-file EntityAdapter)
│ └─ details/ # DetailsFragment
├─ App.kt # @HiltAndroidApp
└─ MainActivity.kt # @AndroidEntryPoint, NavHost

## Tech
- **Kotlin** + Coroutines/LiveData
- **Hilt** for dependency injection
- **Retrofit** + **Moshi** for networking/JSON
- **Navigation** (Safe Args) for type-safe screen args
- **RecyclerView** (ListAdapter + DiffUtil)
- **ViewBinding** across fragments

## API
- `POST https://nit3213api.onrender.com/sydney/auth`
  - Body: `{ "username": "Santiago", "password": "8140457" }`
  - Response: `{ "keypass": "..." }`
- `GET https://nit3213api.onrender.com/dashboard/art`
  - No query/body required in this API version; returns:
  ```json
  {
    "entities": [
      { "artworkTitle":"Mona Lisa", "artist":"Leonardo da Vinci", "medium":"Oil paint", "year":1503, "description":"..." },
      ...
    ],
    "entityTotal": 7
  }

#How to run
- Android Studio Giraffe+ (AGP 8.6.x) with compileSdk 35.
- Open the project → Sync Gradle.
- Run on an emulator (e.g., Medium Phone API 24 or later).
- Login with:
- Username: Santiago
- Password: 8140457

You should see the Art list after login.
  
