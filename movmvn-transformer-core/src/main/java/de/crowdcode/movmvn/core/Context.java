package de.crowdcode.movmvn.core;

/**
 * Interface for context.
 * 
 * @author lofi
 */
public interface Context {

	/**
	 * Logger for info.
	 * 
	 * @param info
	 *            information to be written
	 */
	public void logInfo(String info);

	/**
	 * Set the project work directory, where we can rework on the structure of
	 * the Ant project.
	 * 
	 * @param projectWorkDirectory
	 *            work directory of the Ant project
	 */
	public void setProjectWorkDirectory(String projectWorkDirectory);

	/**
	 * Get the project work directory, where we can rework on the structure of
	 * the Ant project.
	 * 
	 * @return the work directory
	 */
	public String getProjectWorkDirectory();

	/**
	 * Set the Ant zip file project to be worked with.
	 * 
	 * @param zipFile
	 *            the zi
	 */
	public void setZipFile(String zipFile);

	/**
	 * Get the Ant zip file project.
	 * 
	 * @return the zip file with its complete directory
	 */
	public String getZipFile();

	/**
	 * Get the project name.
	 * 
	 * @return the name of the project
	 */
	public String getProjectName();

	/**
	 * Get the source name of the project.
	 * 
	 * @return the name of the project plus the source
	 */
	public String getProjectSourceName();

	/**
	 * Get the target name of the project.
	 * 
	 * @return the name of the project plus the target
	 */
	public String getProjectTargetName();
}
