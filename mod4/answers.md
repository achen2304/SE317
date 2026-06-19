## Q1:

1. JUnit creates a new `ProfileTest` instance
2. JUnit calls `@Before`'s `create()` --> initializes profile, question, criteria
3. JUnit runs `matchAnswersTrueForAnyDontCareCriteria()` --> passes
4. JUnit creates a new `ProfileTest` instance (fresh state)
5. JUnit calls `@Before`'s `create()` again --> initializes profile, question, criteria
6. JUnit runs `matchAnswersFalseWhenMustMatchCriteriaNotMet()` --> passes

## Q2

The inlined version (in the code) minimizes test interdependency because:

- Each test has a clean state created by @Before
- There are no shared local variables
