package io.goose.web.controller.qrcode;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping( "/qrcode" )
public class QRCodeRestController {

   @Autowired
   QRCodeService qrCodeService;


   @RequestMapping( value = "/getQRCode" )
   public String getQRCode( String id ) throws IOException {
      return qrCodeService.createQRCode( id, 200, 200, "none" );
   }

}
