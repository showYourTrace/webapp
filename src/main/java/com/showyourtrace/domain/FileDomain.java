package com.languagelearn.domain;

import com.languagelearn.model.File;
import com.languagelearn.object.encode.FileEncode;
import com.languagelearn.object.response.FileResponse;
import com.languagelearn.repository.FileRepository;
import com.languagelearn.util.TableLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class FileDomain {

    private static final Logger log = LoggerFactory.getLogger(FileDomain.class);

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileEncode fileEncode;
    @Autowired
    private TableLogger tableLogger;

    public FileResponse create(MultipartFile inputFile) {

        byte[] bFile = new byte[(int) inputFile.getSize()];
        try {
            InputStream is = inputFile.getInputStream();
            is.read(bFile);
            is.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        File newFile = new File();

        newFile.setName(inputFile.getName());
        newFile.setSize(inputFile.getSize());
        newFile.setContent(bFile);

        String[] nameParts = inputFile.getName().split("\\.");
        String extension = nameParts.length > 1 ? nameParts[1] : "";

        newFile.setExtension(extension);

        tableLogger.setCreated(newFile);

        fileRepository.save(newFile);

        return fileEncode.encode(newFile);
    }

    public FileResponse get(Long fileId) {
        return fileEncode.encode(fileRepository.findById(fileId));
    }
}
