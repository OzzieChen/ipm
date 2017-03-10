package net.xssu.ipm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.sf.json.JSONObject;
import net.xssu.ipm.mapper.ResourceMapper;
import net.xssu.ipm.pojo.Area;
import net.xssu.ipm.pojo.Automata;
import net.xssu.ipm.pojo.Line;
import net.xssu.ipm.pojo.Node;
import net.xssu.ipm.pojo.Resource;
import net.xssu.ipm.pojo.User;
import net.xssu.ipm.service.ResourceService;
import net.xssu.ipm.utils.C;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bonult
 * @description
 * @date 2016年8月29日 下午7:11:48
 */
@Service
public class ResourceServiceImpl implements ResourceService {

	@Inject
	private ResourceMapper resourceMapper;

	/**
	 * @see ResourceService#findMenus(Set)
	 */
	public List<Resource> findMenus(User user) {
		List<Resource> ress = null;
		//		if(user.getPermissions() == null || user.getPermissions().isEmpty())
		//			return Collections.emptyList();
		try{
			ress = resourceMapper.findResourcesByKey();//user.getPermissions().toArray(new String[0])
		}catch(Exception e){
			e.printStackTrace();
			ress = Collections.emptyList();
		}
		return ress;
	}

	public Automata findAutomataInfo(Automata automata) {
		Automata am = null;
		try{
			am = resourceMapper.findAutomataInfo(automata);
		}catch(Exception e){
			e.printStackTrace();
		}
		return am;
	}

	public List<Automata> findAutomatas(Automata automata) {
		List<Automata> as = null;
		try{
			as = resourceMapper.readAutomatas(automata);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(as == null)
			as = Collections.emptyList();
		return as;
	}

	/**
	 * 解析自动机的json数据，并检查数据正确性，ok->写入数据库，开启事务控制
	 */
	@Transactional
	public String addAutomata(Automata automata, String json) {
		JSONObject jo = JSONObject.fromObject(json);
		JSONObject joUnits;
		String strID;
		JSONObject joTmp;
		Set<?> it;
		//-----------nodes----start------------
		List<Node> nodes = null;
		joUnits = jo.getJSONObject("nodes");
		it = joUnits.keySet();
		nodes = new ArrayList<Node>(it.size());
		Node node;
		for(Object i : it){
			strID = (String) i;
			joTmp = joUnits.getJSONObject(strID);
			node = new Node();

			node.setId(Integer.parseInt(strID.substring(strID.lastIndexOf('_') + 1)));
			node.setName(C.get(joTmp.get("name")));
			node.setTop(C.get(joTmp.get("top")));
			node.setLeft(C.get(joTmp.get("left")));
			node.setWidth(C.get(joTmp.get("width")));
			node.setHeight(C.get(joTmp.get("height")));
			node.setType(C.get(joTmp.get("type")));

			nodes.add(node);
		}
		//-----------nodes----end--------------

		//-----------areas----start------------
		List<Area> areas = null;
		joUnits = jo.getJSONObject("areas");
		it = joUnits.keySet();
		areas = new ArrayList<Area>(it.size());
		Area area;
		for(Object i : it){
			strID = (String) i;
			joTmp = joUnits.getJSONObject(strID);
			area = new Area();
			area.setId(Integer.parseInt(strID.substring(strID.lastIndexOf('_') + 1)));
			area.setName(C.get(joTmp.get("name")));
			area.setTop(C.get(joTmp.get("top")));
			area.setLeft(C.get(joTmp.get("left")));
			area.setWidth(C.get(joTmp.get("width")));
			area.setHeight(C.get(joTmp.get("height")));
			area.setColor(C.get(joTmp.get("color")));

			areas.add(area);
		}
		//-----------areas----end--------------

		//-----------lines----start------------
		List<Line> lines = null;
		joUnits = jo.getJSONObject("lines");
		it = joUnits.keySet();
		lines = new ArrayList<Line>(it.size());
		Line line;
		for(Object i : it){
			strID = (String) i;
			joTmp = joUnits.getJSONObject(strID);
			line = new Line();
			line.setId(Integer.parseInt(strID.substring(strID.lastIndexOf('_') + 1)));
			line.setName(C.get(joTmp.get("name")));
			line.setFrom(C.get(joTmp.get("from")));
			line.setTo(C.get(joTmp.get("to")));
			line.setM(C.get(joTmp.get("m")));
			line.setType(C.get(joTmp.get("type")));
			line.setMarked(C.get(joTmp.get("marked")));
			if(line.getMarked() == null)
				line.setMarked(false);

			lines.add(line);
		}
		//-----------lines----end--------------

		automata.setNodes(nodes);
		automata.setLines(lines);
		automata.setAreas(areas);
		String isOk = checkAutomataJsonData(automata);
		if(isOk != null)
			return isOk;
		try{
			resourceMapper.insertIdAutoIncrease(automata);
			int a = automata.getId();
			Integer amId = a;
			for(Node n : nodes)
				n.setAmId(amId);
			resourceMapper.createAutomataUnits(nodes, "n");
			for(Line n : lines)
				n.setAmId(amId);
			resourceMapper.createAutomataUnits(lines, "l");
			for(Area n : areas)
				n.setAmId(amId);
			resourceMapper.createAutomataUnits(areas, "a");
		}catch(Exception e){
			e.printStackTrace();
			return "出错了";
		}
		return null;
	}

	/**
	 * @Title: checkAutomataJsonData
	 * @Description: 检查自动机是否合法
	 * @param am 自动机数据
	 * @return String error信息，null表示无错误
	 */
	public static String checkAutomataJsonData(Automata am) {
		return null;
	}

	/**
	 * @Title: buildJsonData
	 * @Description: 检查自动机是否合法
	 * @param am 自动机数据
	 * @return String json
	 */
	public static String buildJsonData(Automata am) {
		StringBuilder sb = new StringBuilder();
		sb.append("{title:\"");
		sb.append(C.notNullStr(am.getTitle()));
		sb.append("\",iamId:\"").append(am.getId());
		sb.append("\",description:\"");
		sb.append(C.notNullStr(am.getDescription()));
		sb.append("\",nodes:{");
		int x = 0;
		int idMax = 1;
		if(am.getNodes() != null){
			for(Node n : am.getNodes()){
				if(x++ > 0)
					sb.append(",");
				if(n.getId() > idMax)
					idMax = n.getId();
				sb.append("Don_").append(n.getId());
				sb.append(":{name:\"").append(C.notNullStr(n.getName()));
				sb.append("\",left:").append(n.getLeft());
				sb.append(",top:").append(n.getTop());
				sb.append(",type:\"").append(n.getType());
				sb.append("\",width:").append(n.getWidth());
				sb.append(",height:").append(n.getHeight());
				sb.append("}");
			}
		}
		sb.append("},lines:{");
		if(am.getLines() != null){
			x = 0;
			for(Line l : am.getLines()){
				if(x++ > 0)
					sb.append(",");
				if(l.getId() > idMax)
					idMax = l.getId();
				sb.append("Dol_").append(l.getId());
				sb.append(":{type:\"").append(l.getType());
				sb.append("\",from:\"Don_").append(l.getFrom());
				sb.append("\",to:\"Don_").append(l.getTo());
				sb.append("\",name:\"").append(C.notNullStr(l.getName()));
				sb.append("\",marked:").append(l.getMarked());
				if(l.getM() != null)
					sb.append(",M:").append(l.getM());
				sb.append("}");
			}
		}
		sb.append("},areas:{");
		if(am.getAreas() != null){
			x = 0;
			for(Area a : am.getAreas()){
				if(x++ > 0)
					sb.append(",");
				if(a.getId() > idMax)
					idMax = a.getId();
				sb.append("Doa_").append(a.getId());
				sb.append(":{name:\"").append(C.notNullStr(a.getName()));
				sb.append("\",left:").append(a.getLeft());
				sb.append(",top:").append(a.getTop());
				sb.append(",color:\"").append(a.getColor());
				sb.append("\",width:").append(a.getWidth());
				sb.append(",height:").append(a.getHeight());
				sb.append("}");
			}
		}
		sb.append("},initNum:").append(idMax + 2).append("}");
		return sb.toString();
	}
}
