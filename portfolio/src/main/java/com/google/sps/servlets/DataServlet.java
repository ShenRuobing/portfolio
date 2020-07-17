// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  String lang="en";
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    Query query = new Query("comment").addSort("timestamp", SortDirection.ASCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    String json="{\"quotes\":[";
    int count=0;
    Translate translate = TranslateOptions.getDefaultInstance().getService();

    for (Entity entity : results.asIterable()) {
        String cc = (String) entity.getProperty("contents");
        if(count!=0)
            json+=",";
        count++;
        json+="\"";
        Translation translation =
        translate.translate(cc, Translate.TranslateOption.targetLanguage(lang));
        String translatedText = translation.getTranslatedText();
        json+=translatedText;
        json+="\"";
    }
    json+="]}";
    System.out.print(json);  
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String value = request.getParameter("comment");
    lang=request.getParameter("lang");
    long timestamp = System.currentTimeMillis();
    if (value!="") {
      Entity taskEntity = new Entity("comment");
      taskEntity.setProperty("contents", value);
      taskEntity.setProperty("timestamp", timestamp);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(taskEntity);
    }
    response.sendRedirect("/index.html");
  }
}
