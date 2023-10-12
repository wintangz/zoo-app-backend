package net.wintang.zooapp.controller;

import com.google.zxing.WriterException;
import net.wintang.zooapp.service.QRGeneratorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @GetMapping(value = "/{data}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQRCodeImage(@PathVariable String data) throws IOException, WriterException {
        int width = 300;
        int height = 300;

        BufferedImage bufferedImage = QRGeneratorService.generateQRCode(data, width, height);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}