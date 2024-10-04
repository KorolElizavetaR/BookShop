package com.bookshop.oz.openlibrary;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bookshop.oz.openlibrary.model.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
public class OpenLibraryConsumer {
	private final String URL = "https://openlibrary.org/search.json?q=%s&fields=%s&limit=%d";
	private final String FIELDS = "cover_i,key,title,author_name,subject,editions,editions.publisher,editions.language,editions.isbn";
	
	private final Integer PAGE_LIMIT = 15;
	
	public List<Document> getBooks(String line) throws IOException, InterruptedException, URISyntaxException
	{
		String buildURL = String.format(URL, URLEncoder.encode(line, "UTF-8"), FIELDS, PAGE_LIMIT);
        
		HttpRequest getRequest = HttpRequest.
			newBuilder().
			uri(new URI(buildURL)).GET().header("Accept", "application/json").build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(getRequest, BodyHandlers.ofString());
		
		JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray docsArray = jsonElement.getAsJsonObject().getAsJsonArray("docs");

        Gson gson = new GsonBuilder().create();
        List<Document> documents = new ArrayList<>();

        for (JsonElement docElement : docsArray) {
            Document document = gson.fromJson(docElement, Document.class);
            documents.add(document);
        }
        return documents;
	}
}
