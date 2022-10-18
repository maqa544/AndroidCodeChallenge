# Razorsync Android Code Challenge

## Description
The application in this repository currently loads a list of pokemon from the [PokèApi](https://pokeapi.co/docs/v2).
However the application loads this data from the API everytime the application is launched, or brought
to the foregroun.
## Taks
1. Modify the application to allow it to show the list of Pokemon without having to
   reload the data from the API everytime the application is started, ie. Cache the results of the
   API. Feel free to do this any way you see fit, however a Room Database instance has been provided to
   you for your convenience.

2. Your Second task is to show all of the pokemon the API has instead of the first 20. Feel free to do
   this however you see fit. The performance of your implementation will be scrutinized.

3. Your Optional bonus task is to add the ability to favorite a pokemon by tapping them on the list,
   and persisting the favorite status. This task will require more application wide changes then just
   this file.

## Rules

Feel free to make changes to any file in the application you need to complete the tasks.
You may include and use any additional first or third party libraries to help you.

## Submission
Fork this repository and work from the forked repository. Commit and push your changes to the forked repository.
Submit your repository link to the Hiring Manager.

## Resources
[Paging Library](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0)

[PokèApi](https://pokeapi.co/docs/v2)

[App Architecture](https://developer.android.com/topic/architecture)

[Repository Architecture](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0)

[Room](https://developer.android.com/training/data-storage/room)
