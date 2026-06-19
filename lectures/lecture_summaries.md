# SE317 Lecture Summaries

> **Textbook:** *Introduction to Software Testing, 2nd Edition* — Paul Ammann & Jeff Offutt
> **Companion:** *Pragmatic Unit Testing in Java 8 with JUnit* — Pragmatic Bookshelf (2015)

---

## Module 1 — Lab Setup (`317_lab_Setup.pptx`)

**Instructor:** Ashraf Gaffar

### Required Tools
| Tool | Notes |
|------|-------|
| Java 8 | Required version |
| Eclipse IDE | Primary IDE for course |
| JUnit | Download from GitHub (junit-team/junit) |
| IntelliJ IDEA / NetBeans | Optional alternatives |

### Eclipse Project Setup
1. **Create a `test` source folder** — right-click project → New → Source Folder → name it `test`
   - Keep production code in `src`, test code in `test` (same package `iloveyouboss`)
2. **Create a JUnit test class** — select `ScoreCollection.java` → right-click → New → JUnit Test Case
3. **Add JUnit to the build path** — use the wizard to configure the classpath

---

## Module 2 — Why Do We Test Software? (`Module_2_Ch_1_whyTest_Ga.pptx`)

**Source:** Ch. 1 — *Introduction to Software Testing, 2nd ed.*

### Why Testing Matters
- Modern software defines ultra-complex behavior (embedded systems, enterprise apps, web apps)
- Agile processes put increased pressure on testers — programmers must unit test with little training
- 2016 data: 606 reported software failures impacted half the world's population and cost **$1.7 trillion USD**

### Key Terminology

| Term | Definition |
|------|-----------|
| **Fault** | A static defect in the source code (latent bug) |
| **Error** | An incorrect internal state caused by a fault being triggered |
| **Failure** | External, incorrect behavior visible to the user |
| **Bug** | Informal, imprecise — avoid in technical contexts |

> **Example:** A loop starting at `i=1` instead of `i=0` is a **fault**. When a zero is at index 0, `i=1` causes an **error** in the count variable. The wrong return value is the **failure**.

### Famous Software Failures
- **THERAC-25** — poor safety testing killed 3 patients
- **Ariane 5** — 64-bit to 16-bit conversion bug caused self-destruct (~$370 million lost)
- **NASA Mars Lander (1999)** — units integration fault caused crash
- **Boeing 737 Max** — aggressive MCAS software overrides caused crashes
- **Northeast Blackout** — alarm system failure, 50 million people affected, $6B lost

### Testing Maturity Levels

| Level | Mindset |
|-------|---------|
| **0** | No difference between testing and debugging — "just get it to compile" |
| **1** | Purpose is to show correctness (but correctness is impossible to prove) |
| **2** | Purpose is to show the software *doesn't* work — adversarial dynamic |
| **3** | Testing reduces *risk* — testers and developers cooperate |
| **4** | Testing is a mental discipline that increases overall software quality |

### Verification & Validation (IEEE)
- **Verification** — does the product fulfill requirements from the *previous* phase?
- **Validation** — does the finished software comply with its *intended usage*?

### Cost of Late Testing
- Faults found during **system test** cost ~100× more to fix than faults found at **unit test**
- Faults found **post-deployment** cost ~360× more
- "Testing is too expensive" — the *lack* of testing is even more expensive

---

## Module 3 — Model-Driven Test Design (`Ch02-mdtd_class.pptx`)

**Source:** Ch. 2 — *Introduction to Software Testing, 2nd ed.*

### Core Principle
> Testing can only show the **presence** of failures — not their absence.

### Testing vs. Debugging
- **Testing** — evaluating software by observing its execution
- **Debugging** — finding the fault *given* an observed failure
- Not all inputs will trigger a fault into causing a visible failure

### The RIPR Fault/Failure Model
All four conditions must hold for a failure to be observed:

| Step | Condition |
|------|-----------|
| **R**eachability | The faulty code location must be *executed* |
| **I**nfection | Execution of the fault must cause an *incorrect internal state* |
| **P**ropagation | The infected state must affect an *output or final state* |
| **R**eveal | The tester must *observe* the incorrect output |

### Traditional vs. OO Testing Levels

**Traditional levels** (coarse to fine):
- Acceptance → System → Integration → Module → Unit

**Object-Oriented levels:**
- **Intra-method** — test each method individually
- **Inter-method** — test pairs of methods in the same class
- **Intra-class** — test an entire class via sequences of calls
- **Inter-class** — test multiple classes together

### Why Coverage Criteria?
- Even small programs have astronomical input spaces (a 3-variable 32-bit function has >80 octillion inputs)
- Coverage criteria provide structured, practical ways to search the input space
- Benefits: traceability, regression testing, clear stopping rule, tool support

### Black-Box vs. White-Box vs. Model-Based
| Approach | Source of Tests |
|----------|----------------|
| **Black-box** | External specs, requirements, design |
| **White-box** | Internal source code (branches, conditions, statements) |
| **Model-based** | Abstract model (e.g., UML diagram) |

MDTD makes these distinctions less important — the real question is *at what abstraction level* do we derive tests?

### Four Test Activities & Required Skills

| Activity | Description | Key Skills |
|----------|------------|------------|
| **Test Design (Criteria-based)** | Design test values to satisfy coverage criteria | Discrete math, programming, testing theory |
| **Test Design (Human-based)** | Design tests from domain knowledge | Domain expertise, HCI, psychology, logic |
| **Test Automation** | Embed test values into executable scripts | Programming; less theory needed |
| **Test Execution** | Run tests and record results | Basic computer skills (can be interns) |
| **Test Evaluation** | Evaluate results, report to developers | Domain expertise, empirical background |

> **Key insight:** Using the same people for all four activities wastes specialized talent. A mature test org needs one test designer working with several automators, executors, and evaluators.

### Model-Driven Test Design Process Flow
```
Software Artifact
    → Model/Structure
    → Test Requirements
    → Refined Requirements / Test Specs
    → Input Values → Test Cases → Test Scripts
    → Execute → Results → Pass/Fail
```
MDTD lets one test designer do the math, then traditional testers/programmers handle finding values, automating, running, and evaluating.

---

## Modules 4 & 5 — Test Automation & JUnit (`Ch03-automation_JUnit.pptx`)

**Source:** Ch. 3 — *Introduction to Software Testing, 2nd ed.*
*(Module 5 is a continuation of the same slides)*

### What is Test Automation?
> The use of software to control test execution, compare actual vs. predicted outcomes, set up preconditions, and produce reports.

**Benefits:**
- Reduces cost and human error
- Reduces variance in test quality
- Significantly reduces cost of **regression testing**

### Software Testability
- **Observability** — how easy it is to observe outputs/effects of a program
  - Low when software affects hardware, remote files, or databases
- **Controllability** — how easy it is to provide a program with needed inputs
  - Data abstraction reduces both controllability and observability

### Anatomy of a Test Case

| Part | Definition |
|------|-----------|
| **Test case values** | The input values for one execution |
| **Expected results** | What the software *should* produce |
| **Prefix values** | Setup inputs to get software into the right state |
| **Postfix values** | Inputs sent *after* the test values (verification + exit values) |
| **Test oracle** | Uses expected results to decide pass/fail |

A **test set** is a collection of test cases; an **executable test script** is a test case prepared for automatic execution.

### JUnit Overview
- Open source Java testing framework (`junit.org`)
- Features: assertions, test fixtures, test suites, graphical/text runners
- Used for **unit and integration testing** (not system testing)
- Runs standalone from command line or inside Eclipse/IntelliJ

### JUnit Essentials

**Structure:**
```java
import static org.junit.Assert.*;
import org.junit.*;

public class MyTest {
    @Before public void setUp() { /* runs before each test */ }
    @After  public void tearDown() { /* runs after each test */ }

    @Test
    public void testSomething() {
        assertTrue("message on failure", condition);
    }
}
```

**Key annotations:**
| Annotation | Purpose |
|-----------|---------|
| `@Test` | Marks a method as a test case |
| `@Before` | Runs before every test method (prefix/setup) |
| `@After` | Runs after every test method (postfix/teardown) |
| `@Test(expected = X.class)` | Test passes only if exception X is thrown |

### Testing the `Min` Class — Full Example

The `Min.min(List)` method returns the minimum element. Seven tests cover all cases:

| # | Test | Type |
|---|------|------|
| 1 | `testForNullList` | Null list → `NullPointerException` |
| 2 | `testForNullElement` | Null element in list → `NullPointerException` |
| 3 | `testForSoloNullElement` | Single null element → `NullPointerException` |
| 4 | `testMutuallyIncomparable` | Mixed types → `ClassCastException` |
| 5 | `testEmptyList` | Empty list → `IllegalArgumentException` |
| 6 | `testSingleElement` | `["cat"]` → `"cat"` (happy path) |
| 7 | `testDoubleElement` | `["dog","cat"]` → `"cat"` (happy path) |

### Data-Driven Tests (`@Parameters`)
Avoids test code bloat when testing the same logic with many input sets:
```java
@RunWith(Parameterized.class)
public class DataDrivenCalcTest {
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {{1,1,2}, {2,3,5}});
    }
    @Test public void additionTest() { assertTrue(sum == Calc.add(a,b)); }
}
```

### JUnit Theories (`@Theory` + `@DataPoints`)
- Test methods that accept actual parameters
- Uses **Assume-Act-Assert** contract model
- `@DataPoints` provides all combinations of values; `assumeTrue()` filters invalid ones
- Useful for property-based / contract testing

```java
@Theory
public void removeThenAddDoesNotChangeSet(Set<String> set, String str) {
    assumeTrue(set != null && set.contains(str));  // Assume
    Set<String> copy = new HashSet<>(set);         // Act
    copy.remove(str); copy.add(str);
    assertTrue(set.equals(copy));                  // Assert
}
```

### Running Tests
- **IDE:** Green/red bar in Eclipse, no `main()` needed
- **Command line:** Use `AllTests` suite class with `junit.textui.TestRunner`
- On failure, JUnit reports the location and any thrown exceptions

### Key Takeaway
> Automation makes testing efficient, but it does **not** solve the hardest problem: *what test values to use*. That is the purpose of **test design and coverage criteria**.

---

*Summarized from SE317 lecture slides — Ashraf Gaffar*
