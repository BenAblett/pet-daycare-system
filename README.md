[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Xm4mANuE)
Name : Ben Ablett

Student Number: 20112142

---Chronology of Implementation---

I began by implementing the model classes (Pet, Mammal, Dog, Cat, Parrot, and Owner) as these form the foundation of the system. 
I focused first on constructors, validation rules, and basic getters/setters to ensure each class behaved correctly in isolation.
After that, I implemented the PetsDayCareAPI class, starting with basic CRUD operations (add, delete, update, and retrieval methods). 
Once CRUD was working, I moved on to the listing, searching, and counting methods.
Sorting methods were implemented next, ensuring they followed the requirement of not using built-in sorting utilities.
Finally, I implemented persistence using XStream (save() and load()), followed by building the Driver class to provide a menu.
Testing was developed alongside implementation, starting with model classes and then expanding to API-level tests to achieve high branch coverage.

---Main Difficulties and Solutions---

One of the main challenges was achieving high branch coverage, particularly for methods with multiple conditional paths (e.g., updatePet, findDogByOwnerAndBreedAndAge, and listing methods). 
Initially, some branches were not being triggered, even with many tests.
This was resolved by analysing each conditional path and writing targeted tests specifically designed to trigger missed branches (e.g., null inputs, edge cases, mixed data types, and boundary values).
Another difficulty was handling null values safely, especially in methods like findDogByOwnerAndBreedAndAge, where calling methods like toLowerCase() on null caused runtime errors. 
This was solved by adding defensive null checks before performing string operations.
Persistence also caused issues initially, particularly ensuring objects were correctly saved and reloaded.
This was resolved by verifying that all required classes were included in the XStream allow list and by debugging the load process using controlled test cases.

---Remaining Bugs / Unfinished Elements---

There are no major known functional bugs remaining in the system.
However, some minor improvements could still be made:
- The Driver class does not currently prevent duplicate pets (e.g., same ID) from being added.
- User input validation in the menu system could be stricter (e.g., enforcing only valid inputs for yes/no questions).
- Some output formatting could be improved for consistency.
These do not affect the core functionality of the system.

---Main Learnings---

This assignment significantly improved my understanding of:

- Object-oriented design and inheritance structures
- Writing meaningful unit tests with strong branch coverage
- Identifying and testing edge cases effectively
- The importance of defensive programming (especially null handling)
- Implementing persistence using external libraries

I also learned how to analyse code coverage reports and use them to guide test development, rather than just writing tests blindly.

References
Class notes and provided materials
Test cases were initially generated using ChatGPT a list of these prompts in chronological order can be found at the end of the document.
AI assistance (ChatGPT) was then used to:
Help identify missing branches in coverage
Suggest additional edge case tests
Review and refine test design
Debug specific issues (e.g., null handling and persistence)

All code submitted has been reviewed and understood.

Declaration
This is my work apart from the specific references noted above (and any code from class notes). I understand the code and can describe any parts of the solution if needs be.