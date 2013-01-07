package de.crowdcode.movmvn.core;

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of an object context.
 * 
 * @author lofi
 */
public class ContextImpl implements Context {

	private static final Logger log = Logger.getLogger(ContextImpl.class
			.getName());
	private String projectWorkDirectory;
	private String zipFile;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logInfo(final String info) {
		log.info(info);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProjectWorkDirectory(final String projectWorkDirectory) {
		this.projectWorkDirectory = projectWorkDirectory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectWorkDirectory() {
		return this.projectWorkDirectory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setZipFile(final String zipFile) {
		this.zipFile = zipFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getZipFile() {
		return this.zipFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectName() {
		String nameWithoutZip = StringUtils.substringBefore(zipFile, ".zip");
		String nameResult = StringUtils.substringAfterLast(nameWithoutZip, "/");
		if (nameResult.equals("")) {
			// We have no "/" instead "\\"
			nameResult = StringUtils.substringAfterLast(nameWithoutZip, "\\");
		}
		return nameResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectSourceName() {
		String resultName = projectWorkDirectory.concat("/").concat(
				getProjectName());
		return resultName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectTargetName() {
		String resultName = projectWorkDirectory.concat("/target/").concat(
				getProjectName());
		return resultName;
	}
}
