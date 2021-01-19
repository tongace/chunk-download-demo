package com.example.chunkdownloaddemo;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@Controller
class TestController {
    @GetMapping("/download")
    @SneakyThrows
    public void testDownDownLoadLargeFile(HttpServletRequest request, HttpServletResponse response){
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream("/Users/chairat/Playground/Lab/test.pdf");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            byte[] buf = new byte[8192];
            int bytesread = 0, bytesBuffered = 0;
            while ((bytesread = fileInputStream.read(buf)) > -1) {
                out.write(buf, 0, bytesread);
                bytesBuffered += bytesread;
                if (bytesBuffered > 1024 * 1024) { //flush after 1MB
                    bytesBuffered = 0;
                    out.flush();
                }
            }
        } finally {
            if (out != null) {
                out.flush();
            }
        }
    }
}
