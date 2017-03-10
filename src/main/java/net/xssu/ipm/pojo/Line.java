package net.xssu.ipm.pojo;

import java.io.Serializable;
import java.util.Objects;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.MyJson;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;
import net.xssu.ipm.annotation.Transient;

@MyJson
@Table(name = "line")
public class Line implements Serializable {

	@Transient
	private static final long serialVersionUID = -5131771561206076091L;

	@Pk
	@Transient
	private Integer id;
	@Pk
	@Transient
	private Integer amId;
	@Column
	private Integer from;
	@Column
	private Integer to;
	@Column
	private String name;
	@Column
	private String type;
	@Column
	private String M;
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
		Line that = (Line) o;
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

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getM() {
		return M;
	}

	public void setM(String m) {
		M = m;
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
