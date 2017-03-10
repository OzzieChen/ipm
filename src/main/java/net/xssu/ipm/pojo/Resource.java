package net.xssu.ipm.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;

@Table(name = "resource")
public class Resource implements Serializable {

	private static final long serialVersionUID = -2771588320608567580L;
	@Pk
	private Integer id;
	@Column
	private String name;
	@Column(name = "parent_id")
	private Integer parentId;
	@Column(name = "res_key")
	private String resKey;
	@Column
	private String type;
	@Column(name = "res_url")
	private String resUrl;
	@Column
	private String icon;
	@Column
	private String description;
	@Column(name = "parent_ids")
	private String parentIds;
	@Column
	private String openWay;
	@Column
	private Integer ordr;

	private List<Resource> children;

	public Resource() {}

	public Resource(String resKey) {
		this.resKey = resKey;
	}

	public Resource(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Resource that = (Resource) o;
		if(!Objects.equals(this.id, that.getId()))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public String getOpenWay() {
		return openWay;
	}

	public void setOpenWay(String openWay) {
		this.openWay = openWay;
	}

	public Integer getOrdr() {
		return ordr;
	}

	public void setOrdr(Integer order) {
		this.ordr = order;
	}
}
