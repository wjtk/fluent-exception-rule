# fluent-exception-rule [![Build Status](https://travis-ci.org/wjtk/fluent-exception-rule.png?branch=master)](https://travis-ci.org/wjtk/fluent-exception-rule) [![Coverage Status](https://coveralls.io/repos/wjtk/fluent-exception-rule/badge.png?branch=master)](https://coveralls.io/r/wjtk/fluent-exception-rule?branch=master)

Expected exception rule for [Junit] with [AssertJ] assertions.

- [QuickStart] (#reason)
    - [Reason] (#reason)
    - [Solution] (#solution)
    - [Examples] (#examples)
    - [Usage with custom assertions] (#custom assertions)
    - [Usage with callbacks] (#CheckExpectedException)
    - [Extending] (#extending)
- [Javadoc]
- [Getting started] (#getting started)
- [Code templates] (#code templates)
- [Changelog] (#changelog) 
- [License] (#license)
- [Other resources] (#resources)

## <a name="reason"/> Reason

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
    
    This is not very concise, and hard to read. We want our tests to be readable, don't we? Line with `failBecauseExceptionWasNotThrown` is easy to forget in this bloat code, and our test will pass, in that case, even if there is no exception. Not very good.
    

## <a name="solution"/> Solution

Fluent-exception-rule combines Junit `ExpectedException` rule and AssertJ's assertions convenience. In order to use it, you have to declare rule in your test class:

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

## <a name="examples"/> More examples

Expecting exception without specifying its type:

```java
thrown.expect().hasMessage("exc").hasNoCause();
```

Specify expected exception type:

```java
thrown.expect(IllegalArgumentException.class);
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

## <a name="custom assertions"/> Custom ThrowableAssert

Let's assume that we have written some custom AssertJ assertion for custom exception, for example:

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

Usage of this assert with `FluentExpectedException` is very simple:

```java
thrown.expectWith(SQLExceptionAssert.class).hasMessageContaining("constraint").hasErrorCode(10).hasNoCause();
```

You can also apply custom assertion to exception cause and root cause:

```java
thrown.expectCauseWith(SQLExceptionAssert.class).hasMessageContaining("foreign key").hasErrorCode(11);
thrown.expectRootCauseWith(SQLExceptionAssert.class).hasErrorCode(12).hasMessageContaining("primary key");
```


## <a name="CheckExpectedException"/> CheckExpectedException

In case if: 
- you don't want to write custom AssertJ assertion, but want to test custom exception property, etc.
- you want to verify state of objects after exception

Library provides `CheckExpectedException` rule, which makes possible to verify exceptions in callback style. Unfortunately, Java 7 is not very concise with anonymous classes, so there is some bloat code here.
Declaration:

```java
import pl.wkr.fluentrule.api.CheckExpectedException;

@Rule
public CheckExpectedException thrown = CheckExpectedException.none();
```

Usage:

```java  
import pl.wkr.fluentrule.api.check.Check;


coffeeMachine.insertCoin(2);

thrown.check(new Check() {
    @Override
    public void check(Throwable throwable) {
        assertThat(coffeeMachine.getInsertedMoney()).isEqualTo(2);  //assert state
    }
});

coffeeMachine.getCoffee();
```

In Java 8 you can use benefits of lambdas and their shorter syntax:

```java  
thrown.check(exc -> assertThat(exc).isInstanceOf(NotEnoughMoney.class));
```


You can also use type-safe version with `SafeCheck` class to avoid potential exception casting to desired type:

```java
import pl.wkr.fluentrule.api.check.SafeCheck;


coffeeMachine.insertCoin(1);

thrown.check(new SafeCheck<NotEnoughMoney>() {
    protected void safeCheck(NotEnoughMoney notEnoughMoney) {
        assertThat(notEnoughMoney.getLackingMoney()).isEqualTo(3);  //assert custom exception
    }
});

coffeeMachine.getCoffee();
```

## <a name="extending"/> Extending

In order to follow DRY rule, `FluentExpectedException` and `CheckExpectedException` can be easily overriden and new methods can be added. For example, let's assume that we regularly check if a thrown exception is an instance of `IllegalArgumentException` with `SQLException` root cause with message containing "connection lost". We can write method that will check this expectations(overriding FluentExpectedException):

```java
class MyFluentExpectedException extends FluentExpectedException {

    public ThrowableAssert expectLostConnection() {
        expect().as("expectLostConnection")
                .isInstanceOf(IllegalStateException.class)
                .hasRootCauseInstanceOf(SQLException.class);

        return expectRootCause().as("expectLostConnection-cause").hasMessageContaining("connection lost");
    }
}
```

and use it in tests:

```java
thrown.expectLostConnection().hasMessageContaining("#144");

//sample connection lost simulation
throw new IllegalStateException(new IllegalStateException(new SQLException("connection lost, #144")));
```

## <a name="getting started"/> Getting started

Library is present on maven central, coordinates:

pom in maven:  
```xml
<dependency>
    <groupId>pl.wkr</groupId>
    <artifactId>fluent-exception-rule</artifactId>
    <version>1.0.1</version>
    <scope>test</scope>
</dependency>
```      

in gradle:   
```groovy  
repositories {
    mavenCentral()
}

dependencies {
	testCompile "pl.wkr:fluent-exception-rule:1.0.0"
}
```

Alternatively, you can [download source code as ZIP](https://github.com/wjtk/fluent-exception-rule/releases).

## <a name="code templates"/> Code Templates

You can create code template in your IDE to ease using of fluent exception rule.

For example in Intellij Idea (Settings->IDE Settings->Live Templates) by pasting this template text:
 ```
 @org.junit.Rule    
 public pl.wkr.fluentrule.api.FluentExpectedException thrown = pl.wkr.fluentrule.api.FluentExpectedException.none(); $END$ 
 ```
(don't forget to select "Shorten FQ names" option)
 

## Catch-Exception

From 1.2.0 version on, [Catch-Exception] provides support for AssertJ that is really worth using. It's a small yet great library, however, there are some limitations to it. It can't catch exceptions from static methods and constructors, and final methods as well as final classes are a bit troublesome. In such cases `FluentExpectedException` shines.

## <a name="changelog"/> Changelog

### 1.0.1
- support for assertj 1.7.0 (this version also supports 1.6.0)

### 1.0.0

- refactoring and release to maven central
- project groupId change

#### 0.2.0

- new methods: `expectCause(Class)`, `expectRootCause(Class)`
- new methods `expectWith(assertClass)`, `expectCauseWith(assertClass)`, `expectRootCauseWith()`
- project information improvements
- javadoc

#### 0.1.0 

- initial version

## <a name="license"/> License

This project is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).

## <a name="resources"/> Other resources

- [Project](https://github.com/wjtk/fluent-exception-rule-examples) with examples of fluent-expected-exception usage.

[Junit]: https://github.com/junit-team/junit "Junit"
[AssertJ]: https://github.com/joel-costigliola/assertj-core "AssertJ"
[Catch-Exception]: https://github.com/rwoo/catch-exception "Catch-Exception"
[Javadoc]: https://wjtk.github.io/fluent-exception-rule/javadoc/latest
