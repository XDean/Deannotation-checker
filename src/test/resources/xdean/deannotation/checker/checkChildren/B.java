package xdean.deannotation.checker.checkChildren;

@NotInheritAnno
@InheritAnno
public class B implements A {

}

@NotInheritAnno
class BB extends B {

}
