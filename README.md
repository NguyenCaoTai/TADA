# TADA

* Supported OS: from Android 5.0
* Supported devices (phone only)
* Supported features:
    + Booking two place by dragging map
      
* How to setup, and run app.
    + Checkout the source code from master branch.
    + Register 2 API keys(google map API, Air quality API), and saving correspond both API key in local.properties at root of project.
        - Register the google map api key, and enable APIs (Maps SDK for Android). And saving as "MAPS_API_KEY" variable config, ex: MAPS_API_KEY="api_key"
        - Register the API key for The Movie Database API at https://aqicn.org/api/ And saving as "AQI_API_KEY" variable config, ex: AQI_API_KEY="api_key"
      
    + Open the project by Android Studio, build, run app, and make the toast. :)
