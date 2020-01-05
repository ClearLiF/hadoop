package upload;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.uploadService;

import java.io.File;
import java.io.IOException;

/*
 *   #auther:李琪
 *   #date: 2019/12/30
 *   #description
 */
@Controller("uploadAction")
public class uploadAction extends ActionSupport {
    private uploadService uploadService;
    @Autowired
    public void setUploadService(service.uploadService uploadService) {
        this.uploadService = uploadService;
    }

    private File photo;
    private String photoContentType;
    //文件名称
    private  String photoFileName;
    @Override
    public String execute() throws Exception {
        return super.execute();
    }
    public String upload() throws IOException {

        String path = ServletActionContext.getServletContext().getRealPath("/upload");
        System.out.println(path);
        System.out.println(photoFileName);
        System.out.println(photo.getName());
        File dest = new File(path,photoFileName);

        FileUtils.copyFile(photo,dest);

        System.out.println(path);
        uploadService.upload(path,photoFileName);

        return SUCCESS;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }
}
