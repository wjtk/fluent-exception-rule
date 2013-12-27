fluent-exception-rule [![Build Status](https://travis-ci.org/wjtk/fluent-exception-rule.png?branch=master)](https://travis-ci.org/wjtk/fluent-exception-rule)
=====================

Expected exception rule for [Junit] with [AssertJ] assertions.

Reason
------

In Junit + Assertj test enviroment you can test thrown exceptions in two ways:

- junit `ExpectedException` rule:  
    After using AssertJ you don't want to go back to junit matchers. So this is not very good idea.

- AssertJ way:
  ```java
  @Test
  public void assertj_traditional_way(){
      coffeeMachine.insertCoin(3);
      try {
          coffeeMachine.getCoffee();
  
          failBecauseExceptionWasNotThrown(NotEnoughMoney.class);
      } catch(Exception e) {
          assertThat(e).isInstanceOf(NotEnoughMoney.class).hasMessage("Not enough money, insert 1$ more");
      }
  }
  ```
    
    This not very concise, and hard to read. We want our tests to be readable, don't we? Line with `failBecauseExceptionWasNotThrown` is easy to forget in this bloat code, and our test will pass, in that case, even if there is no exception. Not very good.
    

Solution
--------
Fluent-exception-rule is union of Junit `ExpectedException` rule and AssertJ's assertions convenience. To use it you have to declare rule in your test class:
```java
import pl.wkr.fluentrule.api.FluentExpectedException;

@Rule
public FluentExpectedException thrown = FluentExpectedException.none();
```

and after that you can write this test like that:
```java
@Test
public void fluent_rule_way() throws Exception {
    coffeeMachine.insertCoin(3);

    thrown.expect(NotEnoughMoney.class).hasMessage("Not enough money, insert 1$ more").hasNoCause();
    coffeeMachine.getCoffee();
}
```

You can use all very convenient methods of 'ThrowableAssert' from AssertJ. Isn't it more readable?

More examples
-------------

Expecting exception without specifying its type:
```java
thrown.expect().hasMessage("exc").hasNoCause();
```

Specify expected exception type:
```java
thrown.expect(IllegalArgumentException.class);
```

Expecting exception to be any type of:
```java
thrown.expectAny(IllegalStateException.class, IllegalArgumentException.class);
```

Expecting cause:
```java
thrown.expectCause().hasMessageContaining("to low");
thrown.expectCause(IllegalAccessException.class).hasMessageContaining("memory");
```

Expecting rootCause(exception on tail of exceptions chain):
```java
thrown.expectRootCause().hasMessageContaining("is null");
thrown.expectRootCause(IllegalArgumentException.class).hasMessageContaining("argument");
```

More AssertJ assertions:
```java
thrown.expectAny(RuntimeException.class, SQLException.class)
        .as("exception")
        .hasMessageContaining("this")
        .hasRootCauseInstanceOf(IllegalArgumentException.class)
        .hasCauseInstanceOf(IllegalStateException.class);

thrown.expectCause().as("exception cause").hasMessage("cause");
thrown.expectRootCause().as("exception rootCause").hasMessage("root").hasNoCause();

throw new RuntimeException("this throwable",
        new IllegalStateException("cause",
                new IllegalArgumentException("root")));
```

And that's it, after expectXXX() methods you can use all methods from AssertJ's `ThrowableAssert`

Custom ThrowableAssert
------------------------
Assume that we have written some custom AssertJ assertion for custom exception, for example:

```java
public class SQLExceptionAssert extends AbstractThrowableAssert<SQLExceptionAssert, SQLException> {

    protected SQLExceptionAssert(SQLException actual) {
        super(actual, SQLExceptionAssert.class);
    }

    public SQLExceptionAssert hasErrorCode(int expected) {
        int actualCode = actual.getErrorCode();
        assertThat(actualCode).overridingErrorMessage(
                "\nExpected SQLException.errorCode : <%d> but was: <%d>", expected, actualCode).isEqualTo(expected);
        return myself;
    }
}
```
Usage of this assert with `FluentExpectedException` is very straightforward:

```java
thrown.assertWith(SQLExceptionAssert.class).hasMessageContaining("constraint").hasErrorCode(10).hasNoCause();
```

CheckExpectedException
----------------------
For cases when: 
- you don't want to write custom AssertJ assertion, but want to test custom exception property, etc.
- you want to verify state of objects after exception

Library provides `CheckExpectedException` rule, which make possible to verify exceptions in callback style. Unfortunately, Java 7 is not very concise with anonymous classes, so there is some bloat code here.
Declaration:

```java
import pl.wkr.fluentrule.api.CheckExpectedException;

@Rule
public CheckExpectedException thrown = CheckExpectedException.none();
```
Usage:

```java
import pl.wkr.fluentrule.api.Check;


coffeeMachine.insertCoin(2);

thrown.check(new Check() {
    @Override
    public void check(Throwable throwable) {
        assertThat(coffeeMachine.getInsertedMoney()).isEqualTo(2);  //assert state
    }
});

coffeeMachine.getCoffee();
```

You can also use type-safe version with `SafeCheck` class to avoid potential exception casting to desired type:

```java
import pl.wkr.fluentrule.api.SafeCheck;


coffeeMachine.insertCoin(1);

thrown.check(new SafeCheck<NotEnoughMoney>() {
    protected void safeCheck(NotEnoughMoney notEnoughMoney) {
        assertThat(notEnoughMoney.getLackingMoney()).isEqualTo(3);  //assert custom exception
    }
});

coffeeMachine.getCoffee();
```

Getting started
---------------

Maven coordinates:
```
<dependency>
    <groupId>pl.wkr.test</groupId>
    <artifactId>fluent-exception-rule</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
</dependency>
```        

The project is not in maven central repository, so you can grab it, by cloning this repo:

```bash
$ git clone https://github.com/wjtk/fluent-exception-rule.git
$ cd fluent-exception-rule
$ git checkout 0.1.0
$ mvn javadoc:jar source:jar install
```

Alternatively you can [download source code as ZIP](https://github.com/wjtk/fluent-exception-rule/releases).


Catch-Exception
---------------
From 2013-11 [Catch-Exception] has support for AssertJ, so there are already no reasons not to use it. It's small but great library, but also has some limitations. It can't catch exceptions from static methods and constructors, also final methods and final classes are pain. So proposition is: use catch-exception where it works, and for other cases use `FluentExpectedException`.


Other resources
---------------
- [Project](https://github.com/wjtk/fluent-exception-rule-examples) with more examples of fluent-expected-exception usage.

[Junit]: https://github.com/junit-team/junit "Junit"
[AssertJ]: https://github.com/joel-costigliola/assertj-core "AssertJ"
[Catch-Exception]: https://github.com/rwoo/catch-exception "Catch-Exception"