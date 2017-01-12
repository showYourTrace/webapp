package com.languagelearn.controller.admin;

import com.languagelearn.domain.FileDomain;
import com.languagelearn.exception.ErrorHandler;
import com.languagelearn.exception.ErrorResponse;
import com.languagelearn.object.response.FileResponse;
import com.languagelearn.util.ApplicationProperties;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@Controller
@RequestMapping("/admin/api")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private FileDomain fileDomain;
    @Autowired
    private ServletContext context;
    @Autowired
    private ApplicationProperties applicationProperties;


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExceptions(RuntimeException ex, HttpServletResponse response) {
        return errorHandler.handleExceptions(ex, response);
    }

    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("fileId") final Long fileId) {

    }

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public FileResponse upload(@RequestParam("file") MultipartFile inputFile, @RequestParam("html5") String html5Browser) throws IOException, SQLException {
        String fileStoreFolderPath = context.getRealPath("") + applicationProperties.fileStorePath;

        java.io.File f = new java.io.File(fileStoreFolderPath);
        if(!f.exists()) {
            f.mkdir();
        }

        String fileNameForSave = new Date().getTime() + "_" + inputFile.getOriginalFilename();
        String outFileName = fileStoreFolderPath + "/" + fileNameForSave;
        java.io.File outFile = new java.io.File(outFileName);
        outFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(outFile);
        IOUtils.copy(inputFile.getInputStream(), fos);
        fos.close();

        FileResponse response = new FileResponse();
        response.setTitle(inputFile.getOriginalFilename());
        response.setPath(fileNameForSave);

        return response;
    }

    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET)
    @ResponseBody
    public FileResponse getFile(@PathVariable("fileId") final Long fileId) {
        return fileDomain.get(fileId);
    }

    @ResponseBody
    @RequestMapping(value = "/filestore/{fileName}.{extension}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getFileFromFileStore(@PathVariable("fileName") final String fileName,
                                       @PathVariable("extension") final String extension) throws IOException {
        String fileStoreFolderPath = context.getRealPath("") + applicationProperties.fileStorePath;
        final String fullFileName = fileStoreFolderPath+"\\"+fileName+"."+extension;
        FileInputStream in = new FileInputStream(fullFileName);
        return IOUtils.toByteArray(in);
    }
}
