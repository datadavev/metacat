package edu.ucsb.nceas.metacat.common.index;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dataone.service.types.v1.SystemMetadata;

public class IndexTask implements Serializable {
	
	private SystemMetadata systemMetadata;
	
	private Map<String, List<Object>> fields;
	
	private byte[] resourceMapData;
	
	public byte[] getResourceMapData() {
		return resourceMapData;
	}

	public void setResourceMapData(byte[] resourceMapData) {
		this.resourceMapData = resourceMapData;
	}

	public SystemMetadata getSystemMetadata() {
		return systemMetadata;
	}

	public void setSystemMetadata(SystemMetadata systemMetadata) {
		this.systemMetadata = systemMetadata;
	}

	public Map<String, List<Object>> getFields() {
		return fields;
	}

	public void setFields(Map<String, List<Object>> fields) {
		this.fields = fields;
	}

}
