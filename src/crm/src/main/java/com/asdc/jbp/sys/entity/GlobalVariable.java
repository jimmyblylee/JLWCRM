
package com.asdc.jbp.sys.entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.framework.exception.ServiceException;

/** 
 * ClassName: GlobalVariable.java <br>
 * Description: 当服务器启动时，将全局变量存入内存中<br>
 * Create by: yuruixin/ruixin_yu@asdc.com.cn <br>
 * Create Time: 2016年5月30日<br>
 */
public class GlobalVariable{
	
	@Resource(name = "SysCommonDao")
	private JpaOrmOperator sysCommonDao;
	
	/** 
	 * Description：将数据库中和配置文件中的全局参数存入内存中
	 * @throws ServiceException
	 * @return void
	 * @author name：yuruixin
	 * @throws IOException 
	 **/
	@PostConstruct
	public void  init() throws ServiceException, IOException{ 
	    // String url = this.getClass().getResource("/globalVariable.properties").toString().substring(6);
		// Properties pro = new Properties();
		// FileInputStream in = null;
		// 	in = new FileInputStream(url);
		// 	pro.load(in);
		// 	Iterator<String> it_pro=pro.stringPropertyNames().iterator();
		// 	while(it_pro.hasNext()){
		// 		String key=it_pro.next();
		// 		System.setProperty(key,pro.getProperty(key));
		// 	}
			// in.close();
			List<SysGlobalVariable> SysGlobalVariableList=(List<SysGlobalVariable>) sysCommonDao.queryByNamedQuery("sys.hql.getGlobalVariableInit");
			for (SysGlobalVariable sysGlobalVariable : SysGlobalVariableList) {
				System.setProperty(sysGlobalVariable.getVariableName(),sysGlobalVariable.getVariableValue());
			}
	}
}
