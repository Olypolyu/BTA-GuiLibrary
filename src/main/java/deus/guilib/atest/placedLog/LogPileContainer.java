package deus.guilib.atest.placedLog;


import deus.guilib.container.AdvancedContainer;
import deus.guilib.container.InventoryComponent;
import deus.guilib.util.math.Tuple;
import deus.guilib.routing.Page;
import deus.guilib.element.interfaces.IElement;
import net.minecraft.client.Minecraft;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class LogPileContainer extends AdvancedContainer {

	private int slotIdCounter = 0; // Counter to keep track of slot IDs

	public LogPileContainer(Page page, IInventory playerInventory, IInventory logPileInventory) {
		super();

		for (IElement element : page.getContent()) {
			if (element instanceof deus.guilib.element.elements.Slot) {
				addSlot(new Slot(logPileInventory, slotIdCounter++,element.getX()+1, element.getY()+1));
			}
		}





		//InventoryComponent inventoryComponent = (InventoryComponent) new InventoryComponent(this, playerInventory);
		//inventoryComponent.build();
	}



	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		// No se mueve ningún ítem, acción inactiva
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		// No hay slots objetivo, acción inactiva
		return null;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		// Contenedor usable por el jugador
		return true;
	}
}