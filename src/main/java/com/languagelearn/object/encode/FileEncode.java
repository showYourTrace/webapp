package com.languagelearn.object.encode;

import com.languagelearn.model.File;
import com.languagelearn.object.EncodeEntity;
import com.languagelearn.object.response.FileResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class FileEncode implements EncodeEntity<File, FileResponse> {

    @Override
    public FileResponse encode(File entity) {
        FileResponse response = new FileResponse();
        response.setId(entity.getId());
        response.setCreateDate(entity.getCreatedDate());
        response.setTitle(entity.getName());
        return response;
    }

    @Override
    public Collection<FileResponse> encode(Collection<File> entityCollection) {
        return entityCollection.stream()
                    .map(this::encode)
                    .collect(Collectors.toList());
    }
}
