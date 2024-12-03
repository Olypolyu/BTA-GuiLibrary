package deus.guilib.nodes.domsystem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import deus.guilib.nodes.types.containers.Bar;
import deus.guilib.nodes.types.containers.Panel;
import deus.guilib.nodes.types.inventory.Slot;
import deus.guilib.nodes.types.representation.Image;
import deus.guilib.nodes.types.representation.Label;
import deus.guilib.nodes.types.semantic.*;

import org.w3c.dom.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import deus.guilib.nodes.Node;
import deus.guilib.interfaces.nodes.INode;


public class XMLProcessor {

	private static final Map<String, Class<?>> classNames = Map.of(
		deus.guilib.nodes.Root.class.getSimpleName(), deus.guilib.nodes.Root.class,
		Body.class.getSimpleName(), Body.class,
		Div.class.getSimpleName(), Div.class,
		Node.class.getSimpleName(), Node.class,
		Span.class.getSimpleName(), Span.class,
		Label.class.getSimpleName(), Label.class,
		Bar.class.getSimpleName(), Bar.class,
		Slot.class.getSimpleName(), Slot.class,
		Panel.class.getSimpleName(), Panel.class,
		Image.class.getSimpleName(), Image.class
	);


	public static INode parseXML(String path) {
		try {
			// Reading xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(path));
			document.getDocumentElement().normalize();

			// Obtain root node
			Element root = document.getDocumentElement();

			// Parsing tags to Elements
			Node rootNode = new Node();
			parseChildren(root, rootNode);

			return rootNode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void parseChildren(Element root, INode parentNode) {
		NodeList nodes = root.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node node = nodes.item(i);

			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element elem = (Element) node;

				// Obtener el nombre del nodo sin espacio de nombres (si lo hay)
				String nodeName = node.getLocalName() != null ? node.getLocalName() : node.getNodeName();

				// Capitalizar la primera letra del nombre del nodo
				String capitalized = nodeName.substring(0, 1).toUpperCase() + nodeName.substring(1);

				Map<String, String> attributes = getAttributesAsMap(elem);

				// Crear un nuevo nodo
				INode newNode = createNodeByClassSimpleName(capitalized, attributes, elem);

				// Agregar el nuevo nodo al nodo padre
				parentNode.addChild(newNode);

				// Llamada recursiva para procesar los hijos de este nodo
				parseChildren(elem, newNode);
			}
		}
	}

	private static INode createNodeByClassSimpleName(String name, Map<String, String> attributes, Element element) {
		try {
			Class<?> clazz = classNames.getOrDefault(name, deus.guilib.nodes.Node.class);

			Constructor<?> constructor = clazz.getConstructor(Map.class);

			Object instance = constructor.newInstance(attributes);

			if(clazz.equals(Label.class)) {
				((Label)instance).addText(element.getTextContent().trim());
			}

			return (INode) instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	private static Map<String, String> getAttributesAsMap(Element elem) {
		Map<String, String> attributesMap = new HashMap<>();

		// Obtener los atributos del elemento
		if (elem.hasAttributes()) {
			NamedNodeMap attributeNodes = elem.getAttributes();

			for (int i = 0; i < attributeNodes.getLength(); i++) {
				org.w3c.dom.Node attribute = attributeNodes.item(i);

				// Agregar el nombre del atributo y su valor al mapa
				attributesMap.put(attribute.getNodeName(), attribute.getNodeValue());
			}
		}

		return attributesMap;
	}

	public static void printChildNodes(INode node, String prefix, int lvl) {
		System.out.println(prefix.repeat(lvl) + node.getClass().getSimpleName());

		if (!node.getChildren().isEmpty()) {
			for (INode childNode : node.getChildren()) {
				printChildNodes(childNode, prefix, lvl + 1);
			}
		}
	}

}