package com.shinhancard.chatbot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.dto.request.ApplyMarketingRequest;
import com.shinhancard.chatbot.dto.request.GetMarketingRequest;
import com.shinhancard.chatbot.dto.response.ApplyMarketingResponse;
import com.shinhancard.chatbot.dto.response.GetMarketingResponse;
import com.shinhancard.chatbot.entity.MarketingManage;
import com.shinhancard.chatbot.repository.MarketingManageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketingService {

	private final EAISchemaMapper eaiSchemaMapper;
	private final RequestEAISkill requestEAISkill;
	private final MarketingManageRepository marketingManageRepository;

	public GetMarketingResponse getMarketing(GetMarketingRequest getMarketingRequest) {

		Map<String, Object> requestEAIMap = eaiSchemaMapper.mappingEAISchema("CBS00029", getMarketingRequest);
		Map<String, Object> responseEAIMap = requestEAISkill.requestEAISkill("CBS00029", requestEAIMap);

		return getGetMarketingResponse(responseEAIMap);
	}

	// TODO:: request 받아서 eai 호출 후 결과값을 전달
	public ApplyMarketingResponse applyMarketing(ApplyMarketingRequest applyMarketingRequest) {
		MarketingManage marketingManage = new MarketingManage();
		List<String> offerIds = new ArrayList<>();
		try {
			marketingManage = marketingManageRepository.findOneByMarketingId(applyMarketingRequest.getMarketingId());
			offerIds = marketingManage.getOffers();
		} catch (Exception e) {
			return ApplyMarketingResponse.builder()
					.resultCode(ResultCode.FAILED)
					.build();
		}

		List<Map<String, Object>> getResponse = new ArrayList<>();
		for (String offerId : offerIds) {
			Map<String, Object> requestEAIMap = eaiSchemaMapper.mappingEAISchema("CBS00030", applyMarketingRequest,
					offerId);
			Map<String, Object> responseEAIMap = requestEAISkill.requestEAISkill("CBS00030", requestEAIMap);
			getResponse.add(responseEAIMap);
		}

		return getApplyMarketingInfoes(marketingManage, makeBolean(getResponse));
	}

	public Boolean makeBolean(List<Map<String, Object>> getResponses) {
		Boolean result = true;
		for (Map<String, Object> getResponse : getResponses) {
			if (!"01".equals(String.valueOf(getResponse.get("PS_CCD")))) {
				result = false;
				break;
			}
		}
		return result;
	}

	// TODO :: 처리 해주어야
	public ApplyMarketingResponse getApplyMarketingInfoes(MarketingManage marketingManage, Boolean getBoolean) {
		return ApplyMarketingResponse.builder()
				.resultCode(getBoolean ? ResultCode.SUCCESS : ResultCode.FAILED)
				.responseMessage(getBoolean ? marketingManage.getSuccessMessage() : marketingManage.getFailureMessage())
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

	public GetMarketingResponse getGetMarketingResponse(Map<String, Object> responseEAIMap) {
		GetMarketingResponse result = new GetMarketingResponse();

		List<Map<String, Object>> GRID1 = (List<Map<String, Object>>) responseEAIMap.get("GRID1");

		for (Map<String, Object> GRID : GRID1) {
			String marketingId = (String) GRID.get("MO_N");
			try {
				MarketingManage marketingManage = marketingManageRepository.findOneByMarketingId(marketingId);
				if (marketingManage.canApply()) {
					MarketingInfo marketingInfo = mappingMarketingInfo(marketingManage);
					result.addMarketingInfo(marketingInfo);
				}
			} catch (Exception e) {
				continue;
			}
		}

		result.setSize();
		result.setResultCode();

		return result;
	}

	public MarketingInfo mappingMarketingInfo(MarketingManage marketingManage) {
		MarketingInfo result = new MarketingInfo();
		result.setMarketingId(marketingManage.getMarketingId());
		result.setMarketingName(marketingManage.getMarketingName());
		result.setDiscription(marketingManage.getDiscription());
		return result;
	}

}
