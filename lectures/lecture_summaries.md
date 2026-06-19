# SE317 Lecture Summaries

> **Textbook:** *Introduction to Software Testing, 2nd Edition* — Paul Ammann & Jeff Offutt
> **Companion:** *Pragmatic Unit Testing in Java 8 with JUnit* — Pragmatic Bookshelf (2015)
> **Instructor:** Ashraf Gaffar

---

## Module 1 — Lab Setup (`317_lab_Setup.pptx`)

### Required Textbook

*Pragmatic Unit Testing in Java 8 with JUnit*, 1st Edition
- Publisher: Pragmatic Bookshelf (March 31, 2015)
- ISBN-10: 1941222595 | ISBN-13: 978-1941222591
- 236 pages

### Required Tools

| Tool | Source |
|------|--------|
| **Java 8** | java.com/download |
| **Eclipse IDE** | eclipse.org/downloads/ |
| **IntelliJ IDEA** | jetbrains.com/idea/download/ *(optional)* |
| **NetBeans** | netbeans.org/downloads/ *(optional)* |
| **JUnit** | github.com/junit-team/junit/wiki/Download-and-Install |

### Project Structure

Tests and production code live in the same **package** (`iloveyouboss`) but in **separate source folders**:
- `src/` — production code (e.g., `ScoreCollection.java`)
- `test/` — test code (e.g., `ScoreCollectionTest.java`)

This separation keeps tests out of the production build while sharing the same package namespace.

### Eclipse Setup Steps

**Step 1 — Create the `test` source folder:**
1. In Package Explorer, select the project
2. Right-click → New → Source Folder
3. Type `test` as the Folder Name → click Finish

**Step 2 — Create a JUnit test class:**
1. Select `ScoreCollection.java` in Package Explorer
2. Right-click → New → JUnit Test Case
3. Use the wizard to generate the test class skeleton

**Step 3 — Add JUnit to the classpath:**
- Eclipse provides a wizard to add the JUnit library to the project build path
- After this, you can run test classes using Run As → JUnit Test

---

## Module 2 — Why Do We Test Software? (`Module_2_Ch_1_whyTest_Ga.pptx`)

**Source:** Ch. 1 — *Introduction to Software Testing, 2nd ed.*

### Why Testing Is Increasingly Important

Modern software is uniquely complex compared to other engineering products:
- Software defines behavior rather than physical structure — its behavior is **intangible and emergent**
- Infrastructure: network routers, financial systems, switching networks
- Embedded control: airplanes, air traffic control, medical devices, consumer electronics
- Agile processes push testing responsibility onto developers who often have **no formal testing education**
- Tests are key to functional requirements — but who builds those tests?

> *"Software is a skin that surrounds our civilization."* — Dr. Mark Harman

### Key Terminology (Precise Definitions)

The word **"bug"** is informal and imprecise — speakers often don't know if they mean a fault, error, or failure. This course uses precise terms:

| Term | Definition | Example |
|------|-----------|---------|
| **Fault** | A static defect in the software code (latent — doesn't cause harm until triggered) | Loop starting at `i=1` instead of `i=0` |
| **Error** | An incorrect internal state that is the manifestation of a fault being executed | `i` is `1` not `0` on first iteration; `count` gets the wrong value |
| **Failure** | External, incorrect behavior visible to users — the output/result is wrong | `numZero` returns `0` instead of `1` for input `[0, 2, 7]` |

Note: Software does not physically degrade, but it "ages" — faults in software are equivalent to design/construction mistakes in hardware.

### Concrete Example: `numZero`

```java
public static int numZero(int[] arr) {
    // Effects: If arr is null throw NullPointerException
    //          else return the number of occurrences of 0 in arr
    int count = 0;
    for (int i = 1; i < arr.length; i++) {  // FAULT: should start at 0
        if (arr[i] == 0) { count++; }
    }
    return count;
}
```

| Test Input | Expected | Actual | Fault reached? | Error occurs? | Failure? |
|-----------|---------|--------|---------------|--------------|---------|
| `[2, 7, 0]` | 1 | 1 | Yes | Yes (i starts at 1) | **No** — error doesn't propagate |
| `[0, 2, 7]` | 1 | 0 | Yes | Yes (i starts at 1, misses index 0) | **Yes** — count returns 0 |

This illustrates that a fault doesn't always cause a failure — the error must propagate to an output the tester can observe.

### The Term "Bug" — Historical Context

- **Thomas Edison** (1878): *"Bugs — as such little faults and difficulties are called — show themselves and months of intense watching, study and labor are requisite."*
- **Ada Lovelace**: described sources of error in Babbage's Analytical Engine

### Spectacular Software Failures

| Failure | What Happened | Cost |
|---------|-------------|------|
| **Intel Pentium FDIV fault** | Floating-point division errors; public relations disaster | — |
| **THERAC-25** | Radiation machine; poor testing of safety-critical software killed 3 patients | Human lives |
| **Ariane 5** | Exception-handling bug: 64-bit to 16-bit conversion error caused self-destruct on maiden flight | ~$370 million |
| **NASA Mars Polar Lander (1999)** | Crashed due to units integration fault | ~$100 million |
| **Healthcare.gov** | Crashed repeatedly on launch — never load tested | — |
| **Boeing A220** | Engines failed after software update allowed excessive vibrations | — |
| **Toyota brakes** | Dozens dead, thousands of crashes | — |
| **Northeast Blackout** | 50 million people without power, $6B USD — alarm system failed | $6 billion |
| **Boeing 737 Max** | Crashed due to overly aggressive MCAS software flight overrides | Human lives |

### True Cost of Software Failure

2016 analysis by Fail Watch:
- **606** reported software failures
- Impacted **half the world's population**
- Cost a combined **$1.7 trillion USD**
- Poor software is a significant drag on the world's economy

### Testing Goals by Maturity Level

| Level | Mindset | Problem |
|-------|---------|---------|
| **Level 0** | No difference between testing and debugging — "just get it to compile and run" | Doesn't help develop reliable or safe software |
| **Level 1** | Testing shows *correctness* | Correctness is impossible to achieve; no stopping rule; bad tests look the same as good tests |
| **Level 2** | Testing shows the software *doesn't work* — look for failures | Adversarial: failures = bad developers, no failures = bad testers; doesn't build teamwork |
| **Level 3** | Testing *reduces risk* — probability × cost of damage | Testers and developers cooperate; acknowledges we can't find all failures |
| **Level 4** | Testing is a *mental discipline* that increases quality across the whole team | Test engineers become technical leaders; experts help developers rather than police them |

### Verification & Validation (IEEE Definitions)

- **Verification** — "Are we building the product right?" Does the current phase's output fulfill the requirements from the *previous* phase?
- **Validation** — "Are we building the right product?" Does the finished software comply with the user's *intended usage*?
- **IV&V** — Independent Verification and Validation (done by a separate team)

### Cost of Late Testing

Based on Carnegie Mellon SEI data (unit cost per fault, assuming 100 faults at $1,000 each):

| Phase where fault is **detected** | Relative cost |
|-----------------------------------|--------------|
| Requirements | $6K |
| Design | $13K |
| Programming/Unit Test | $20K |
| Integration Test | $100K |
| System Test | $250K |
| Post-Deployment | $360K |

Key insight: Most faults **originate** during Requirements and Design, but most are **detected** during Integration and System Test — far too late. Early testing is dramatically cheaper.

> *"Testing is too expensive."* — Poor program managers  
> Reality: **Not testing is even more expensive.** The cost is invisible and delayed, not zero.

### Summary: Why Do We Test?

A tester's goal is to **eliminate faults as early as possible** to:
- Improve quality
- Reduce cost
- Preserve customer satisfaction

---

## Module 3 — Model-Driven Test Design (`Ch02-mdtd_class.pptx`)

**Source:** Ch. 2 — *Introduction to Software Testing, 2nd ed.*

### Complexity of Testing Software

No other engineering field builds products as complicated as software. The term *correctness* has no objective meaning for software (unlike a building or car). Like other engineers, testers must use **abstraction** to manage complexity. This is the purpose of **Model-Driven Test Design (MDTD)** — the model is an abstract structure derived from the software.

### Core Principle

> *Testing can only show the **presence** of failures — not their absence.*

### Testing vs. Debugging

| Concept | Definition |
|---------|-----------|
| **Testing** | Evaluating software by observing its execution |
| **Test Failure** | Execution of a test that results in a software failure (this is a *good* thing — we found something) |
| **Debugging** | The process of finding a fault *given* a known failure |

Not all inputs will trigger a fault into causing a failure. You can have a fault that never causes a failure under normal usage.

### The RIPR Fault/Failure Model

Four conditions must **all** hold for a failure to be observable by a tester:

| Condition | Description |
|-----------|------------|
| **R**eachability | The location in the program containing the fault must be reached (executed) |
| **I**nfection | The state of the program must become incorrect as a result of executing the fault |
| **P**ropagation | The infected (incorrect) state must propagate to affect some output or final state |
| **R**evealability | The tester must actually observe the incorrect portion of the program state |

Flow: Test → reaches Fault → Infects Program State → Propagates to Final State → Tester Observes Failure

After observing a failure: **Uncover** the fault, **Understand** it, **Fix** it.

### Traditional Testing Levels

From high-level (coarse) to low-level (fine):
- **Acceptance Testing** — is the software acceptable to the user?
- **System Testing** — tests overall functionality of the entire system
- **Integration Testing** — tests how modules interact with each other
- **Module Testing** (developer testing) — tests each class, file, module, or component
- **Unit Testing** (developer testing) — tests each individual method

### Object-Oriented Testing Levels

OO systems are better described by these levels:
- **Intra-method testing** — test each method individually
- **Inter-method testing** — test pairs of methods in the same class
- **Intra-class testing** — test an entire class as sequences of method calls
- **Inter-class testing** — test multiple classes working together

### Why We Need Coverage Criteria

Even small programs have too many inputs to test exhaustively:

```java
private static double computeAverage(int A, int B, int C)
```

- Each 32-bit variable has over **4 billion** possible values
- Three variables = **80 octillion** (80 × 10²⁷) possible test inputs
- Input space is effectively infinite

**The Switchboard Problem:** A 100-unit board (each on/off) has 2¹⁰⁰ = 1.27 × 10³⁰ combinations. At 1 million tests/second, you'd need more than **10,000 trillion years** to test all combinations (the universe is ~13.8 billion years old).

**Advantages of Coverage Criteria:**
- Maximize "bang for the buck" — fewest tests that find the most problems
- Provide traceability from software artifacts to tests
- Make regression testing easier and repeatable
- Give testers a clear **stopping rule**
- Can be supported by powerful tools

### Test Requirements and Criteria

- **Test Criterion** — a collection of rules that define test requirements (e.g., "cover every statement", "cover every branch")
- **Test Requirements** — specific things that must be satisfied during testing (each statement is a test requirement under statement coverage)

All testing criteria fall into four types of structures:
1. **Input domains**
2. **Graphs**
3. **Logic expressions**
4. **Syntax descriptions**

### Black-Box vs. White-Box vs. Model-Based Testing

| Approach | Tests Derived From | Typical Use |
|----------|-------------------|------------|
| **Black-box** | External descriptions: specs, requirements, design | Higher levels (user, system, UX testing) |
| **White-box** | Source code internals: branches, conditions, statements | Lower levels (unit and integration testing) |
| **Model-based** | A model of the software (UML diagrams, etc.) | Both high and low levels |

MDTD makes these distinctions less important. The real question is: *from what abstraction level do we derive tests?*

### Four Types of Test Activities

Testing work breaks into four distinct activities, each requiring **different skills**. Using the same people for all four wastes specialized talent.

#### 1. Test Design — Criteria-Based
- The most technically demanding job in software testing
- Analogous to **software architecture** on the development side
- Requires: discrete math, programming, knowledge of testing theory (much of a CS degree)
- Goal: design test values to satisfy coverage criteria or engineering goals

#### 2. Test Design — Human-Based
- Much harder than it looks to developers
- Criteria-based approaches are "blind" to special situations that domain experts notice
- Requires: domain knowledge, HCI/UX, psychology, logic, law, philosophy
- Requires **almost no traditional CS** — a biology or psychology background is more useful
- Goal: design tests based on domain knowledge and intuition

#### 3. Test Automation
- Slightly less technical than test design
- Requires: programming skills; very little theory
- Often involves solving hard problems related to **observability and controllability**
- Key question: who determines and embeds **expected outputs**? Test designers may not always know
- Goal: embed test values into executable scripts that run automatically

#### 4. Test Execution
- Easy and trivial if tests are well automated
- Requires: basic computer skills (interns, non-technical employees)
- If GUI tests aren't automated, requires a lot of careful manual labor
- Test executors must be **very meticulous** with bookkeeping
- Goal: run tests on the software and record results

#### 5. Test Evaluation
- Much harder than it appears
- Requires: domain knowledge, testing knowledge, UX/psychology understanding
- Nearly no traditional CS required
- Goal: evaluate test results, report to developers (Microsoft: **Report, Replicate, Repair**)

#### Team Organization

A mature test organization:
- One **test designer** can support several automators, executors, and evaluators
- Improved automation reduces the number of executors (theoretically to zero, practically not)
- Test evaluators must be free to **add domain-based tests** that blind engineering processes won't think of
- Putting the wrong people on the wrong tasks leads to inefficiency, low satisfaction, low performance

### Model-Driven Test Design (MDTD) Process

MDTD raises the abstraction level so one test designer can do the hard math, then others handle implementation.

```
Software Artifact
  ──[analysis]──► Model / Structure
  ──[criterion]──► Test Requirements
  ──[refine]────► Refined Requirements / Test Specs     ← DESIGN ABSTRACTION LEVEL
  ──[generate]──► Input Values
  ──[prefix/postfix/expected]──► Test Cases
  ──[automate]──► Test Scripts                          ← IMPLEMENTATION ABSTRACTION LEVEL
  ──[execute]───► Test Results
  ──[evaluate]──► Pass / Fail
        │
        └──[feedback]──► back to Software Artifact
```

#### Assigned Roles by Activity
- **Test Design** → criteria-based + human-based
- **Test Automation** → automators
- **Test Execution** → executors
- **Test Evaluation** → evaluators

### Illustrative MDTD Example: `indexOf`

```java
/** Return index of node n at first position, -1 if not present */
public int indexOf(Node n) {
    for (int i = 0; i < path.size(); i++)
        if (path.get(i).equals(n)) return i;
    return -1;
}
```

**Control Flow Graph nodes:** 1(init) → 2(i<size?) → 3(equals?) → 4(return i) / 5(return -1)

**Edge-Pair Coverage requirements** (paths of length 2):
1. `[1,2,3]`  2. `[1,2,5]`  3. `[2,3,4]`  4. `[2,3,2]`  5. `[3,2,3]`  6. `[3,2,5]`

**Test Paths that satisfy EPC:**
- `[1, 2, 5]` — empty path, n not found
- `[1, 2, 3, 2, 5]` — n found after looking at one element but not matching
- `[1, 2, 3, 2, 3, 4]` — n found on second element

The test designer produces these requirements; automators, executors, and evaluators handle the rest.

---

## Module 4 — Test Automation & JUnit (`Ch03-automation_JUnit.pptx`)

**Source:** Ch. 3 — *Introduction to Software Testing, 2nd ed.*

### What Is Test Automation?

> *The use of software to control the execution of tests, the comparison of actual outcomes to predicted outcomes, the setting up of test preconditions, and other test control and test reporting functions.*

**Benefits of automation:**
- Reduces cost
- Reduces human error
- Reduces variance in test quality between individuals
- **Significantly** reduces the cost of regression testing (running old tests after every change)

### Software Testability (3.1)

> *The degree to which a system or component facilitates the establishment of test criteria and the performance of tests to determine whether those criteria have been met.*

Plainly: **how hard it is to find faults** in the software.

Testability is dominated by two practical problems:
1. How to **provide** test values to the software
2. How to **observe** the results of test execution

#### Observability
*How easy it is to observe the behavior of a program in terms of its outputs, effects on the environment, and other hardware/software components.*

- Low observability: software that affects hardware devices, databases, or remote files
- High observability: software that outputs to screen or returns values directly

#### Controllability
*How easy it is to provide a program with the needed inputs, in terms of values, operations, and behaviors.*

- Easy to control: software with keyboard inputs
- Hard to control: software driven by hardware sensors or distributed systems
- **Data abstraction reduces both controllability and observability**

### Components of a Test Case (3.2)

A test case is a structured, multipart artifact:

| Component | Definition |
|-----------|-----------|
| **Test case values** | The input values needed to execute the software under test |
| **Expected results** | The output the software *should* produce if correct |
| **Prefix values** | Inputs needed to put the software in the correct state *before* the test |
| **Postfix values** | Inputs sent *after* the test values |
| — *Verification values* | Values needed to observe the test results |
| — *Exit values* | Values/commands needed to return the program to a stable state |
| **Test oracle** | The mechanism that uses expected results to judge pass/fail |

#### Building Up to a Test Set and Script

- **Test case** = test case values + prefix + postfix + expected results
- **Test set** = a collection of test cases
- **Executable test script** = a test case prepared to run automatically and produce a report

### Test Automation Framework (3.3)

> *A set of assumptions, concepts, and tools that support test automation.*

### JUnit

**JUnit** is an open source Java testing framework for writing and running repeatable automated tests.

Features:
- Assertions for testing expected results
- Test fixtures for sharing common test data across tests
- Test suites for organizing and running groups of tests
- Graphical and text-based test runners
- Widely used in industry
- Runs standalone (command line) or inside an IDE (Eclipse, IntelliJ)

JUnit is used for:
- Testing an entire object
- Testing part of an object (a method or interacting methods)
- Testing interaction between several objects

Primary use: **unit and integration testing** (not system testing)

### Writing Tests in JUnit

Each test method checks a condition using **assertions** from `junit.framework.Assert`:

```java
assertTrue(boolean condition)
assertTrue(String message, boolean condition)  // message printed on failure
fail(String message)                           // unconditionally fail a test
```

All assertion methods return `void`. The test runner collects pass/fail results and reports them.

### JUnit Test Fixtures

A **test fixture** is the shared state setup for tests — objects and variables used by more than one test.

```java
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class MinTest {
    private List<String> list;  // Test fixture — shared across tests

    @Before
    public void setUp() {
        list = new ArrayList<String>();  // Runs before every test method (prefix)
    }

    @After
    public void tearDown() {
        list = null;  // Runs after every test method (postfix) — redundant here
    }
}
```

Key annotations:
| Annotation | When it runs | Purpose |
|-----------|-------------|---------|
| `@Before` | Before *each* test method | Initialize/reset shared state (prefix values) |
| `@After` | After *each* test method | Clean up state (postfix values) |
| `@BeforeClass` | Once before *all* tests in the class | Expensive setup (e.g., DB connections) |
| `@AfterClass` | Once after *all* tests in the class | Expensive teardown |

### Simple JUnit Example

```java
// Production code
public class Calc {
    static public int add(int a, int b) { return a + b; }
}

// Test class
import org.junit.Test;
import static org.junit.Assert.*;

public class CalcTest {
    @Test
    public void testAdd() {
        assertTrue("Calc sum incorrect", 5 == Calc.add(2, 3));
        //          ↑ printed if fails   ↑ expected  ↑ actual
    }
}
```

### Full Example: Testing the `Min` Class

The `Min.min(List)` method returns the minimum element from a list.

```java
public static <T extends Comparable<? super T>> T min(List<? extends T> list) {
    if (list.size() == 0) throw new IllegalArgumentException("Min.min");
    Iterator<? extends T> itr = list.iterator();
    T result = itr.next();
    if (result == null) throw new NullPointerException("Min.min");
    while (itr.hasNext()) {
        T comp = itr.next();  // throws NullPointerException, ClassCastException as needed
        if (comp.compareTo(result) < 0) { result = comp; }
    }
    return result;
}
```

#### Seven Tests for `Min`

**Tests that expect exceptions (5):**

```java
// 1. Null list — uses try/catch + fail()
@Test
public void testForNullList() {
    list = null;
    try { Min.min(list); }
    catch (NullPointerException e) { return; }
    fail("NullPointerException expected");
}

// 2. Null element in multi-element list — uses @Test(expected=...)
@Test(expected = NullPointerException.class)
public void testForNullElement() {
    list.add(null); list.add("cat");
    Min.min(list);
}

// 3. Single null element
@Test(expected = NullPointerException.class)
public void testForSoloNullElement() {
    list.add(null);
    Min.min(list);
}

// 4. Mutually incomparable types (raw types bypass generics)
@Test(expected = ClassCastException.class)
@SuppressWarnings("unchecked")
public void testMutuallyIncomparable() {
    List list = new ArrayList();
    list.add("cat"); list.add("dog"); list.add(1);
    Min.min(list);
}

// 5. Empty list
@Test(expected = IllegalArgumentException.class)
public void testEmptyList() {
    Min.min(list);
}
```

**Happy path tests (2):**

```java
// 6. Single element
@Test
public void testSingleElement() {
    list.add("cat");
    Object obj = Min.min(list);
    assertTrue("Single Element List", obj.equals("cat"));
}

// 7. Two elements — verifies comparison logic
@Test
public void testDoubleElement() {
    list.add("dog"); list.add("cat");
    Object obj = Min.min(list);
    assertTrue("Double Element List", obj.equals("cat"));
}
```

### Data-Driven Tests with `@Parameters`

**Problem:** Testing the same function multiple times with similar inputs causes test code bloat.  
**Solution:** Data-driven unit tests run the same test logic over a collection of input/expected pairs.

```java
@RunWith(Parameterized.class)
public class DataDrivenCalcTest {
    public int a, b, sum;

    // Constructor called once per row of data
    public DataDrivenCalcTest(int v1, int v2, int expected) {
        this.a = v1; this.b = v2; this.sum = expected;
    }

    // Data table: {input1, input2, expected}
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
            {1, 1, 2},   // Test 1
            {2, 3, 5}    // Test 2
        });
    }

    @Test
    public void additionTest() {
        assertTrue("Addition Test", sum == Calc.add(a, b));
    }
}
```

JUnit instantiates `DataDrivenCalcTest` once per row and runs `additionTest()` for each.

### JUnit Theories (`@Theory` + `@DataPoints`)

Theories allow test methods to **accept parameters** and test general contracts/properties.

**Contract model:** Assume → Act → Assert

```java
@Theory
public void removeThenAddDoesNotChangeSet(Set<String> someSet, String str) {
    assumeTrue(someSet != null);                // Assume — precondition
    assumeTrue(someSet.contains(str));          // Assume — precondition
    Set<String> copy = new HashSet<>(someSet); // Act
    copy.remove(str);
    copy.add(str);
    assertTrue(someSet.equals(copy));           // Assert — postcondition
}
```

**Where data values come from — `@DataPoints`:**

```java
@DataPoints
public static String[] animals = {"ant", "bat", "cat"};

@DataPoints
public static Set[] animalSets = {
    new HashSet(Arrays.asList("ant", "bat")),
    new HashSet(Arrays.asList("bat", "cat", "dog", "elk")),
    new HashSet(Arrays.asList("Snap", "Crackle", "Pop"))
};
```

- JUnit tries **all combinations** (3 strings × 3 sets = 9 combinations)
- Combinations where `assumeTrue` is false are **skipped**
- In this example, 4 of 9 combinations satisfy both assumes

**Boilerplate required:**
```java
@RunWith(Theories.class)
public class SetTheoryTest { ... }
```

### Running JUnit Tests

**In an IDE (Eclipse):**
- No `main()` needed — right-click test class → Run As → JUnit Test
- Green bar = all pass, Red bar = at least one failure
- JUnit shows location and exception for each failure

**From the command line:**
```java
@RunWith(Suite.class)
@Suite.SuiteClasses({ StackTest.class })  // list all test classes here
public class AllTests {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
```

Two runner options:
- **Character-based** (`textui.TestRunner`) — command line output
- **GUI-based** (`swingui.TestRunner`) — graphical "Run" button, visual results

### Key Takeaway

> *The only way to make testing efficient as well as effective is to automate as much as possible. Test frameworks provide very simple ways to automate our tests. It is no "silver bullet," however — it does not solve the hard problem of testing: **what test values to use?** This is test design — the purpose of test criteria.*

---

## Module 5 — Graph Coverage Criteria (`+Ch07-1-2-overviewGraphCoverage_ClassShr.pptx`)

**Source:** Ch. 7.1–7.2 — *Introduction to Software Testing, 2nd ed.*

### Four Structures for Modeling Software

All testing criteria in this course are applied to one of four model types:

| Structure | Applied To |
|-----------|-----------|
| **Graphs** | Control flow, FSMs, statecharts, use cases, design structure, source code |
| **Logic** | DNF (disjunctive normal form) specs, source code conditions |
| **Input Space** | Input models, specifications |
| **Syntax** | Source code, design artifacts |

Graph coverage is the most commonly used — this module covers graphs.

### Graphs and Paths (7.1)

#### Formal Graph Definition

A graph G consists of:
- **N** — a non-empty set of **nodes**
- **N₀ ⊆ N** — a non-empty set of **initial nodes** (where execution starts)
- **N_f ⊆ N** — a non-empty set of **final nodes** (where execution ends)
- **E ⊆ N × N** — a set of **edges** (n_i, n_j), where n_i is the *predecessor* and n_j is the *successor*

A graph is **invalid** if N₀ is empty.

#### Paths

| Term | Definition |
|------|-----------|
| **Path** | A sequence of nodes [n₁, n₂, …, n_M] where each consecutive pair (nᵢ, nᵢ₊₁) is an edge |
| **Length** | The number of edges in a path (a single node = length 0) |
| **Subpath** | A subsequence of nodes from a path p that is itself a valid path |
| **Test Path** | A path that begins at an initial node (N₀) and ends at a final node (N_f) |

Every test case executes exactly one test path from start to finish.

#### SESE Graphs

**Single-Entry, Single-Exit** graphs have exactly one initial node and one final node. Example — double-diamond graph:

```
    1
   / \
  2   3
   \ /
    4
   / \
  5   6
   \ /
    7
```

Four possible test paths: `[1,2,4,5,7]`, `[1,2,4,6,7]`, `[1,3,4,5,7]`, `[1,3,4,6,7]`

#### Visiting vs. Touring

- **Visit** — a test path p *visits* node n if n appears anywhere in p; p *visits* edge e if e appears in p
- **Tour** — a test path p *tours* subpath q if q is a **subpath** of p (q appears contiguously within p)

Example — path `[1, 2, 4, 5, 7]`:
- Visits nodes: `{1, 2, 4, 5, 7}`
- Visits edges: `{(1,2), (2,4), (4,5), (5,7)}`
- Tours subpaths: `[1,2,4]`, `[2,4,5]`, `[4,5,7]`, `[1,2,4,5]`, `[2,4,5,7]`, `[1,2,4,5,7]`

#### Syntactic vs. Semantic Reach

- **Syntactic reach** — a subpath *exists* in the graph structure (you can draw a path to it)
- **Semantic reach** — a test *actually exists* that can execute that subpath

Dead code is syntactically reachable but not semantically reachable. This distinction matters for feasibility (section 7.3).

#### Deterministic vs. Non-Deterministic Software

- **Deterministic** — the same test always executes the same test path (many tests → one test path, many-to-one)
- **Non-deterministic** — the same test can execute different test paths on different runs (many tests → many test paths, many-to-many)

### Testing and Covering Graphs (7.2)

The testing process using graphs:
1. Develop a model of the software as a graph
2. Define **test requirements** — properties that test paths must satisfy
3. Select a **test criterion** — the rule that defines which test requirements must be met
4. A test set T **satisfies** criterion C if for every test requirement in TR, some test path in path(T) meets it

Two families of criteria:
- **Structural Coverage Criteria** — defined purely in terms of nodes and edges
- **Data Flow Coverage Criteria** — requires the graph to be annotated with variable references

### Structural Coverage Criteria

#### Node Coverage (NC)

> **NC:** TR contains each reachable node in G.

Test set T satisfies NC iff for every syntactically reachable node n, some test path in path(T) visits n.

#### Edge Coverage (EC)

> **EC:** TR contains each reachable path of **length up to 1** in G.

"Length up to 1" (not just "length 1") ensures EC subsumes NC even for graphs with a single node and no edges.

EC is **slightly stronger** than NC — they differ only when there is more than one path between two nodes (e.g., an if-else creates two edges between the same node pair).

**Example:**
```
    1
   / \
  2   |
   \ /
    3
```
- **NC:** TR = `{1, 2, 3}` → satisfied by test path `[1, 2, 3]`
- **EC:** TR = `{(1,2), (1,3), (2,3)}` → requires test paths `[1, 2, 3]` AND `[1, 3]`

#### Edge-Pair Coverage (EPC)

> **EPC:** TR contains each reachable path of **length up to 2** in G.

Requires consecutive pairs of edges (subpaths of length 2). "Length up to 2" handles graphs with fewer than 2 edges.

**Example** (graph with nodes 1–6, converging at node 4):
- EPC TR = `{[1,4,5], [1,4,6], [2,4,5], [2,4,6], [3,4,5], [3,4,6]}`

#### Complete Path Coverage (CPC)

> **CPC:** TR contains all paths in G.

**Infeasible** for graphs with loops — infinite number of paths. In practice, the tester must decide which paths to include (Specified Path Coverage).

### Structural Coverage Example

Graph with nodes 1–7, loop at nodes 5–6:

| Criterion | TR | Example Test Paths |
|-----------|----|--------------------|
| **NC** | `{1,2,3,4,5,6,7}` | `[1,2,3,4,7]`, `[1,2,3,5,6,5,7]` |
| **EC** | `{(1,2),(1,3),(2,3),(3,4),(3,5),(4,7),(5,6),(5,7),(6,5)}` | `[1,2,3,4,7]`, `[1,3,5,6,5,7]` |
| **EPC** | `{[1,2,3],[1,3,4],[1,3,5],[2,3,4],[2,3,5],[3,4,7],[3,5,6],[3,5,7],[5,6,5],[6,5,6],[6,5,7]}` | 4 test paths needed, including `[1,3,5,6,5,6,5,7]` to cover `[6,5,6]` |
| **CPC** | Infinite (due to loop) | Infinite test paths |

### Handling Loops in Graphs

If a graph has a loop, CPC is infinite. Historical evolution of approaches:

| Era | Approach |
|-----|---------|
| 1970s | Execute each cycle once (informal) |
| 1980s | Execute each loop exactly once (formalized) |
| 1990s | Execute loops 0 times, once, and more than once |
| 2000s | **Prime Paths** — the modern standard |

### Simple Paths and Prime Paths

| Term | Definition |
|------|-----------|
| **Simple Path** | A path where no node appears more than once (except possibly the first and last node being the same — a loop). No internal loops. |
| **Prime Path** | A simple path that does not appear as a **proper subpath** of any other simple path. These are the "maximal" simple paths. |

**Example** (cycle graph: 1→2→4→1, 1→3→4→1):

Simple paths include: `[1,2,4]`, `[2,4,1]`, `[4,1,2]`, `[1,2,4,1]`, etc.

Prime paths (the maximal ones):
`[1,2,4,1]`, `[1,3,4,1]`, `[2,4,1,2]`, `[2,4,1,3]`, `[3,4,1,2]`, `[3,4,1,3]`, `[4,1,2,4]`, `[4,1,3,4]`

#### Prime Path Coverage (PPC)

> **PPC:** TR contains each prime path in G.

Properties of PPC:
- **Finite** — even for graphs with loops, because prime paths are simple (no node repeated)
- Automatically subsumes NC and EC (every prime path visits all nodes and edges along it)
- **Does not always subsume EPC** — self-edges are a counterexample

**Self-edge counterexample (PPC ≠ EPC):**
```
  1 → 2 ⟳ → 3
```
- EPC requires: `[1,2,3]`, `[1,2,2]`, `[2,2,3]`, `[2,2,2]`
- PPC requires only: `[1,2,3]`, `[2,2]`
- `[1,2,2]` and `[2,2,3]` are not simple paths (node 2 repeats), so PPC doesn't require them

### Touring With Sidetrips and Detours

Prime paths have no internal loops, but a test path executing them might encounter an unavoidable loop. Three modes of touring:

| Mode | Requirement |
|------|------------|
| **Exact Tour** | q is a contiguous subpath of p — perfect match |
| **Tour With Sidetrip** | Every **edge** of q appears in p in the same order — p can take a loop and return to the same node |
| **Tour With Detour** | Every **node** of q appears in p in the same order — p can take a detour from nᵢ and return at a successor of nᵢ |

Sidetrip = leave and return to the **same node** in q  
Detour = leave from nᵢ and return at a **successor** of nᵢ in q

#### Best Effort Touring (Practical Recommendation)

1. Satisfy as many test requirements as possible **without** sidetrips (exact tours)
2. For remaining unsatisfied TRs, **allow sidetrips**

Always allowing sidetrips weakens the criterion. Always requiring exact tours may leave many infeasible TRs.

### Infeasible Test Requirements

Some test requirements **cannot be satisfied** by any test:
- **Dead code** — a node or edge that is syntactically present but never executed
- **Contradictory conditions** — a subpath that requires `X > 0` and `X < 0` simultaneously

It is generally **undecidable** whether all test requirements for a given graph are feasible. Most structural criteria have some infeasible requirements.

### Simple and Prime Path Enumeration Example

Graph with nodes 1–7, edges including a loop at `[5,6,5]`:

| Length | Paths | Notes |
|--------|-------|-------|
| 0 | `[1]`,`[2]`,…,`[7]` | Each node |
| 1 | `[1,2]`,`[1,3]`,`[2,3]`,`[3,4]`,`[3,5]`,`[4,7]`!,`[5,7]`!,`[5,6]`,`[6,5]` | `!` = terminates at final node |
| 2 | `[1,2,3]`,`[1,3,4]`,`[1,3,5]`,`[2,3,4]`,`[2,3,5]`,`[3,4,7]`!,`[3,5,7]`!,`[3,5,6]`!,`[5,6,5]`*,`[6,5,7]`!,`[6,5,6]`* | `*` = cycles |
| 3–4 | `[1,2,3,4]`,`[1,2,3,5]`,…,`[1,2,3,5,6]`! | Extended paths |

**Prime paths** = paths that are not a proper subpath of any longer simple path.

### Data Flow Coverage Criteria

Structural criteria only track *which code executes*. Data flow criteria also track *how values move* through the program.

#### Key Definitions

| Term | Definition |
|------|-----------|
| **def(n)** or **def(e)** | The set of variables **defined** (written/assigned) at node n or edge e |
| **use(n)** or **use(e)** | The set of variables **used** (read/accessed) at node n or edge e |
| **DU pair** | A pair of locations (lᵢ, lⱼ) where variable v is **defined** at lᵢ and **used** at lⱼ |
| **def-clear path** | A path from lᵢ to lⱼ where v is **not redefined** anywhere along the path |
| **du-path** | A simple, def-clear subpath from a definition of v to a use of v |
| **Reach** | A def at lᵢ *reaches* a use at lⱼ if there is a def-clear path between them |

#### Data Flow Example (double-diamond graph)

```
    1: X = 42
   / \
  2   3
   \ /
    4
   / \
  5: Z=X-8   6: Z=X*2
   \ /
    7
```

- `def(1) = {X}`, `use(5) = {X}`, `use(6) = {X}`
- def(5) = {Z}, def(6) = {Z}

#### Data Flow Test Criteria (weakest → strongest)

| Criterion | Abbr. | Requirement | Focus |
|-----------|-------|------------|-------|
| **All-defs** | ADC | For each def of each variable v, at least one du-path is toured | Every definition reaches *some* use |
| **All-uses** | AUC | For each DU pair (lᵢ, lⱼ, v), at least one du-path from lᵢ to lⱼ is toured | Every definition reaches *every* use |
| **All-du-paths** | ADUPC | For each DU pair (lᵢ, lⱼ, v), *every* du-path from lᵢ to lⱼ is toured | Every definition reaches every use via *every* possible path |

**Example results for variable X (defined at node 1):**

| Criterion | Required Test Paths |
|-----------|-------------------|
| All-defs | `[1, 2, 4, 5]` *(just reach one use)* |
| All-uses | `[1, 2, 4, 5]` and `[1, 2, 4, 6]` *(reach both uses)* |
| All-du-paths | `[1,2,4,5]`, `[1,3,4,5]`, `[1,2,4,6]`, `[1,3,4,6]` *(all paths to all uses)* |

#### DU-Path Touring

A test path p **du-tours** subpath d with respect to v if:
- p tours d (d is a subpath of p), AND
- the subpath taken is **def-clear** with respect to v

Sidetrips can be used (just as with prime path touring) as long as the path remains def-clear.

### Criteria Subsumption Summary

```
All-du-paths ⊇ All-uses ⊇ All-defs
CPC ⊇ PPC ⊇ EPC ⊇ EC ⊇ NC
PPC ⊇ EC ⊇ NC  (but PPC ⊄ EPC due to self-edges)
```

---

*Summarized from SE317 lecture slides — Ashraf Gaffar*
