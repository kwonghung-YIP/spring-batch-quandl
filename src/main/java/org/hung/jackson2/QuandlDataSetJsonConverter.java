package org.hung.jackson2;

import java.io.IOException;

import org.hung.pojo.QuandlDataSet;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

//@JsonComponent
public class QuandlDataSetJsonConverter extends JsonDeserializer<QuandlDataSet> {

	@Override
	public QuandlDataSet deserialize(JsonParser parser, DeserializationContext context) 
			throws IOException, JsonProcessingException {

		TreeNode rootNode = parser.getCodec().readTree(parser);
		
		TreeNode datasetNode = rootNode.get("dataset");
		
		ObjectMapper mapper = new ObjectMapper();
		
		//mapper.treeToValue(datasetNode, valueType)
		return null;
	}

}
