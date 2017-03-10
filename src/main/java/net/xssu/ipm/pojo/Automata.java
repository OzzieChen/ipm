package net.xssu.ipm.pojo;

import java.io.Serializable;
import java.util.List;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.MyJson;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;
import net.xssu.ipm.annotation.Transient;

@MyJson
@Table(name = "automata")
public class Automata implements Serializable {

	@Transient
	private static final long serialVersionUID = 7129488975471969685L;

	@Pk
	private Integer id;
	@Column
	private String title;
	@Column
	private String description;

	private List<Node> nodes;
	private List<Line> lines;
	private List<Area> areas;

	public Automata(Integer id) {
		this.id = id;
	}

	public Automata() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

}
