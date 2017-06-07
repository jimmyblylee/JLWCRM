package com.asdc.jbp.crm.controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unused")
@Controller("ImageController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImageController extends ControllerHelper {

    public void convertFileToBase64() {
        try {
            MultipartHttpServletRequest fileReq = (MultipartHttpServletRequest) servletRequest;
            InputStream uploadFileStream = fileReq.getFile("salesImage").getInputStream();
            Base64 encoder = new Base64();
            byte[] data = new byte[uploadFileStream.available()];
            //noinspection ResultOfMethodCallIgnored
            uploadFileStream.read(data);
            uploadFileStream.close();
            String base64String = encoder.encodeToString(data);
            workDTO.setResult(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertContactFileToBase64() {
        try {
            MultipartHttpServletRequest fileReq = (MultipartHttpServletRequest) servletRequest;
            InputStream uploadFileStream = fileReq.getFile("contactImage").getInputStream();
            Base64 encoder = new Base64();
            byte[] data = new byte[uploadFileStream.available()];
            //noinspection ResultOfMethodCallIgnored
            uploadFileStream.read(data);
            uploadFileStream.close();
            String base64String = encoder.encodeToString(data);
            workDTO.setResult(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
