package com.stl.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stl.api.ApiConsts;
import com.stl.api.util.AdmeshUtils;
import com.stl.api.util.StatusCode;
import com.stl.common.models.EntityId;
import com.stl.common.models.ModelQuote;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(ApiConsts.API_V1_ENDPOINT + "/quotes")
public class QuotesController extends BaseController {
    @ApiOperation(
            value = "Post a file upload to create a quote", 
            notes = "Post a file upload, generates and returns a quote or an error code",
            response = EntityId.class)
    @RequestMapping(
            method = RequestMethod.POST, 
            produces = {JSON_ACCEPT_HEADER})
    @SuppressWarnings("rawtypes")
    public @ResponseBody ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                //create a temp file
                byte[] bytes = file.getBytes();
                File temp = File.createTempFile("tempfile", ".tmp");  
                FileOutputStream fos = new FileOutputStream(temp);
                fos.write(bytes);
                fos.close();
                
                String absolutePath = temp.getAbsolutePath();
                
                BigDecimal vol = AdmeshUtils.getVolume(absolutePath);
                return respond(new ModelQuote(vol.doubleValue()));
            } catch (Exception e) {
                return respond(new StatusCode(1000, "unable to read file upload"));
            }
        } else {
            return respond(new StatusCode(1001, "empty file provided"));
        }
    }
}
