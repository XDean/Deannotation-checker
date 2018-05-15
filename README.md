# Deannotation Checker
[![Build Status](https://travis-ci.org/XDean/Deannotation-checker.svg?branch=master)](https://travis-ci.org/XDean/Deannotation-checker)
[![codecov.io](http://codecov.io/github/XDean/deannotation-checker/coverage.svg?branch=master)](https://codecov.io/gh/XDean/deannotation-checker/branch/master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.XDean/deannotation-checker/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.XDean/deannotation-checker)

Dean Checker Framework

Get the error in advance at compile period!

# Get Start

```xml
<dependency>
  <groupId>com.github.XDean</groupId>
  <artifactId>deannotation-checker</artifactId>
  <version>x.x.x</version>
</dependency>
```

and you may read [Use Meta Annotation](https://github.com/XDean/AnnotationProcessorToolkit/blob/master/doc/Meta.md#use-meta-annotation) to understand how to use meta-annotation.

# Features

- [`@AssertMethod`](#assertmethod)
- [`@AssertChildren`](#assertchildren)
- [Common Annotations](#common-annotations)
  - [`@TypreRestrict`](#typerestrict)


# `AssertMethod`

## Sample Usage

```java
@AssertMethod(argCount = 0, returnType = @TypeRestrict(void.class))
public @interface Init {
   ...
}
```

```java
@AssertMethod(argCount = 0, returnType = @TypeRestrict(void.class))
public @interface Init { // this line will give compile error '[5,15] Must only have 0 arguments'
   ...
}
```

## eclipse snapshot

![AssertMethodEclipse.jpg](doc/image/AssertMethodEclipse.jpg)

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| returnType | [`TypreRestrict`](#typerestrict) | `Irrelevant` | return type must match the `TypeRestrict` |
| requiredModifier | `Modifier[]` | `{}` | method must have all these modifiers |
| forbiddenModifier | `Modifier[]` | `{}` | method must have none of theses modfiers |
| argCount | int | -1 | method must have exactly parameter count if the value is not negative |
| argTypes | [`TypreRestrict[]`](#typerestrict) | `{}` | parameters must match the types with one to one correspondence<br>`argTypes` length can't more than `argCount` if it is not negative |

# `AssertChildren`

# Common Annotations

## `TypeRestrict`

Restriction of type. Use `TypeRestrict.Handler` to check the restriction.

### Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| value | `Class` | `Irrelevant` | target class, if it's `Irrelevant`, the restriction will match any type |
| type | `Type` | `EQUAL` | the restriction type |

### Type

1. `EQUAL`, the type must equals target type
2. `EXTEND`, the type must assignable to target type
3. `SUPER`, the type must assignable from target type
