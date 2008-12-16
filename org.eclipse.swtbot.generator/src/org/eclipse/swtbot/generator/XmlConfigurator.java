/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlConfigurator {

	private final SugarGenerator	sugarConfiguration;
	private final SAXParserFactory	saxParserFactory;

	public XmlConfigurator(SugarGenerator sugarConfiguration, ClassLoader classLoader) {
		this.sugarConfiguration = sugarConfiguration;
		saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setNamespaceAware(true);
	}

	public void load(InputSource inputSource) throws ParserConfigurationException, SAXException, IOException {
		SAXParser saxParser = saxParserFactory.newSAXParser();
		saxParser.parse(inputSource, new DefaultHandler() {
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				if (localName.equals("widget")) {
					String className = attributes.getValue("class");
					try {
						addClass(className);
					} catch (ClassNotFoundException e) {
						throw new SAXException("Cannot find Matcher class : " + className);
					}
				}
			}
		});
	}

	private void addClass(String className) throws ClassNotFoundException {
		sugarConfiguration.addFactoryMethods(new SWTBotGeneratorFactoryReader(className));
		sugarConfiguration.addImports(new SWTBotGeneratorFactoryReader(className));
	}

	public static void main(String... args) throws Exception {
		if (args.length != 4) {
			System.err.println("Args: config-file source-dir generated-class output-dir");
			System.err.println("");
			System.err.println("    config-file : Path to config file listing matchers to generate sugar for.");
			System.err.println("                  e.g. path/to/matchers.xml");
			System.err.println("");
			System.err.println("    source-dir  : Path to Java source containing matchers to generate sugar for.");
			System.err.println("                  May contain multiple paths, separated by commas.");
			System.err.println("                  e.g. src/java,src/more-java");
			System.err.println("");
			System.err.println("generated-class : Full name of class to generate.");
			System.err.println("                  e.g. org.myproject.MyMatchers");
			System.err.println("");
			System.err.println("     output-dir : Where to output generated code (package subdirs will be");
			System.err.println("                  automatically created).");
			System.err.println("                  e.g. build/generated-code");
			System.exit(-1);
		}

		String configFile = args[0];
		String srcDirs = args[1];
		String fullClassName = args[2];
		File outputDir = new File(args[3]);

		String fileName = fullClassName.replace('.', File.separatorChar) + ".java";
		int dotIndex = fullClassName.lastIndexOf(".");
		String packageName = dotIndex == -1 ? "" : fullClassName.substring(0, dotIndex);
		String shortClassName = fullClassName.substring(dotIndex + 1);

		if (!outputDir.isDirectory()) {
			System.err.println("Output directory not found : " + outputDir.getAbsolutePath());
			System.exit(-1);
		}

		File outputFile = new File(outputDir, fileName);
		outputFile.getParentFile().mkdirs();

		SugarGenerator sugarGenerator = new SugarGenerator();
		try {
			sugarGenerator.addWriter(new HamcrestFactoryWriter(packageName, shortClassName, new FileWriter(outputFile)));
			sugarGenerator.addWriter(new QuickReferenceWriter(System.out));

			XmlConfigurator xmlConfigurator = new XmlConfigurator(sugarGenerator, XmlConfigurator.class.getClassLoader());
			xmlConfigurator.load(new InputSource(configFile));
			System.out.println("Generating " + fullClassName);
			sugarGenerator.generate();
		} finally {
			sugarGenerator.close();
		}
	}
}
