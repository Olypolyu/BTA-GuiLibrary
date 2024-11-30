package deus.guilib.interfaces;

import deus.guilib.interfaces.element.INode;

import java.util.List;

@FunctionalInterface
public interface IChildrenLambda {
	void apply(List<INode> elements);
}
