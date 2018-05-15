package xdean.deannotation.checker.checkChildren;

import xdean.deannotation.checker.CheckChildren;

@CheckChildren(annotated = { InheritAnno.class, NotInheritAnno.class })
public interface A {

}
