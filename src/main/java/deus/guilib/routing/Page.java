package deus.guilib.routing;

import deus.guilib.element.config.ChildrenPlacement;
import deus.guilib.element.config.derivated.GuiConfig;
import deus.guilib.element.interfaces.IElement;
import deus.guilib.element.interfaces.IUpdatable;
import deus.guilib.user.PageGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChest;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {

	protected int mouseX = 0;
	protected int mouseY = 0;
	protected Router router;
	protected Minecraft mc;
	protected GuiConfig config;
	protected int width, height = 0;
	protected int xSize, ySize = 0;
	private List<IElement> content = new ArrayList<>();


	public Page(Router router) {
		this.router = router;
		mc = Minecraft.getMinecraft(this);
	}

	public void addContent(IElement... element) {
		content.addAll(List.of(element));
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getxSize() {
		return xSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setXYWH(int xSize, int ySize, int width, int height) {
		this.ySize=ySize;
		this.xSize=xSize;
		this.width=width;
		this.height=height;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void config(GuiConfig config) {
		this.config = config;
	}

	public List<IElement> getContent() {
		return content;
	}

	public void render() {
		if (content.isEmpty()) return;

		int[] accumulatedPosition = {0, 0};  // Acumulación inicial de posiciones X, Y

		for (IElement child : content) {
			if (shouldPositionChild(child)) {
				positionChild(child, accumulatedPosition);
			}
			child.draw();  // Dibujamos el hijo
		}
	}

	private boolean shouldPositionChild(IElement child) {
		return !child.getConfig().isIgnoreFatherPlacement() && !child.isPositioned();
	}

	private void positionChild(IElement child, int[] accumulatedPosition) {
		int[] basePos = calculateBasePosition(child);

		// Posición acumulada
		int relativeX = basePos[0] + accumulatedPosition[0] + child.getX();
		int relativeY = basePos[1] + accumulatedPosition[1] + child.getY();

		child.setPositioned(true);
		child.setX(relativeX);
		child.setY(relativeY);

		// Acumulamos posiciones en función de la configuración
		accumulatePosition(accumulatedPosition, child);
	}

	private void accumulatePosition(int[] accumulatedPosition, IElement child) {
		switch (config.getPlacement()) {
			case TOP:
				accumulatedPosition[1] += child.getHeight();  // Acumulamos en Y (hacia abajo)
				break;
			case BOTTOM:
				accumulatedPosition[1] -= child.getHeight();  // Acumulamos en Y (hacia arriba)
				break;
			default:
				accumulatedPosition[0] += child.getWidth();   // Acumulamos en X (hacia la derecha)
				break;
		}
	}

	private int[] calculateBasePosition(IElement child) {
		int childWidth = child.getWidth();
		int childHeight = child.getHeight();

		// Calculamos la posición base de forma más compacta
		return switch (config.getPlacement()) {
			case CENTER -> new int[]{(width - childWidth) / 2, (height - childHeight) / 2};
			case TOP -> new int[]{(width - childWidth) / 2, 0};
			case BOTTOM -> new int[]{(width - childWidth) / 2, height - childHeight};
			case LEFT -> new int[]{0, (height - childHeight) / 2};
			case RIGHT -> new int[]{width - childWidth, (height - childHeight) / 2};
			case TOP_LEFT -> new int[]{0, 0};
			case BOTTOM_LEFT -> new int[]{0, height - childHeight};
			case BOTTOM_RIGHT -> new int[]{width - childWidth, height - childHeight};
			case TOP_RIGHT -> new int[]{width - childWidth, 0};
			default -> new int[]{0, 0};
		};
	}



	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void update() {
		for (IElement element : content) {
			if (element instanceof IUpdatable) {
				((IUpdatable) element).update();
			}
		}
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
}
