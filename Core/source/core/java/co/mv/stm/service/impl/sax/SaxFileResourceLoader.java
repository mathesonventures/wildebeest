package co.mv.stm.service.impl.sax;

import co.mv.stm.Resource;
import co.mv.stm.service.ResourceLoader;
import java.io.File;

public class SaxFileResourceLoader implements ResourceLoader
{
	public SaxFileResourceLoader(File resourceFile)
	{
		this.setResourceFile(resourceFile);
	}
	
	// <editor-fold desc="ResourceFile" defaultstate="collapsed">

	private File m_resourceFile = null;
	private boolean m_resourceFile_set = false;

	public File getResourceFile() {
		if(!m_resourceFile_set) {
			throw new IllegalStateException("resourceFile not set.  Use the HasResourceFile() method to check its state before accessing it.");
		}
		return m_resourceFile;
	}

	private void setResourceFile(
		File value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceFile cannot be null");
		}
		boolean changing = !m_resourceFile_set || m_resourceFile != value;
		if(changing) {
			m_resourceFile_set = true;
			m_resourceFile = value;
		}
	}

	private void clearResourceFile() {
		if(m_resourceFile_set) {
			m_resourceFile_set = true;
			m_resourceFile = null;
		}
	}

	private boolean hasResourceFile() {
		return m_resourceFile_set;
	}

	// </editor-fold>

	@Override public Resource load()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}