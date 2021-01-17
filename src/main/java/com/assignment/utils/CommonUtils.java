package com.assignment.utils;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.WriteConcern;

import lombok.extern.log4j.Log4j2;

/**
 * Common Utility to perform database operations
 */

@Component
@Log4j2
public class CommonUtils {

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void writeConcern() {
		mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
	}

	public <T> void save(T entityToSave) {
		if (entityToSave != null) {
			mongoTemplate.save(entityToSave);
		}

	}

	public <T> T insert(T entityToSave) {
		if (entityToSave != null) {
			return mongoTemplate.insert(entityToSave);
		}
		return null;
	}


	public <T> void remove(T entityToDelete) {
		if (null != entityToDelete) {
			mongoTemplate.remove(entityToDelete);
		}
	}

	public <T> void save(T entityToSave, String collectionName) {
		if (entityToSave != null) {
			mongoTemplate.save(entityToSave, collectionName);
		}
	}

	public <T> void save(List<T> entityList) {
		if (null != entityList && !entityList.isEmpty()) {
			for (T t : entityList) {
				mongoTemplate.save(t);
			}
		}
	}

	public <T> T findOne(Map<String, Object> params, Class<T> entityClass) {
		Query query = new Query();
		if (params == null || params.isEmpty()) {
			return null;
		} else {
			for (Entry<String, Object> entry : params.entrySet()) {
				query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
			}
		}
		return mongoTemplate.findOne(query, entityClass);
	}

	public <T> T findOne(Query query, Class<T> entityClass) {
		return mongoTemplate.findOne(query, entityClass);
	}

	public <T> List<T> find(Query query, Class<T> entityClass) {
		return mongoTemplate.find(query, entityClass);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return mongoTemplate.findAll(entityClass);
	}

	public <T> List<T> findAll(Map<String, Object> params, Class<T> entityClass) {
		Query query = new Query();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
			}
		}
		return mongoTemplate.find(query, entityClass);
	}

	public static String postRequestMethod(String url, Object object, Map<String, String> header, String contentType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			ResponseHandler<String> hanlder = new BasicResponseHandler();
			HttpPost postRequest = new HttpPost(url);
			StringEntity input = null;
			if (null != contentType && !contentType.isEmpty()) {
				if (contentType.equals(Constants.CONTENT_TYPE_JSON)) {
					String json = new Gson().toJson(object);
					input = new StringEntity(json);
				} else {
					input = new StringEntity((String) object);
				}
				input.setContentType(contentType);
			}
			if (null != header) {
				for (Map.Entry<String, String> element : header.entrySet()) {
					postRequest.addHeader(element.getKey(), element.getValue());
				}
			}
			postRequest.setEntity(input);
			return httpClient.execute(postRequest, hanlder);
		} catch (HttpResponseException e) {
			log.error("POST method call failed for :" + url, e);
			return Constants.HTTP_SERVICE_FAILED;
		} catch (Exception e) {
			log.error(Constants.ERROR_TRY_AGAIN, e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				log.error(Constants.ERROR_TRY_AGAIN, e);
			}
		}
		return null;
	}

	public static String getRequestMethod(String url, Map<String, String> header) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			ResponseHandler<String> handler = new BasicResponseHandler();
			HttpGet getRequest = new HttpGet(url);
			if (null != header) {
				for (Map.Entry<String, String> element : header.entrySet()) {
					getRequest.addHeader(element.getKey(), element.getValue());
				}
			}
			return httpClient.execute(getRequest, handler);
		} catch (HttpResponseException e) {
			log.error("GET method call failed for :" + url, e);
			return Constants.HTTP_SERVICE_FAILED;
		} catch (Exception e) {
			log.error(Constants.ERROR_TRY_AGAIN, e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				log.error(Constants.ERROR_TRY_AGAIN, e);
			}
		}
		return null;
	}

	public static Map<String, String> prepareAuthHeader(String user, String password) {
		Map<String, String> authHeader = new HashMap<>();
		String authString = user + ":" + password;
		authHeader.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(authString.getBytes()));
		return authHeader;
	}
	
	public static <T> T extractObjectFromString(String object, Class<T> type) {
		try {
			return new ObjectMapper().readValue(object, type);
		} catch (Exception e) {
			log.error("Unable to convert:", e);
		}
		return null;
	}

	public <T> T findAndModify(String collectionName, Class<T> entityClass, Map<String, Object> updates,
							   Map<String, Object> queries) {
		Query query = new Query();
		if (collectionName == null || collectionName.isEmpty()) {
			return null;
		} else {
			for(Entry<String, Object> entry : queries.entrySet()){
				query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
			}
			Update update = new Update();
			for(Entry<String, Object> entry : updates.entrySet()){
				update.set(entry.getKey(), entry.getValue());
			}
			return mongoTemplate.findAndModify(query, update, entityClass);
		}
	}

	public <T> List<T> findAllUnpaidOrders(Class<T> entityClass) {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").nin(Collections.singleton("paid"))).with(Sort.by(Sort.Direction.DESC, "_id"));
		return mongoTemplate.find(query, entityClass);
	}
}