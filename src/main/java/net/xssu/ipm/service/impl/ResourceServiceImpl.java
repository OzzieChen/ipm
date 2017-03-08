package net.xssu.ipm.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.xssu.ipm.mapper.ResourceMapper;
import net.xssu.ipm.pojo.Resource;
import net.xssu.ipm.pojo.User;
import net.xssu.ipm.service.ResourceService;

import org.springframework.stereotype.Service;

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
}
