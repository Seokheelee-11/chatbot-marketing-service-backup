package com.shinhancard.chatbot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.dto.request.ApplyRequest;
import com.shinhancard.chatbot.dto.request.InquiryRequest;
import com.shinhancard.chatbot.dto.response.ApplyResponse;
import com.shinhancard.chatbot.entity.MarketingManage;
import com.shinhancard.chatbot.repository.MarketingManageRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
@Builder
public class MarketingService {

	private final EAISchemaMapper eaiSchemaMapper;
	private final EAISkillService eaiSkillService;
	private final MarketingManageRepository marketingManageRepository;

	public List<MarketingInfo> inquiryMarketing(InquiryRequest inquiryRequest) {
		log.info("getEaiResponse start");
		Map<String, Object> eaiMaprequest = eaiSchemaMapper.mappingEAISchema("CBS00029", inquiryRequest);
		log.info("mapping eai schema result : {}", eaiMaprequest);
		Map<String, Object> eaiMapResponse = eaiSkillService.callEAISkill("CBS00029", eaiMaprequest);
		
		List<MarketingInfo> marketingInfoes = getMarketingInfoes((List<Map<String, Object>>) eaiMapResponse.get("GRID1"));
		return reSizingMarketingInfoes(marketingInfoes, inquiryRequest.getStart(), inquiryRequest.getSize());
	}

	public ApplyResponse applyMarketing(ApplyRequest applyRequest) {
		MarketingManage marketingManage = new MarketingManage();
		List<String> offerIds = new ArrayList<>();

		try {
			marketingManage = marketingManageRepository.findOneByMarketingId(applyRequest.getMarketingId());
			offerIds = marketingManage.getOffers();
		} catch (Exception e) {
			return ApplyResponse.builder().resultCode(ResultCode.FAILED).build();
		}
		
		List<Map<String, Object>> applyEAIResponses = new ArrayList<>();
		
		log.info("getEaiResponse start");
		for (String offerId : offerIds) {
			Map<String, Object> eaiMaprequest = eaiSchemaMapper.mappingEAISchema("CBS00030", applyRequest, offerId);
			log.info("mapping eai schema result : {}", eaiMaprequest);			
			Map<String, Object> eaiMapResponse = eaiSkillService.callEAISkill("CBS00030", eaiMaprequest);
			applyEAIResponses.add(eaiMapResponse);
		}		
		
		return getApplyMarketingInfoes(marketingManage, getStatus(applyEAIResponses));
	}

	public List<MarketingInfo> reSizingMarketingInfoes(List<MarketingInfo> marketingInfoes, Integer start,
			Integer size) {
		List<MarketingInfo> result = new ArrayList<>();
		try {
			if(size!=0) {
				for (int i = start; i < start + size && i < marketingInfoes.size(); i++) {
					result.add(marketingInfoes.get(i));
				}	
			}else {
				result = marketingInfoes;
			}				
		} catch (Exception e) {
			result = marketingInfoes;
		}
		return result;

	}

	public Map<String, Object> getInqueryEAIResponse(InquiryRequest inquiryRequest) {
		log.info("getEaiResponse start");
		Map<String, Object> eaiMaprequest = eaiSchemaMapper.mappingEAISchema("CBS00029", inquiryRequest);
		log.info("mapping eai schema result : {}", eaiMaprequest);
		Map<String, Object> eaiMapResponse = eaiSkillService.callEAISkill("CBS00029", eaiMaprequest);
		return eaiMapResponse;
	}

	public List<Map<String, Object>> getApplyEAIResponse(ApplyRequest applyRequest, List<String> offerIds) {
		List<Map<String, Object>> result = new ArrayList<>();
		log.info("getEaiResponse start");
		for (String offerId : offerIds) {
			Map<String, Object> eaiMaprequest = eaiSchemaMapper.mappingEAISchema("CBS00030", applyRequest, offerId);
			log.info("mapping eai schema result : {}", eaiMaprequest);			
			Map<String, Object> eaiMapResponse = eaiSkillService.callEAISkill("CBS00030", eaiMaprequest);
			result.add(eaiMapResponse);
		}
		return result;
	}
	

	public Boolean getStatus(List<Map<String, Object>> getApplyResponses) {
		Boolean result = true;
		if (getApplyResponses.isEmpty()) {
			result = false;
		} else {
			for (Map<String, Object> getApplyResponse : getApplyResponses) {
				if (!"01".equals(String.valueOf(getApplyResponse.get("PS_CCD")))) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public ApplyResponse getApplyMarketingInfoes(MarketingManage marketingManage, Boolean status) {
		return ApplyResponse.builder().resultCode(status ? ResultCode.SUCCESS : ResultCode.FAILED)
				.responseMessage(status ? marketingManage.getSuccessMessage() : marketingManage.getFailureMessage())
				.marketingName(marketingManage.getMarketingName()).build();
	}

	public List<String> getOfferIds(String marketingId) {
		List<String> result = new ArrayList<>();
		try {
			MarketingManage marketingManage = marketingManageRepository.findOneByMarketingId(marketingId);
			result = marketingManage.getOffers();
		} catch (Exception e) {
		}
		return result;
	}

	public List<MarketingInfo> getMarketingInfoes(List<Map<String, Object>> marketingList) {
		List<MarketingInfo> marketingInfoes = new ArrayList<>();

		for (Map<String, Object> marketing : marketingList) {
			String marketingId = (String) marketing.get("MO_N");
			try {
				MarketingManage marketingManage = marketingManageRepository.findOneByMarketingId(marketingId);
				if (marketingManage.canApply()) {
					MarketingInfo marketingInfo = mappingMarketingInfo(marketingManage);
					marketingInfoes.add(marketingInfo);
				}
			} catch (Exception e) {
				continue;
			}
		}
		return marketingInfoes;
	}

	public MarketingInfo mappingMarketingInfo(MarketingManage marketingManage) {
		MarketingInfo result = new MarketingInfo();
		result.setMarketingId(marketingManage.getMarketingId());
		result.setMarketingName(marketingManage.getMarketingName());
		result.setDescription(marketingManage.getDescription());
		return result;
	}

}
