# How to implement MVP for Android

### What is MVP? ###

The MVP pattern allows separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen. Ideally the MVP pattern would achieve that same logic might have completely different and interchangeable views.

### Why use MVP? ###

In Android we have a problem arising from the fact that Android activities are closely coupled to both interface and data access mechanisms. We can find extreme examples such as CursorAdapter, which mix adapters, which are part of the view, with cursors, something that should be relegated to the depths of data access layer .
For an application to be easily extensible and maintainable we need to define well separated layers. What do we do tomorrow if, instead of retrieving the same data from a database, we need to do it from a web service? We would have to redo our entire view .
MVP makes views independent from our data source. We divide the application into at least three different layers, which let us test them independently. With MVP we are able to take most of logic out from the activities so that we can test it without using instrumentation tests.

### MVP Architecture ###

#### The presenter ####
The presenter is responsible to act as the middle man between view and model. It retrieves data from the model and returns it formatted to the view. But unlike the typical MVC, it also decides what happens when you interact with the view.
#### The View ####
The view, usually implemented by an Activity (it may be a Fragment, a View… depending on how the app is structured), will contain a reference to the presenter. Presenter will be ideally provided by a dependency injector such as Dagger, but in case you don’t use something like this, it will be responsible for creating the presenter object. The only thing that the view will do is calling a method from the presenter every time there is an interface action (a button click for example).
#### The model ####
In an application with a good layered architecture, this model would only be the gateway to the domain layer or business logic. If we were using the Uncle Bob clean architecture , the model would probably be an interactor that implements a use case. But this is another topic that I’d like to discuss in future articles. For now, it is enough to see it as the provider of the data we want to display in the view.

## Licence
```
Copyright 2017 Agileinfoways Pvt. Ltd.
Copyright 2017 The Android Open Source Project for AppRateAlert

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
