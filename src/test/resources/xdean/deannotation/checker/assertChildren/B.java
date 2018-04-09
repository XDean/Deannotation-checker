package xdean.deannotation.checker.assertChildren;

@NotInheritAnno
@InheritAnno
public class B implements A {

}

@NotInheritAnno
class BB extends B {

}
