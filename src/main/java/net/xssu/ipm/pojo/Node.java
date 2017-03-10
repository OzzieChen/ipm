package net.xssu.ipm.pojo;

import java.io.Serializable;
import java.util.Objects;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.MyJson;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;
import net.xssu.ipm.annotation.Transient;

@MyJson
@Table(name = "node")
public class Node implements Serializable {

	@Transient
	private static final long serialVersionUID = 6248382270362392237L;
	@Pk
	@Transient
	private Integer id;
	@Column
	@Transient
	private Integer amId;
	@Column
	private String name;
	@Column
	private Integer left;
	@Column
	private Integer top;
	@Column
	private Integer width;
	@Column
	private Integer height;
	@Column
	private String type;
	@Column
	private Boolean marked;
	@Column
	private Boolean alt;

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Node that = (Node) o;
		if(!Objects.equals(id, that.id) || !Objects.equals(amId, that.amId))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmId() {
		return amId;
	}

	public void setAmId(Integer amId) {
		this.amId = amId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getMarked() {
		return marked;
	}

	public void setMarked(Boolean marked) {
		this.marked = marked;
	}

	public Boolean getAlt() {
		return alt;
	}

	public void setAlt(Boolean alt) {
		this.alt = alt;
	}

}
