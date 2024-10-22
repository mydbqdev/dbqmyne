package com.dbq.postservice.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbq.postservice.db.model.AdsCollection;
import com.dbq.postservice.dto.AdsBody;
import com.dbq.postservice.interfeces.AdsApi;
import com.dbq.postservice.service.AdsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")
@RestController
@RequestMapping("/v1/post")
@RequiredArgsConstructor
public class AdsApiController implements AdsApi {

    private static final Logger log = LoggerFactory.getLogger(AdsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    private final AdsService adsService;

    public ResponseEntity<Object> adsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @ModelAttribute AdsBody body) {

            try {
            	
            	return new ResponseEntity<Object>(adsService.createAdss(body) , HttpStatus.OK) {};
          
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

            }
           }

}
