package com.example;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
  
  @ModelAttribute
  IndexForm setUpForm() {
    return new IndexForm();
  }
  

  @GetMapping(path="/")
  public String index() throws Exception {

//    try {
//      Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
//      field.setAccessible(true);
//      field.set(null, java.lang.Boolean.FALSE);
//    } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
//        | IllegalAccessException ex) {
//      ex.printStackTrace(System.err);
//    }
//
//    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//    keyGen.init(128);
    return "index";
  }
  
  
  @RequestMapping(path = "/encrypt", method = RequestMethod.POST)
  public String encrypt(Model model, @Validated IndexForm form, BindingResult result) throws Exception {
    if (result.hasErrors()) {
      return "index";
    }
    byte[] secretKeyBytes = form.getSecretKey().getBytes();
    secretKeyBytes = Arrays.copyOf(secretKeyBytes, 16);
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    
    byte[] encryptBytes = cipher.doFinal(form.getTargetEncrypt().getBytes());
    byte[] encryptBytesBase64 = Base64.getEncoder().encode(encryptBytes);
    String encrypted = new String(encryptBytesBase64);
    model.addAttribute("encrypted", encrypted);
    return "index";
  }
}
