# search-places
Application for searching places in Seattle area. Created with MVVM architecture, Dagger 2, Android Architecture Components, RxJava, Retrofit etc.

# Prerequisite
1. Android studio 3.4
2. Gradle 3.4.0
3. Java

# Assumptions made
1. No user login/session.
2. Saving app data in application internal memory without any encryption.
3. No proguard configuration.
4. No search history.
5. No offline mode.
6. No tab & landscape mode support.

# Libraries used
1. AndroidX - for backward compatibility of widgets/API's, architecture components and other Android components/feature.
2. Material - for backward compatibility of material design.
3. Dagger   - for dependency injection. This improves the code test coverage.
4. ConstraintLayout - for flat view hierarchy.          
5. Glide    - for image loading and caching.                  
6  Navigation - for navigation between fragments.
7. Coordinatorlayout - for coordinate the animations and transitions of the views 
8. Lifecycle :- for viewmodel, livedata and reactive stream. Mainly used in UI layer.
9. Paging - for pagination support.
10. RxJava - for ease of operations and reactive programing. Mainly used in repo layer.
11. Retrofit - for networking.
12. Eventbus - for passing data events between different components.
13. Test dependencies - for unit testing.

# Permission used
ACCESS_NETWORK_STATE - to check internet availability.                       
INTERNET - for networking.

# Notes
I have added unit test case for only one class due to time limitations. But code has been wrriten in a such way that all core logic can be tested alone. 
