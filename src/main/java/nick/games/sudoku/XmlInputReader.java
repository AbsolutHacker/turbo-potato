package nick.games.sudoku;

import io.vavr.collection.Stream;
import io.vavr.control.Try;
import nick.games.sudoku.api.Number;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class XmlInputReader {

  private static final DocumentBuilder documentBuilder = Try.of(() -> DocumentBuilderFactory.newInstance().newDocumentBuilder()).get();


  public static Stream<Number[][]> read(File file) {
    return readPuzzles(file).map(XmlInputReader::readString).map(XmlInputReader::gridAlign);
  }

  public static Stream<String> readPuzzles(File file) {

    try {
      final Document document = documentBuilder.parse(file);
      final NodeList games = document.getElementsByTagName("game");

      return Stream.ofAll(() -> new NodeListIterator(games)).map(XmlInputReader::readDataFromNode);
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
      return null;
  }

  static int numericValue(int digitSymbol) {
    if (digitSymbol >= '0' && digitSymbol <= '9') {
    return digitSymbol - '0';
    } else
      throw new IllegalArgumentException("out of range");
  }

  static List<Number> readString(String numberString) {
    return numberString.chars().map(XmlInputReader::numericValue).mapToObj(Number::fromInt).collect(Collectors.toList());
  }

  static Number[][] gridAlign(List<Number> numberList) {
    final int size = (int) Math.sqrt(numberList.size());
    if (numberList.size() != size * size) {
      throw new IllegalArgumentException("illegal list size: " + numberList.size());
    }
    Number[][] result = new Number[size][size];
    Iterator<Number> iterator = numberList.iterator();
    for (int rowHead = 0; rowHead < size; rowHead++) {
      for (int colHead = 0; colHead < size; colHead++) {
        result[rowHead][colHead] = iterator.next();
      }
    }
    return result;
  }

  static class NodeListIterator implements Iterator<Node> {
    private final NodeList nodeList;
    private int head = 0;
    public NodeListIterator(NodeList nodeList) {
      this.nodeList = nodeList;
    }

    @Override
    public boolean hasNext() {
      return head < nodeList.getLength();
    }

    @Override
    public Node next() {
      return nodeList.item(head++);
    }
  }

  static String readDataFromNode(Node node) {
    return node.getAttributes().getNamedItem("data").getTextContent();
  }
}
