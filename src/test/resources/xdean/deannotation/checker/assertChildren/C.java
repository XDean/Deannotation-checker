package xdean.deannotation.checker.assertChildren;

@NotInheritAnno
@InheritAnno
public class C implements A {

}

@InheritAnno
class CC extends C {

}