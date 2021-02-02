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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketingService {

	
	private final EAISchemaMapper eaiSchemaMapper;
	private final EAISkillService eaiSkillService;
	private final MarketingManageRepository marketingManageRepository;

	public List<MarketingInfo> getMarketing(InquiryRequest inquiryRequest) {

		Map<String, Object> eaiMaprequest = eaiSchemaMapper.mappingEAISchema("CBS00029", inquiryRequest);
		Map<String, Object> eaiMapresponse = eaiSkillService.callEAISkill("CBS00029", eaiMaprequest);

		
		return getMarketingInfoes((List<Map<String, Object>>)eaiMapresponse.get("GRID1"));
	}

	public ApplyResponse applyMarketing(ApplyRequest applyMarketingRequest) {
		MarketingManage marketingManage = new MarketingManage();
		List<String> offerIds = new ArrayList<>();
		try {
			marketingManage = marketingManageRepository.findOneByMarketingId(applyMarketingRequest.getMarketingId());
			offerIds = marketingManage.getOffers();
		} catch (Exception e) {
			return ApplyResponse.builder()
					.resultCode(ResultCode.FAILED)
					.build();
		}

		List<Map<String, Object>> getResponse = new ArrayList<>();
		for (String offerId : offerIds) {
			Map<String, Object> requestEAIMap = eaiSchemaMapper.mappingEAISchema("CBS00030", applyMarketingRequest,
					offerId);
			Map<String, Object> responseEAIMap = eaiSkillService.callEAISkill("CBS00030", requestEAIMap);
			getResponse.add(responseEAIMap);
		}

		return getApplyMarketingInfoes(marketingManage, getStatus(getResponse));
	}

	public Boolean getStatus(List<Map<String, Object>> getResponses) {
		Boolean result = true;
		for (Map<String, Object> getResponse : getResponses) {
			if (!"01".equals(String.valueOf(getResponse.get("PS_CCD")))) {
				result = false;
				break;
			}
		}
		return result;
	}

	public ApplyResponse getApplyMarketingInfoes(MarketingManage marketingManage, Boolean status) {
		return ApplyResponse.builder()
				.resultCode(status ? ResultCode.SUCCESS : ResultCode.FAILED)
				.responseMessage(status ? marketingManage.getSuccessMessage() : marketingManage.getFailureMessage())
				.marketingName(marketingManage.getMarketingName())
				.build();
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
