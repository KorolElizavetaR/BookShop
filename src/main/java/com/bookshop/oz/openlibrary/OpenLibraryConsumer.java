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
	private final String basicURL = "https://openlibrary.org/search.json?";
	private final String searchedByTitle = "q=%s";
	private final String fields = "fields=cover_i,key,title,author_name,editions,editions.publisher,editions.language,editions.isbn";
	private final String limit = "limit=%d";
	
	private final Integer PAGE_LIMIT = 10;
	
	public List<Document> getBooks(String line) throws IOException, InterruptedException, URISyntaxException
	{
		line = URLEncoder.encode(line, "UTF-8");
		String URL = String.format(basicURL+searchedByTitle+"&"+fields+"&"+limit, line, PAGE_LIMIT);
        
		HttpRequest getRequest = HttpRequest.
			newBuilder().
			uri(new URI(URL)).GET().header("Accept", "application/json").build();
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
