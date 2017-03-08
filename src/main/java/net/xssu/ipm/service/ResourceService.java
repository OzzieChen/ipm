package net.xssu.ipm.service;

import java.util.List;

import net.xssu.ipm.pojo.Resource;
import net.xssu.ipm.pojo.User;

public interface ResourceService {

	/**
	 * @Title: findMenus
	 * @Description: 根据用户找出对应的资源
	 * @param @param permissions
	 * @return List<Resource>
	 */
	List<Resource> findMenus(User user);

}
