package de.crowdcode.movmvn.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Unzipper.
 * 
 * @author lofi
 * @version 1.0.0
 * @since 1.0.0
 */
public class Unzipper {

	/**
	 * Unzip a file.
	 * 
	 * @param archiveFile
	 *            to be unzipped
	 * @param outPath
	 *            the place to put the result
	 * @throws IOException
	 *             exception
	 * @throws ZipException
	 *             exception
	 */
	public void unzipFileToDir(final File archiveFile, final File outPath)
			throws IOException, ZipException {
		ZipFile zipFile = new ZipFile(archiveFile);
		Enumeration<ZipArchiveEntry> e = zipFile.getEntries();
		while (e.hasMoreElements()) {
			ZipArchiveEntry entry = e.nextElement();
			File file = new File(outPath, entry.getName());
			if (entry.isDirectory()) {
				FileUtils.forceMkdir(file);
			} else {
				InputStream is = zipFile.getInputStream(entry);
				FileOutputStream os = FileUtils.openOutputStream(file);
				try {
					IOUtils.copy(is, os);
				} finally {
					os.close();
					is.close();
				}
				file.setLastModified(entry.getTime());
			}
		}
	}
}
