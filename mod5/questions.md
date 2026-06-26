**1. What is the difference between testing the 7 inputs in a sequence and testing them individually. How are the two test cases designed? (Use narrative description, no test code needed.)**

The difference in testing sequentially and individually mainly comes from what is actually being tested. As the name implies, testing individually means testing one thing at a time, to make sure it works. This allows the tester to see exactly what portion of code and inpu causes a bug. Sequential tests on the other hand test the device in it's entirity and through the real world use, where it will be on for long periods of time without resetting. They are basically designed for testers to find bugs vs. validating it would work in the real world

**2. As the same person who developed, refactored, and tested the code, does your refactored code make it easier or harder to test the system, explain with examples.**

The refactored code makes it easier to test. Since both humidity and temperature now share the same SensorState logic, I only need to verify that logic once. For example, testing that max updates correctly for humidity also means that it works for temperature, since they run the exact same code. In the original version, I would have had to write separate tests for the duplicated humidity and temperature methods even though they did the same thing.

**3. Does your refactored code parametrize your tests? Explain.**

Yes, because humidity and temperature share the same SensorState class, the Style 2 parameterized tests can pass both values in a single row of data and test both sensors at once. In the original code, humidity and temperature had separate methods with separate logic, so parameterizing a test across both sensors would have been awkward since the inputs and expected outputs were tied to different methods.

**4. If you received the refactored code (written by another developer) to just test it, would it be easier or harder than case iv) above?**

It would be easier. The refactored code has a single SensorState class with clearly defined behavior for current, max, min, and trend. As a tester I can write tests against that one class and both humidity and temperature would work, as they use the same code. With the original duplicated code I would have had to look through two separate sets of methods to understand what each one does and write tests for each one. There also is just less code to write tests for in general.

**5. Would you prefer to test the original code or the refactored code, if both were written by another developer?**

The refactored code. With the original, any bug in the max, min, or trend logic would appear in both the humidity and temperature methods separately, making it harder to find. The refactored version has the logic in SensorState, so a failing test would be from the combined class directly, leading to less searching for the spot where the bug is.
