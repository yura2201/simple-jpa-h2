package com.stackoverflow.mysamples.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stackoverflow.mysamples.util.ResourceProvider;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 17.10.2022
 */
@Controller
public class ZooController {

  @GetMapping(value = { "/file", "/billing/contractor/contract/file" }, produces = "application/pdf")
  public @ResponseBody byte[] getFile(@RequestParam(value = "id", required = false) UUID id,
      HttpServletResponse response)
      throws IOException {
    response.setHeader("Content-Disposition", "attachment; filename=amt-02.pdf");
    return ResourceProvider.getResourceBytes(this.getClass(), "/amt-02.pdf");
  }
}
