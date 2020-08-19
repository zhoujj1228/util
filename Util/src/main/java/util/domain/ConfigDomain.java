package util.domain;

import java.util.Hashtable;

public class ConfigDomain {
	private String id;
	private String value;
	private Hashtable<String, ConfigDomain> map = new Hashtable<String, ConfigDomain>();
	public ConfigDomain getMapConfig(String id){
		return map.get(id);
	}
	public String getConfig(String id){
		return map.get(id).getValue();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Hashtable<String, ConfigDomain> getMap() {
		return map;
	}
	public void setMap(Hashtable<String, ConfigDomain> map) {
		this.map = map;
	}
	
}
