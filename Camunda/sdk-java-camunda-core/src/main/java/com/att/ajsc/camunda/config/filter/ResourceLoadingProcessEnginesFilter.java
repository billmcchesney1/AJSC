/*******************************************************************************
 * Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
 *  
 *******************************************************************************/
package com.att.ajsc.camunda.config.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.camunda.bpm.spring.boot.starter.webapp.filter.ResourceLoaderDependingFilter;
import org.springframework.beans.factory.annotation.Value;
//import org.camunda.bpm.webapp.impl.engine.ProcessEnginesFilter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ResourceLoadingProcessEnginesFilter extends ProcessEnginesFilter implements ResourceLoaderDependingFilter {

  private ResourceLoader resourceLoader;
  
  @Override
  protected String getWebResourceContents(String name) throws IOException {
    InputStream is = null;

    try {      	
      name = name.replace(CAMUNDA_SUFFIX, "");
      Resource resource = resourceLoader.getResource("classpath:" + name);
      is = resource.getInputStream();

      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      StringWriter writer = new StringWriter();
      String line = null;

      while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.append("\n");
      }

      return writer.toString();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
        }
      }
    }
  }

  /**
   * @return the resourceLoader
   */
  public ResourceLoader getResourceLoader() {
    return resourceLoader;
  }

  /**
   * @param resourceLoader
   *          the resourceLoader to set
   */
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

}
