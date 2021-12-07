
# Introduction 
Weather App is an Android App has ability to display current weather conditions, integrated with https://openweathermap.org/api APIs

## Table of contents
* [General info](#general-info)
* [Pre-requisites](#pre-requisites)
* [Build requirements](#build-requirements)
* [Libraries used](#libraries-used)
* [Developing steps](#developing-steps)

## General info
This Application is written in Kotlin, and use MVVM as a design pattern.

## Pre-requisites
- Android Studio Arctic Fox | 2020.3.1 or higher

## Build requirements
-   minSdkVersion is API 21 (Lollipop 5.0) or higher.
-   Android SDK platform: API 31.

## Libraries Used
- [Foundation](https://developer.android.com/jetpack/components) - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.
	-   [AppCompat](https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat)  - Degrade gracefully on older versions of Android.
	-   [Android KTX](https://developer.android.com/kotlin/ktx)  - Write more concise, idiomatic Kotlin code.

- [Architecture](https://developer.android.com/jetpack/arch/) - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
	-   [Data Binding](https://developer.android.com/topic/libraries/data-binding/)  - Declaratively bind observable data to UI elements.
	-   [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle)  - Create a UI that automatically responds to lifecycle events.
	-   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - Build data objects that notify views when the underlying database changes.
	-   [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)  - Handle navigation between screens.
	-   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - Store UI-related data that isn't destroyed on app. Easily schedule asynchronous tasks for optimal execution.

- Third party and miscellaneous libraries
	- [Koin](https://insert-koin.io/): for dependency injection (service locator).
	- [RxJava2](https://github.com/ReactiveX/RxJava): for managing background threads with simplified code.
	- [Retrofit2](https://square.github.io/retrofit/): for consuming HTTP requests.

## Developing steps

### Add new screen to exits feature

Follow next steps:
1. Create new Fragment for the new screen  and make it extend from BaseFragment.
2. Create new View-Model for the Fragment, and make it extend from BaseViewModel.
3. In "di.AppModule.kt" add dependency injection for the new View-Model.
4. Find related Navigation graph in resource directory, then add the new Fragment on it.
5. Modify redirection in Navigation graph to show the new Fragment.
 
### Add new feature

Follow next steps:
1. Create new Activity that extend from BaseActivity.
2. Create new Fragment for the new screen  and make it extend from BaseFragment.
3. Create new View-Model for the Fragment, and make it extend from BaseViewModel.
4. In "di.AppModule.kt" add dependency injection for the new View-Model.
5. Create new Navigation graph in resource directory, then add the new Fragment on it.
6. Link the new Activity to the generated navigation graph.
