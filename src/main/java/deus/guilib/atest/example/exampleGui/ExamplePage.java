package deus.guilib.atest.example.exampleGui;

import deus.guilib.element.config.Placement;
import deus.guilib.element.config.derivated.GuiConfig;

import deus.guilib.element.elements.semantic.Body;
import deus.guilib.element.elements.other.FreeElement;
import deus.guilib.element.elements.semantic.Div;
import deus.guilib.routing.Page;
import deus.guilib.routing.Router;

public class ExamplePage extends Page {

	//private final PlayerInventory playerInventory = (PlayerInventory) new PlayerInventory(40).setPosition(Placement.CENTER).setSid("INV");

	public ExamplePage(Router router, String... text) {
		super(router);
		config(GuiConfig.create().setChildrenPlacement(Placement.CHILD_DECIDE));
		addContent(
			//playerInventory
			new FreeElement().addChildren(
				new Body().addChildren(
					new FreeElement(),
					new Div().addChildren(
						new FreeElement().setSid("FREE2")
					)
				)
			)
		);
		reloadStyles();
	}

	@Override
	public void update() {
		super.update();
		//playerInventory.update();
	}
}
