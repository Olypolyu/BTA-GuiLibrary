package deus.guilib.element;

import deus.guilib.element.config.Placement;
import deus.guilib.element.stylesystem.BorderStyle;
import deus.guilib.element.stylesystem.StyleParser;
import deus.guilib.element.stylesystem.StyleSystem;
import deus.guilib.error.Error;
import deus.guilib.interfaces.element.IStylable;
import deus.guilib.resource.Texture;

import java.util.HashMap;
import java.util.Map;

public class Node extends Root implements IStylable {

	public Node() {
		super();
	}

	public Node(Map<String, String> attributes) {
		super(attributes);
	}

	@Override
	public void applyStyle(Map<String, Object> styles) {
		StyleSystem.loadImagesFromStyles(styles);
		this.styles = styles;
	}


	@Override
	protected void drawIt() {
		if (mc == null) {
			throw new IllegalStateException(Error.MISSING_MC.getMessage());
		}


		updateLocalAndGlobalPositionFromStyle();
		updateSizeFromStyle();
		drawBackground();
		drawBorder();

		//texture.draw(mc, gx, gy);

	}

	protected void drawBackground() {
		if (styles.containsKey("backgroundColor")) {
			this.drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + getHeight(), StyleParser.parseColorToARGB((String) styles.get("backgroundColor")));
		} else
		if (styles.containsKey("backgroundImage")) {
			Texture t = (Texture) styles.get("backgroundImage");
			int scaleW = 0, scaleH = 0;

			if (styles.containsKey("backgroundImageScale")) {
				scaleW = scaleH = (Integer) styles.get("backgroundImageScale");
			}

			if (styles.containsKey("backgroundImageScaleWidth")) {
				scaleW = (Integer) styles.get("backgroundImageScaleWidth");
			}

			if (styles.containsKey("backgroundImageScaleHeight")) {
				scaleH = (Integer) styles.get("backgroundImageScaleHeight");
			}

			t.draw(mc, gx, gy, width, height, scaleW, scaleH);
		}
	}

	protected void drawBorder() {
		if (styles.containsKey("border")) {

			BorderStyle borderStyle = StyleParser.parseBorder((String) styles.get("border"));

			drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderStyle.width, borderStyle.color); // Superior
			drawRect(this.gx, this.gy + getHeight() - borderStyle.width, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Inferior
			drawRect(this.gx, this.gy, this.gx + borderStyle.width, this.gy + getHeight(), borderStyle.color); // Izquierda
			drawRect(this.gx + getWidth() - borderStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderStyle.color); // Derecha

			if (styles.containsKey("border-top")) {
				BorderStyle borderTopStyle = StyleParser.parseBorder((String) styles.get("border-top"));
				drawRect(this.gx, this.gy, this.gx + getWidth(), this.gy + borderTopStyle.width, borderTopStyle.color); // Superior
			}

			if (styles.containsKey("border-bottom")) {
				BorderStyle borderBottomStyle = StyleParser.parseBorder((String) styles.get("border-bottom"));
				drawRect(this.gx, this.gy, this.gx + borderBottomStyle.width, this.gy + getHeight(), borderBottomStyle.color); // Izquierda
			}

			if (styles.containsKey("border-left")) {
				BorderStyle borderLeftStyle = StyleParser.parseBorder((String) styles.get("border-left"));
				drawRect(this.gx, this.gy, this.gx + borderLeftStyle.width, this.gy + getHeight(), borderLeftStyle.color); // Izquierda
			}

			if (styles.containsKey("border-right")) {
				BorderStyle borderRightStyle = StyleParser.parseBorder((String) styles.get("border-right"));
				drawRect(this.gx + getWidth() - borderRightStyle.width, this.gy, this.gx + getWidth(), this.gy + getHeight(), borderRightStyle.color); // Derecha
			}

		}
	}

	protected void updateSizeFromStyle() {
		if (styles.containsKey("width")) {
			this.width = StyleParser.parsePixels((String) styles.get("width"));
		}

		if (styles.containsKey("height")) {
			this.height = StyleParser.parsePixels((String) styles.get("height"));
		}


		if (styles.containsKey("childrenPlacement")) {
			this.childrenPlacement = Placement.valueOf((String) styles.get("childrenPlacement"));
		}
	}

	protected void updateLocalAndGlobalPositionFromStyle() {
		if (styles.containsKey("localx")) {
			this.x = (int) styles.get("localx");
		}

		if (styles.containsKey("localy")) {
			this.y = (int) styles.get("localy");
		}

		if (styles.containsKey("globalx")) {
			this.gx = (int) styles.get("globalx");
		}

		if (styles.containsKey("globaly")) {
			this.gy = (int) styles.get("globaly");
		}
	}
}
