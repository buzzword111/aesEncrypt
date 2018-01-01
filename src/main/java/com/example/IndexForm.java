package com.example;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class IndexForm {
  @NotEmpty
  private String targetEncrypt;
  @NotEmpty
  private String secretKey;
}
