# Baking App

## Project Overview

You will productionize an app, taking it from a functional state to a production-ready state. 
This will involve finding and handling error cases, adding accessibility features, allowing for localization, 
adding a widget, and adding a library.

## Why this Project?
As a working Android developer, you often have to create and implement apps where you are responsible for designing and planning the steps 
you need to take to create a production-ready app. Unlike Popular Movies where we gave you an implementation guide, 
it will be up to you to figure things out for the Baking App.

In this project you will:

* Use MediaPlayer/Exoplayer to display videos.
* Handle error cases in Android.
* Add a widget to your app experience.
* Leverage a third-party library in your app.
* Use Fragments to create a responsive design that works on phones and tablets.

App Description:

Your task is to create a Android Baking App that will allow Udacity’s resident baker-in-chief, Miriam, to share her recipes with the world. You will create an app that will allow a user to select a recipe and see video-guided steps for how to complete it.

[The recipe listing is located here.](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json)

The JSON file contains the recipes' instructions, ingredients, videos and images you will need to complete this project. 
Don’t assume that all steps of the recipe have a video. Some may have a video, an image, or no visual media at all.

One of the skills you will demonstrate in this project is how to handle unexpected input in your data -- 
professional developers often cannot expect polished JSON data when building an app.
________________________________________________________________________________________________________________________________
# My Project Completion

## Screenshots
![alt text](https://i.imgur.com/HxdC2kg.jpg?1)![alt text](https://i.imgur.com/agIhMr4.png?1) ![alt text](https://i.imgur.com/cgQev65.png?1)
![alt text](https://i.imgur.com/1HDMNWg.jpg?1)

## Libraries & Implementations & Additional Content
* [ButterKnife](https://github.com/JakeWharton/butterknife) 

* [Retrofit](http://square.github.io/retrofit/) 

* [Gson](https://github.com/google/gson) 

* [ExoPlayer](https://github.com/google/ExoPlayer)

* [Glide](https://github.com/bumptech/glide) 

* [CardView](https://github.com/codepath/android_guides/wiki/Using-the-CardView) 

* [Glide](https://github.com/bumptech/glide) 
________________________________________________________________________________________________________________________________

## Why this Project

To become an Android developer, you must know how to bring particular mobile experiences to life. Specifically, you need to know how to build clean and compelling user interfaces (UIs), fetch data from network services, and optimize the experience for various mobile devices. You will hone these fundamental skills in this project.

By building this app, you will demonstrate your understanding of the foundational elements of programming for Android. Your app will communicate with the Internet and provide a responsive and delightful user experience.
________________________________________________________________________________________________________________________________

# Popular Movies, Stage 1

## Project Overview

Most of us can relate to kicking back on the couch and enjoying a movieDetails with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing. We will split the development of this app in two stages. First, let's talk about stage 1.

In this stage you’ll build the core experience of your movies app.

You app will:

* Present the user with a grid arrangement of movieDetails posters upon launch.
* Allow your user to change sort order via a setting:
  * The sort order can be by most popular or by highest-rated
* Allow the user to tap on a movieDetails poster and transition to a details screen with additional information such as:
  * original title
  * movieDetails poster image thumbnail
  * A plot synopsis (called overview in the api)
  * user rating (called vote_average in the api)
  * release date

## What Will I Learn After Stage 1?

* You will fetch data from the Internet with theMovieDB API.
* You will use adapters and custom list layouts to populate list views.
* You will incorporate libraries to simplify the amount of code you need to write
