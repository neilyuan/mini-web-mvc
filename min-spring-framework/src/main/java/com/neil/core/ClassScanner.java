package com.neil.core;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

	/**
	 * scan class reference in package
	 * @param packageName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> scan(String packageName) throws IOException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		String relativePhysicalPath = StringUtils.replace(packageName, ".", "/");
		ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resources = appClassLoader.getResources(relativePhysicalPath);
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			if (StringUtils.contains(resource.getProtocol(),"jar")) {
				JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
				String fullJarPhysicalPath = jarURLConnection.getJarFile().getName();
				classes.addAll(getClassFromJar(fullJarPhysicalPath, relativePhysicalPath));
			} else {
				//TODO
			}
		}
		return classes;
	}

	/**
	 *  Get class type from Jar file
	 * @param fullJarPhysicalPath
	 * @param relativePhysicalPath
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> getClassFromJar(String fullJarPhysicalPath, String relativePhysicalPath) throws IOException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		JarFile jarFile = new JarFile(fullJarPhysicalPath);
		Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
		while (jarEntryEnumeration.hasMoreElements()) {
			JarEntry jarEntry = jarEntryEnumeration.nextElement();
			String entryName = jarEntry.getName(); // JarEntryName example: com/neil/controller/exampleController.class
			if (StringUtils.startsWithIgnoreCase(entryName, relativePhysicalPath) && StringUtils.endsWith(entryName, ".class")) {
				String classFullName = StringUtils.substringBefore(
					StringUtils.replace(entryName, "/", "."),
					".class"
				);
				classes.add(Class.forName(classFullName));
			}
		}
		return classes;
	}
}
