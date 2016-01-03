package commons;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtils {

	/**
	 * List directory contents for a resource folder. Not recursive. This is
	 * basically a brute-force implementation. Works for regular files and also
	 * JARs.
	 * 
	 * taken from: http://manuel.kiessling.net/2015/01/19/setting
	 * -up-a-scala-sbt-multi
	 * -project-with-cassandra-connectivity-and-migrations/
	 * 
	 * Fixed leaking jarFile not being closed. Fixed warning due to raw type
	 * usage of java.lang.Class. Extracted method getFiles for readibility.
	 * 
	 * @param clazz
	 *            Any java class that lives in the same place as the resources
	 *            you want.
	 * @param path
	 *            Should end with "/", but not start with one.
	 * @return Just the name of each member item, not the full paths.
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 * @author Greg Briggs
	 * 
	 * 
	 */
	public static String[] getResourceListing(Class<?> clazz, String path)
			throws URISyntaxException, IOException {
		URL dirURL = clazz.getClassLoader().getResource(path);
		if (dirURL != null && dirURL.getProtocol().equals("file")) {
			/* A file path: easy enough */
			return new File(dirURL.toURI()).list();
		}

		if (dirURL == null) {
			/*
			 * In case of a jar file, we can't actually find a directory. Have
			 * to assume the same jar as clazz.
			 */
			String me = clazz.getName().replace(".", "/") + ".class";
			dirURL = clazz.getClassLoader().getResource(me);
		}

		if (dirURL.getProtocol().equals("jar")) {
			/* A JAR path */
			String jarPath = dirURL.getPath().substring(5,
					dirURL.getPath().indexOf("!")); // strip out only the JAR
													// file
			JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
			try {
				return getFiles(path, jar);
			} finally {
				jar.close();
			}
		}

		throw new UnsupportedOperationException("Cannot list files for URL "
				+ dirURL);
	}

	private static String[] getFiles(String path, JarFile jar) {
		Enumeration<JarEntry> entries = jar.entries();
		// avoid duplicates in case it is a subdirectory
		Set<String> result = new HashSet<String>();
		while (entries.hasMoreElements()) {
			String name = entries.nextElement().getName();
			if (!name.startsWith(path))
				continue;

			// filter according to the path
			String entry = name.substring(path.length());
			int checkSubdir = entry.indexOf("/");
			if (checkSubdir >= 0) {
				// if it is a subdirectory, we just return the
				// directory name
				entry = entry.substring(0, checkSubdir);
			}
			result.add(entry);
		}
		return result.toArray(new String[result.size()]);
	}

}