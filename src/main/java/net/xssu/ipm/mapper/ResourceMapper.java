package net.xssu.ipm.mapper;

import java.util.List;

import net.xssu.ipm.pojo.Resource;

/**
 * @author bonult
 * @description 资源DAO
 */
public interface ResourceMapper extends BaseMapper {

	public List<Resource> findResourcesByKey(/* @Param("attrs") String[] attrs */) throws Exception;

	//	@SelectProvider(type = CrudTemplet.class, method = "select")
	//	public Resource readResource(Resource resource) throws Exception;
	//
	//	public List<Resource> findResources(Resource resource) throws Exception;
	//
	//	public List<Resource> findResPListByKeys(@Param("attrs") String[] attrs) throws Exception;
	//
	//	public List<Resource> findResourcesByAttr(@Param("attr") String attr, @Param("attrs") String[] attrs) throws Exception;
	//
	//	public Set<String> findResAttrByResIds(@Param("attr") String attr, @Param("ids") List<?> ress) throws Exception;

}
