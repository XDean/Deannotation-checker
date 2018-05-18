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

and you may need to read [Use Meta Annotation](https://github.com/XDean/AnnotationProcessorToolkit/blob/master/doc/Meta.md#use-meta-annotation) to understand how to use meta-annotation.

# Sample Usage

```java
@CheckMethod(argCount = 0, returnType = @CheckType(void.class))
public @interface Init {
   ...
}
```

```java
@Init
public void func(int i) { // this line will give compile error '[5,15] Must only have 0 arguments'
   ...
}
```

## eclipse snapshot

![AssertMethodEclipse.jpg](doc/image/AssertMethodEclipse.jpg)

# Features

- [`@CheckType`](#checktype)
- [`@CheckAnnotation`](#checkannotation)
- [`@CheckModifier`](#checkmodifier)
- [`@CheckClass`](#checkclass)
- [`@CheckField`](#checkfield)
- [`@CheckMethod`](#checkmethod)
- [`@CheckParam`](#checkparam)
  
# `CheckType`

Check the annotated type. Note this is different with `@CheckClass`.

### Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| value | `Class` | `Irrelevant` | target type, if it's `Irrelevant`, the check will always pass |
| type | `Type` | `EQUAL` | check type |

### Type

1. `EQUAL`, the type must equals target type
2. `EXTEND`, the type must assignable to target type
3. `SUPER`, the type must assignable from target type

# `CheckAnnotation`

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| require | `Class<? extends Annotation>[]` | `{}` | required annotations |
| forbid | `Class<? extends Annotation>[]` | `{}` | forbidden annotations |

# `CheckModifier`

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| require | `Modifier[]` | `{}` | required modifiers |
| forbid | `Modifier[]` | `{}` | forbidden modifiers |

# `CheckClass`

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| implement | `Class<?>[]` | `{}` | check the class implements(extends) all the classes |
| modifier | `CheckModifier` | `@CheckModifier` | check the class's modifiers |
| annotation | `CheckAnnotation` | `@CheckAnnotation` | check the class's annotations |

# `CheckField`

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| type | `CheckType` | `@CheckType` | check the field's type |
| modifier | `CheckModifier` | `@CheckModifier` | check the field's modifiers |
| annotation | `CheckAnnotation` | `@CheckAnnotation` | check the field's annotations |

# `CheckMethod`

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| modifier | `CheckModifier` | `@CheckModifier` | check the method's modifiers |
| annotation | `CheckAnnotation` | `@CheckAnnotation` | check the method's annotations |
| returnType | `CheckType` | `Irrelevant` | check the method's return type |
| argCount | int | -1 | method must have exactly parameter count if the value is not negative |
| argTypes | `CheckParam[]` | `{}` | check parameters with one to one correspondence<br>`argTypes` length can't more than `argCount` if it is not negative |

# `CheckParam`

## Attributes

| Name | Type |  Default | Description |
| -- | -- | -- | -- |
| type | `CheckType` | `@CheckType` | check the parameter's type |
| annotation | `CheckAnnotation` | `@CheckAnnotation` | check the parameter's annotations |
