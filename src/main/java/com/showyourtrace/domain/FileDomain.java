package com.showyourtrace.domain;

import com.showyourtrace.model.File;
import com.showyourtrace.object.encode.FileEncode;
import com.showyourtrace.object.response.FileResponse;
import com.showyourtrace.repository.FileRepository;
import com.showyourtrace.util.TableLogger;
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
