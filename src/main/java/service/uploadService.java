package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upload.UploadHadoop;

import java.io.IOException;

/*
 *   #auther:李琪
 *   #date: 2019/12/30
 *   #description
 */
@Service
public class uploadService {
    UploadHadoop hadoop;
    @Autowired
    public void setHadoop(UploadHadoop hadoop) {
        this.hadoop = hadoop;
    }

    public  void upload(String path, String name) throws IOException {

        UploadHadoop.upload(path,name);

    }
}
