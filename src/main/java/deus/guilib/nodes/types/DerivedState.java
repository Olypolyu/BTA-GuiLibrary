package deus.guilib.nodes.types;

import deus.guilib.interfaces.nodes.INode;
import deus.guilib.interfaces.nodes.IStylable;
import deus.guilib.nodes.config.Placement;

import java.util.List;
import java.util.Map;

public abstract class DerivedState<T> implements INode, IStylable {

	protected T value;
	protected INode child;

	protected abstract INode generateNewChild();

	public DerivedState(T value) {
		this.value = value;
		this.child = generateNewChild();
	}

	public void setValue(T value) {
		this.value = value;
		this.child = generateNewChild();
	}

	public T getValue() {
		return value;
	}

	public INode getChild() {
		return child;
	}

	@Override
	public void draw() { child.draw(); }

	@Override
	public INode setChildrenPlacement(Placement placement) { return child.setChildrenPlacement(placement); }

	@Override
	public Placement getChildrenPlacement() { return  child.getChildrenPlacement(); }

	@Override
	public Placement getSelfPlacement() { return child.getSelfPlacement(); }

	@Override
	public void setSelfPlacement(Placement placement) {child.setSelfPlacement(placement);}

	@Override
	public int getX() {
		return child.getX();
	}

	@Override
	public int getY() {
		return child.getY();
	}

	@Override
	public INode setPosition(int x, int y) {
		child.setPosition(x, y);
		return this;
	}

	@Override
	public INode setGlobalPosition(int gx, int gy) {
		child.setGlobalPosition(gx, gy);
		return this;
	}

	@Override
	public int getGx() {
		return child.getGx();
	}

	@Override
	public int getGy() {
		return child.getGy();
	}

	@Override
	public int getWidth() {
		return child.getWidth();
	}

	@Override
	public int getHeight() {
		return child.getHeight();
	}

	@Override
	public INode setWidth(int width) {
		return child.setWidth(width);
	}

	@Override
	public INode setHeight(int height) {
		return child.setHeight(height);
	}

	protected INode parent;
	@Override
	public INode getParent() {
		return this.parent;
	}

	@Override
	public void setParent(INode parent) {
		this.parent = parent;
		child.setParent(parent);
	}

	@Override
	public boolean hasChildren() {
		return child.hasChildren();
	}

	@Override
	public List<INode> getChildren() {
		return child.getChildren();
	}

	@Override
	public List<INode> getDescendants() {
		return child.getDescendants();
	}

	@Override
	public INode addChildren(INode... children) {
		return child.addChildren(children);
	}

	@Override
	public INode setChildren(List<INode> children) {
		// no.
		return this;
	}

	@Override
	public String getGroup() {
		return child.getGroup();
	}

	@Override
	public INode setGroup(String group) {
		return child.setGroup(group);
	}

	@Override
	public String getId() {
		return child.getId();
	}

	@Override
	public INode setSid(String sid) {
		return child.setSid(sid);
	}

	@Override
	public Map<String, String> getAttributes() {
		return child.getAttributes();
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		this.child.setAttributes(attributes);
	}

	@Override
	public INode getNodeById(String id) {
		return child.getNodeById(id);
	}

	@Override
	public List<INode> getNodeByGroup(String group) {
		return child.getNodeByGroup(group);
	}

	@Override
	public List<INode> getNodeByClass(String className) {
		return child.getNodeByClass(className);
	}

	@Override
	public INode getClone() {
		return generateNewChild();
	}

	@Override
	public void update() {
		child.update();
	}

	@Override
	public void applyStyle(Map<String, Object> styles) {
		if (child instanceof IStylable) {
			((IStylable) child).applyStyle(styles);
		}
	}

	@Override
	public Map<String, Object> getStyle() {
		if (child instanceof IStylable) {
			return ((IStylable) child).getStyle();
		}
		return null;
	}

	@Override
	public void deleteStylesRecursive() {
		if (child instanceof IStylable) {
			((IStylable) child).deleteStylesRecursive();
		}
	}

	@Override
	public void deleteStyles() {
		if (child instanceof IStylable) {
			((IStylable) child).deleteStyles();
		}
	}
}
