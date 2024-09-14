package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.error.Error;

public class CraftingTable extends Element {

	private int SLOT_SIZE = 18; // Tamaño de cada slot (ajustar según sea necesario)
	private int cols = 3;
	private int rows = 3;
	private int slotQuantity = 9;
	private boolean fake = false;

	public CraftingTable() {
		// Agregar 9 slots, formando una cuadrícula de 3x3
		for (int i = 0; i < slotQuantity; i++) {
			addChildren(new Slot().setSid("CRAFTING_SLOT_"+i));
		}
	}

	@Override
	protected void drawIt() {

	}

	@Override
	protected void drawChild() {
		if (mc == null) {
			System.out.println(Error.MISSING_MC);
			return;
		}

		if (!children.isEmpty()) {
			// Colocar los slots en una cuadrícula de 3x3


			for (int i = 0; i < children.size(); i++) {

				int col = i % cols; // columna
				int row = i / cols; // fila
				int xPos = this.x + col * SLOT_SIZE;
				int yPos = this.y + row * SLOT_SIZE;

				children.get(i).setPosition(xPos, yPos);
				children.get(i).draw();
			}
		}
	}

	public int getCols() {
		return cols;
	}

	public CraftingTable setCols(int cols) {
		this.cols = cols;
		return this;
	}

	public int setRows(int rows) {
		this.rows = rows;
		return rows;
	}

	public int getRows() {
		return rows;
	}

	public CraftingTable setSlotQuantity(int slotQuantity) {
		this.slotQuantity = slotQuantity;
		return this;
	}

	public int getSlotQuantity() {
		return slotQuantity;
	}

	@Override
	public int getWidth() {
		return cols*SLOT_SIZE;
	}

	@Override
	public int getHeight() {
		return rows*SLOT_SIZE;
	}

	public boolean isFake() {
		return fake;
	}

	public CraftingTable setFake(boolean fake) {
		this.fake = fake;
		children.forEach(
			c->((Slot)c).setFake(fake)
		);
		return this;
	}
}