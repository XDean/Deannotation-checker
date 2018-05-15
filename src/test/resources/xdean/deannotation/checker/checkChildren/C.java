package xdean.deannotation.checker.checkChildren;

@NotInheritAnno
@InheritAnno
public class C implements A {

}

@InheritAnno
class CC extends C {

}

@InheritAnno
abstract class CCC extends C {

}
