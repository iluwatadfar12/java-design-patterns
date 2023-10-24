/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.slob.lob;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private String name;
  private List<Product> products;

  /**
   * @param xmlDoc
   * @return
   * @throws ParserConfigurationException
   */
  public Element departmentsToXmlElement(Document xmlDoc) throws ParserConfigurationException {
    Element root = xmlDoc.createElement("departments");
    for (Product product : products) {
      Element xmlElement = product.toXmlElement(xmlDoc);
      if (xmlElement != null) {
        root.appendChild(xmlElement);
      }
    }
    xmlDoc.appendChild(root);
    return (Element) xmlDoc.getFirstChild();
  }

  /**
   * @param source
   * @return
   */
  public Customer readDepartments(Element source) {
    List result = new ArrayList();
    Node child = source.getFirstChild();
    while (child != null) {
      if (child.getNodeType() == Node.ELEMENT_NODE && child.getTextContent() != null) {
        System.out.println(child.getAttributes());
      }
      child = child.getNextSibling();
    }
    return null;
  }
}
