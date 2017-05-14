/**
 * Project Name : jbp-plugins-file-test-war <br>
 * File Name : FileTestController.java <br>
 * Package Name : com.asdc.jbp.file.controller <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.attachment.controller;


import java.io.IOException;
import java.util.Date;


import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.asdc.jbp.attachment.entity.SysAttachment;
import com.asdc.jbp.attachment.service.FileService;
import com.asdc.jbp.attachment.utils.FileUtils;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.StringUtils;
import com.asdc.jbp.sys.token.Token;

/**
 * ClassName : FileTestController <br>
 * Description : test controller of file service <br>
 * Create Time : May 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Controller("FileUpLoadController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileUpLoadController extends ControllerHelper {

	@Resource
	private FileService service;

	public void uploadFile() throws Exception, ServiceException {
		SysAttachment att = new SysAttachment();
		MultipartHttpServletRequest fileReq = (MultipartHttpServletRequest) servletRequest;
		MultipartFile requestFile = fileReq.getFile("customFilter");
		String fileName= requestFile.getOriginalFilename();
		
		StringBuilder sbUri = new StringBuilder(); // 定义文件存放路径，规则：业务名成+时间(年-月)+文件名
		sbUri.append("/").append("resResume").append("/");
		sbUri.append((new Date().getTime())).append("/");
		sbUri.append(fileName);
		att.setName(fileName);
		att.setDesc("");
		att.setNature("");
		att.setOperatorId(((Token)sessionDTO.get("token")).getUser().getId());
		att.setOperatorName(((Token)sessionDTO.get("token")).getUser().getName());
		att.setRef(1);
		att.setSuffix(StringUtils.getFilenameExtension(fileName));
		att.setOperateTime(new Date());
		att.setUri(sbUri.toString());

		//service.uploadAtt(att, mf.getInputStream());
		String filaPath = service.uploadFileByBasePath(att, requestFile.getInputStream());
		workDTO.setFileResultSuccess(att.getId().toString(), att.getName(), filaPath);
		

	}

	// Integer userId = token.getUser().getId();

	public void testDown() {
		workDTO.letMeControlTheResponseStream();
		try {
			SysAttachment att = service.getAttById(1);
			servletResponse.setContentType("");
			servletResponse
					.setContentType(FileUtils.getMimeType(att.getName()));
			servletResponse.setHeader("Content-disposition",
					"attachment; filename="
							+ new String(att.getName().getBytes("utf-8"),
									"ISO8859-1"));
			// TODO fix this length
			// servletResponse.setHeader("Content-Length",
			// String.valueOf(fileLength));
			service.downloadAtt(1, servletResponse.getOutputStream());
			servletResponse.getOutputStream().close();
		} catch (IOException | ServiceException e) {
		}
	}
}
