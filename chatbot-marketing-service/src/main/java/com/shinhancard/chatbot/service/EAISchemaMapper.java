package com.shinhancard.chatbot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.shinhancard.chatbot.dto.request.ApplyMarketingRequest;
import com.shinhancard.chatbot.dto.request.GetMarketingRequest;

@Component
public class EAISchemaMapper {
//	
//	public Map<String, Object> mappingEAISchema(String eaiSchema, Object getClass) {
//		Map<String, Object> result = new HashMap<>();
//
//		if ("CBS00029".equals(eaiSchema)) {
//			GetMarketingRequest getMarketingRequest = (GetMarketingRequest)getClass;
//			result.put("CLNN", getMarketingRequest.getUserId());
//			result.put("MO_BJ_TCD", getMarketingRequest.getTargetChannel());
//			if(getMarketingRequest.getShowsApplied()) {
//				result.put("RG_OFF_INC_F", "Y");	
//			}else {
//				result.put("RG_OFF_INC_F", "N");
//			}			
//		}
//		
//
//		return result;
//	}

	public Map<String, Object> mappingEAISchema(String eaiSchema, Object... getObjects) {
		Map<String, Object> result = new HashMap<>();

		for (Object getObject : getObjects) {

			if ("CBS00029".equals(eaiSchema)) {
				if (getObject instanceof GetMarketingRequest) {
					GetMarketingRequest getMarketingRequest = (GetMarketingRequest) getObject;
					result.put("CLNN", getMarketingRequest.getUserId());
					result.put("MO_BJ_TCD", getMarketingRequest.getTargetChannel());
					if (getMarketingRequest.getShowsApplied()) {
						result.put("RG_OFF_INC_F", "Y");
					} else {
						result.put("RG_OFF_INC_F", "N");
					}
				}
				break;
			}

			if ("CBS00030".equals(eaiSchema)) {
				if (getObject instanceof ApplyMarketingRequest) {
					ApplyMarketingRequest applyMarketingRequest = (ApplyMarketingRequest) getObject;
					result.put("CLNN", applyMarketingRequest.getUserId());
					result.put("MO_N", applyMarketingRequest.getMarketingId());
				}
				if(getObject instanceof String) {
					String offerId = (String)getObject;
					result.put("CRD_SV_N", offerId);
				}

			}
		}

		return result;
	}

}
