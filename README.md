# Brewery Production Management System

## Context
You are building a production management system for a craft brewery. The brewery produces different types of beer and needs to track production, inventory, and brewer schedules.

## Exam Rules & Circumstances
- You have **3 hours** to complete this assignment.
- While developing your solution, you must follow proper git commit practices, ensuring that your commits are atomic and descriptive. (Please note: submitting the final solution in a single commit is not acceptable.)
- This is an individual task. Do not seek assistance from AI tools or any external sources.
- You must submit source code that you fully understand and are able to explain in detail.

## Your Tasks

### Task 1: Produce Beer!
The starter code contains a basic `Beer` class. However, beers can be of different types (Lager or Ale) and each type has different characteristics:
- **Lagers** require **cold** storage (below 5°C) and have longer fermentation times (21 days)
- **Ales** can be stored at **room temperature** and have shorter fermentation times (14 days)

Design and implement a proper type system for beers that allows the brewery to:
- Store beer-specific information (name, alcohol content, production date)
- Handle type-specific storage requirements
- Calculate the days until a beer is ready for sale (production date + fermentation time)

**Important:**
- Not all beers are suitable for storage at the same temperature. Your design should prevent storing beers at incorrect temperatures.
- Beers alcohol content is represented as a percentage and should be between 0 and 100.

### Task 2: Hire Brewers!
The starter code has a basic `Brewer` class. However, the brewery employs different types of brewers:
- **Helper Brewers** work only on specific weekdays (e.g., Monday to Friday, or Monday and Wednesday)
- **Master Brewers** work all 7 days of the week
- All brewers have a name and years of experience

Extend the existing `Brewer` class to handle these different types. The system must be able to:
- Check if a specific brewer is available on a given day
- Track brewing experience
- Identify the role of each brewer
- Master Brewer _can brew_ a specific beer on a _given day_;
   - Each day's production is independent - beer can be fully produced in a single day
   - On each day, the master can produce as many batches (liters) as there are helpers available that day
      - Example: Master brewer requested to produce beer on Monday and Thursday
         - Monday: 3 helpers available → 3 batches can be produced on Monday
         - Thursday: 2 helpers available → 2 batches can be produced on Thursday
         - **Total production capacity for Monday and Thursday: 5 batches** (3 + 2)

### Task 3: Complete the Brewery Class
The `Brewery` class is partially implemented. You need to complete it with the following functionality:
- Add new brewers to the brewery
- Add newly produced beers to the stock
- Retrieve beers from stock by name and quantity
- Calculates the total production capacity across specific days
- Finds beers that are past their prime (older than 180 days)

**Important:**
- You have to design appropriate data structure to manage the stock of beers
- Only one Master Brewer can be assigned to the brewery at any time

- The provided `Beer`, `Brewer` and `Brewery` classes is partially implemented; you need to take care of
   - visibility,
   - constructors,
   - immutability,
   - null safety, and other OOP principles.
- Not all the required methods are mentioned with `//TODO` comments in the starter code!
- Not all the classes/interfaces/records/enums you need are provided; you may need to create additional ones.
- Partially implemented methods may not have complete signatures; you need to design the method signatures yourself.

### Task 6: Unit Testing
Create comprehensive tests for your solution!
There are some test already implemented in `BreweryTest`, but you need to extend or implement the following additional tests:
- `void setUp()`
- `void testProductionCapacityOnMultipleDays()`
- `void testProductionCapacityWithInvalidDay()`
- `testFindExpiredBeers()`
- `void testBeerFermentationDays()`
- `void testBeerStorageTemperature()`
- `testHelperBrewerAvailability()`
- `void testFindOptimalProductionDays()`

## Technical Requirements

- Use Java 21 features where appropriate
- Configure your own Maven dependencies
- Ensure proper encapsulation of all fields
- Use immutable objects where it makes sense
- Apply proper null safety practices
- Use Java Stream API for collection operations
- Write clean, maintainable code

## Deliverables (GitHub repository)
1. Completed Java source files with all functionality implemented
2. Maven `pom.xml` with properly configured dependencies
3. Test classes
4. All code should compile and run without errors