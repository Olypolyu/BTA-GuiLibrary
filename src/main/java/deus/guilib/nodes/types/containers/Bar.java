package deus.guilib.nodes.types.containers;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;
import deus.guilib.resource.Texture;

import java.util.Map;

public class Bar extends Node {

	private int length = 3;
	private int offset = 7;
	private boolean small = false;
	private String direction = "vertically";

	public Bar() {
		super();
	}

	public Bar(Map<String, String> attributes) {
		super(attributes);
	}


	public Bar setLength(int length) {
		this.length = length;
		return this;
	}

	public Bar setOffset(int offset) {
		this.offset = offset;
		return this;
	}

	public Bar setSmall(boolean small) {
		this.small = small;
		return this;
	}

	@Override
	public void draw() {
		super.draw();

		updateLenghtAndOffsetFromStyle();
		if (styles.containsKey("barDirection")) {
			direction = (String) styles.get("barDirection");
		}
	}

	@Override
	protected void drawBackgroundImage() {
		if (direction.equals("vertically")) {
			drawBackgroundVertically();
		} else if (direction.equals("horizontally")) {
			drawBackgroundHorizontally();
		}
	}

	private void drawBackgroundHorizontally() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");

			int frameX = 0;
			for (int i = 0; i < length - 1; i++) {
				if (i == length - 1) {
					frameX = 1;
				} else if (i > 0) {
					frameX = 2;
				} else {
					frameX = 0;
				}

				t.drawWithFrame(mc, i * 32, gy, width, height, frameX, 4);
			}
		}
	}

	private void drawBackgroundVertically() {
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			int frameY;
			for (int i = 0; i < length-1; i++) {
				if (i == length - 2) {
					frameY = 2;
				} else if (i > 0) {
					frameY = 1;
				} else {
					frameY = 0;
				}
				t.drawWithFrame(mc, gx, gy + (i * 32), width, height, 0, frameY*32);
			}
		}
	}

	@Override
	protected void drawChild() {
		// Evitar errores si no hay hijos
		if (!children.isEmpty()) {
			int totalHeight = 0;

			// Excluir la altura del primer hijo
			for (INode child : children) {
				totalHeight += child.getHeight();
			}
			// Sumar los offsets entre los hijos (todos los espacios entre ellos)
			totalHeight += (children.size() - 1) * offset;

			// Ajustar posición inicial en Y, centrado si hay espacio suficiente
			int startY = gy;
			if (totalHeight < getHeight()) {
				startY = gy + (getHeight() - totalHeight) / 2;
			}

			// Aplicar desplazamiento adicional si el contenedor es "pequeño"
			int currentY = startY - (small ? 3 : 0);

			// Dibujar cada hijo en la posición calculada
			for (int i = 0; i < children.size(); i++) {
				INode child = children.get(i);
				int posY = currentY;
				int posX = (width/2)-(children.get(i).getWidth()/2); // Posición fija en X

				// Dibujar el hijo sin alterar su contribución al totalHeight en el primer hijo
				child.setPosition(posX, posY);
				child.draw();

				// Solo actualizar la posición Y para los hijos después del primero
				if (i > 0) {
					currentY += child.getHeight() + offset;
				}
			}
		}
	}

	protected void updateLenghtAndOffsetFromStyle() {
		if (attributes.containsKey("length")) {
			length = Integer.parseInt(attributes.get("length"));
		} else {
			length = children.size();
		}
		if (attributes.containsKey("offset")) {
			offset = Integer.parseInt(attributes.get("offset"));
		}
	}

	@Override
	public int getHeight() {
		return length * 32;
	}
}