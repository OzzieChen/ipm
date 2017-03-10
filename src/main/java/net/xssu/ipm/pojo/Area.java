package net.xssu.ipm.pojo;

import java.io.Serializable;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.MyJson;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;
import net.xssu.ipm.annotation.Transient;

@MyJson
@Table(name = "area")
public class Area implements Serializable {

	@Transient
	private static final long serialVersionUID = 7712631381396916162L;

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
	private String color;
	@Column
	private Boolean alt;

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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getAlt() {
		return alt;
	}

	public void setAlt(Boolean alt) {
		this.alt = alt;
	}

}
