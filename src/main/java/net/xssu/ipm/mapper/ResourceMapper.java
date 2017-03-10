package net.xssu.ipm.mapper;

import java.util.List;

import net.xssu.ipm.mybatis.CrudTemplet;
import net.xssu.ipm.pojo.Area;
import net.xssu.ipm.pojo.Automata;
import net.xssu.ipm.pojo.Line;
import net.xssu.ipm.pojo.Node;
import net.xssu.ipm.pojo.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author bonult
 * @description 资源DAO
 */
public interface ResourceMapper extends BaseMapper {

	public List<Resource> findResourcesByKey(/* @Param("attrs") String[] attrs */) throws Exception;

	@SelectProvider(type = CrudTemplet.class, method = "selectByWhere")
	public List<Automata> readAutomatas(Automata am) throws Exception;

	@SelectProvider(type = CrudTemplet.class, method = "selectByWhere")
	public List<Node> readNodes(Node node) throws Exception;

	@SelectProvider(type = CrudTemplet.class, method = "selectByWhere")
	public List<Line> readLines(Line line) throws Exception;

	@SelectProvider(type = CrudTemplet.class, method = "selectByWhere")
	public List<Area> readAreas(Area area) throws Exception;

	public Automata findAutomataInfo(Automata am) throws Exception;

	public int createAutomataUnits(@Param("list") List<?> units, @Param("op") String op) throws Exception;
}
