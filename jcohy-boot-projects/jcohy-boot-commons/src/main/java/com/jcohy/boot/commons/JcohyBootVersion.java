package com.jcohy.boot.commons;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.04.0 2022/5/30:23:24
 * @since 2022.04.0
 */
public final class JcohyBootVersion {

	public static final long SERIAL_VERSION_UID = getVersion().hashCode();

	public static String getVersion() {
		return determineSagaBootVersion();
	}

	private static String determineSagaBootVersion() {

		// 首先，获取包的版本
		String implementationVersion = JcohyBootVersion.class.getPackage().getImplementationVersion();
		if (implementationVersion != null) {
			return implementationVersion;
		}

		// 从源代码的 Manifest 文件中获取 Implementation-Version 属性值
		CodeSource codeSource = JcohyBootVersion.class.getProtectionDomain().getCodeSource();

		if (codeSource == null) {
			return null;
		}

		URL codeSourceLocation = codeSource.getLocation();

		try {
			URLConnection connection = codeSourceLocation.openConnection();
			if (connection instanceof JarURLConnection) {
				return getImplementationVersion(((JarURLConnection) connection).getJarFile());
			}
			try (JarFile jarFile = new JarFile(new File(codeSourceLocation.toURI()))) {
				return getImplementationVersion(jarFile);
			}
		}
		catch (Exception ex) {
			return "";
		}
	}

	private static String getImplementationVersion(JarFile jarFile) throws IOException {
		return jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
	}
}
