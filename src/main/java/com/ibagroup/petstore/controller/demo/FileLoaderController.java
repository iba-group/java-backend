package com.ibagroup.petstore.controller.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v2/file/")
public class FileLoaderController {

  private Map<String, byte[]> files = new HashMap<>();

  @PostMapping(path = "{fileName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity upload(
      @PathVariable(value = "fileName") String fileName,
      @RequestPart("file") MultipartFile file) throws IOException {
    files.put(fileName, file.getBytes());
    return ResponseEntity.ok().build();
  }

  @GetMapping(path = "{fileName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity download(
      @PathVariable String fileName) {
    byte[] file = files.get(fileName);

    HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION,
        "attachment;filename=\"" + fileName + "\";charset=utf-8");

    return ResponseEntity.ok().headers(header)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .contentLength(file.length)
        .body(file);
  }



}
