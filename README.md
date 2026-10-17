# Local Events Explorer

Local Events Explorer is an Android app that helps users discover local events, view event details, and save their favourite events for later.

The app is built using Kotlin and Jetpack Compose. It uses a local JSON Server with a db.json file as the mock backend for event data.

### Features
- Browse local events
- View event details
- Bookmark favourite events
- Save bookmarks locally
- Open event locations in Maps
- Cache events for 5 minutes to reduce unnecessary API requests
- Use WorkManager to refresh event data in the background, approximately every 6 hours
- Load event images using Coil

### Tech Stack
Kotlin, Jetpack Compose, MVVM, Retrofit, Room, Hilt, and Coroutines.

### Mock API Setup

The app uses JSON Server to provide event data from a local db.json file.

Start the server from the folder containing db.json:

npx json-server --watch db.json --host 0.0.0.0 --port 3000

For the Android emulator, configure the API base URL as:

http://10.0.2.2:3000/

Make sure the JSON Server is running before launching the app.

### Getting Started
Clone the repository, open the project in Android Studio, sync Gradle, and run the app on an emulator or Android device.
