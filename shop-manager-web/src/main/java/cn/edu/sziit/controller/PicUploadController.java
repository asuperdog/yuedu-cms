package cn.edu.sziit.controller;

import cn.edu.sziit.bean.PicUploadResult;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@CrossOrigin
@RestController
public class PicUploadController {
    @Autowired
    private FastFileStorageClient storageClient;

    // 读取properties里面的属性值，注入服务器地址
    @Value("${image_server_url}")
    private String IMAGE_SERVER_URL;
    private static final String[] TYPE = {".jpg", ".jpeg", ".png", ".bmp", ".gif"};

    // filePostName : "uploadFile",     //上传文件名
    // uploadJson : '/rest/pic/upload', //图片上传请求路径
    // dir : "image"                    //上传文件类型

    @RequestMapping("/rest/pic/upload")
    public PicUploadResult upload(MultipartFile uploadFile) throws Exception {
        // 声明标志位
        boolean flag = false;

        // 初始化返回数据，初始化上传失败 1
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(1);

        // 校验后缀
        for (String type : TYPE) {
            String oname = uploadFile.getOriginalFilename();
            // 如果后缀是要求的格式结尾，标志位设置为true，跳出循环
            if (StringUtils.endsWithIgnoreCase(oname, type)) {
                flag = true;
                break;
            }
        }

        // 如果校验失败，直接返回
        if (!flag) {
            return picUploadResult;
        }

        // 重置标志位
        flag = false;

        // 图片内容校验
        try {
            BufferedImage image = ImageIO.read(uploadFile.getInputStream());
            if (image != null) {
                picUploadResult.setHeight(String.valueOf(image.getHeight()));
                picUploadResult.setWidth(String.valueOf(image.getWidth()));
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 校验成功，使用FastDFS上传图片
        if (flag) {
            // 获取上传文件的后缀名
            String ext = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");

            // 使用StorageClient上传图片(文件输入流对象, 文件的大小, 文件的后缀, 文件的元数据)
            StorePath storePath = storageClient.uploadFile(uploadFile.getInputStream(), uploadFile.getSize(), ext, null);

            // 进行返回的结果的拼接，上传图片的url
            String picUrl = IMAGE_SERVER_URL + storePath.getFullPath();

            // 设置图片的url
            picUploadResult.setUrl(picUrl);

            // 上传成功设置为 0
            picUploadResult.setError(0);
        }
        return picUploadResult;
    }
}
