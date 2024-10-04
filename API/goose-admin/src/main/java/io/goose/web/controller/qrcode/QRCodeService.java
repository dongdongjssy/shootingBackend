package io.goose.web.controller.qrcode;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import io.goose.framework.rest.service.FileUploadDownloadOSSService;



@Service
public class QRCodeService {

   @Autowired
   FileUploadDownloadOSSService fileUploadDownloadService;


   public String createQRCode( String content, int width, int height, String logoImageName )
         throws IOException {
      String resultImage = "";
      if ( !StringUtils.isEmpty( content ) ) {
         ServletOutputStream stream = null;
         ByteArrayOutputStream os = new ByteArrayOutputStream();
         @SuppressWarnings( "rawtypes" )
         HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
         hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );// 指定字符编码格式
         hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M );// 指定二维码纠错等级为中级
         hints.put( EncodeHintType.MARGIN, 2 );// 设置图片的边距
         BufferedImage logoImage = null;
         int logoWidth = width / 5;
         int logoHeight = height / 5;
         if ( logoImageName != null ) {
            logoImage = fileUploadDownloadService.composeImageFullPath( logoImageName );
            if ( logoImage != null ) {
               Image resizedImg =
                     logoImage.getScaledInstance( logoWidth, logoHeight, Image.SCALE_DEFAULT );
               logoImage = new BufferedImage( logoWidth, logoHeight, BufferedImage.TYPE_INT_ARGB );
               logoImage.getGraphics().drawImage( resizedImg, 0, 0, null );
            }
         }

         try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix =
                  writer.encode( content, BarcodeFormat.QR_CODE, width, height, hints );
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage( bitMatrix );

            // Calculate the delta height and width between QR code and logo
            int deltaHeight = qrImage.getHeight() - logoWidth;
            int deltaWidth = qrImage.getWidth() - logoHeight;

            if ( logoImage != null ) {
               // Initialize combined image
               BufferedImage combined = new BufferedImage( qrImage.getHeight(), qrImage.getWidth(),
                     BufferedImage.TYPE_INT_ARGB );
               Graphics2D g = ( Graphics2D ) combined.getGraphics();

               // Write QR code to new image at position 0/0
               g.drawImage( qrImage, 0, 0, null );
               g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f ) );

               // Write logo into combine image at position (deltaWidth / 2) and
               // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
               // the same space for the logo to be centered
               g.drawImage( logoImage, Math.round( deltaWidth / 2 ), Math.round( deltaHeight / 2 ),
                     null );
               // Write combined image as PNG to OutputStream
               ImageIO.write( combined, "png", os );
            } else {
               ImageIO.write( qrImage, "png", os );
            }

            // System.out.println(os.toByteArray());
            // System.out.println(Base64.encode(os.toByteArray()));
            resultImage =
                  new String( "data:image/png;base64," + Base64.encode( os.toByteArray() ) );

            return resultImage;
         } catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
         } finally {
            if ( stream != null ) {
               stream.flush();
               stream.close();
            }
         }
      }
      return null;
   }
}
