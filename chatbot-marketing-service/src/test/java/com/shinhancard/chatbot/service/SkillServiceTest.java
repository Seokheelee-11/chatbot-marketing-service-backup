package com.shinhancard.chatbot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.dto.request.InquiryRequest;
import com.shinhancard.chatbot.dto.response.InquiryResponse;
import com.shinhancard.chatbot.repository.MarketingManageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {
	@Mock
	private EAISkillService eaiSkillService;
	
	MarketingService marketingService;
	
	@InjectMocks
	private EAISchemaMapper eaiSchemaMapper;
	
	@InjectMocks
	private MarketingManageRepository marketingManageRepository;

	@Before
	public void setUp() {
		marketingService = new MarketingService(eaiSchemaMapper, eaiSkillService, marketingManageRepository);
	}
	
	@Test
	public void skillTest() {
		Map<String, Object> testInput = new HashMap<>();
		testInput.put("CLNN", "P123456789");
		testInput.put("MO_BJ_TCD", "F57");
		testInput.put("RG_OFF_INC_F", "N");

		Map<String, Object> testGRID1 = new HashMap<>();
		testGRID1.put("MO_N", "A010ABLDI");
		testGRID1.put("CRD_SV_N", "A011ABLDI");
		testGRID1.put("MO_STD", "20210201");
		testGRID1.put("MO_EDD", "20210228");
		testGRID1.put("OFF_RG_F", "N");

		Map<String, Object> testGRID2 = new HashMap<>();
		testGRID2.put("MO_N", "A010ABLDJ");
		testGRID2.put("CRD_SV_N", "A011ABLDJ");
		testGRID2.put("MO_STD", "20210201");
		testGRID2.put("MO_EDD", "20210228");
		testGRID2.put("OFF_RG_F", "N");

		List<Map<String, Object>> testGRID = new ArrayList<>();
		testGRID.add(testGRID1);
		testGRID.add(testGRID2);

		Map<String, Object> testOutput = new HashMap<>();
		testOutput.put("GRID1", testGRID);

		when(eaiSkillService.callEAISkill("CBS00029", testInput)).thenReturn(testOutput);
		InquiryRequest inquiryRequest = InquiryRequest.builder().showsApplied(false).targetChannel("F57")
				.userId("P123456789").build();
		MarketingInfo marketingInfo1 = new MarketingInfo("신한데이","A010ABLDI","(광고)[신한카드]@@ 고객님 신한카드 이용하시고@@ 쿠폰 받아가세요~@@블라블라블라@@블라블라블라@@블라블라블라@@블라블라블라");
		MarketingInfo marketingInfo2 = new MarketingInfo("신한데이2","A010ABLDJ","22(광고)[신한카드]@@ 고객님 신한카드 이용하시고@@ 쿠폰 받아가세요~@@블라블라블라@@블라블라블라@@블라블라블라@@블라블라블라");
		List<MarketingInfo> marketingInfoes = new ArrayList<MarketingInfo>();
		marketingInfoes.add(marketingInfo1);
		marketingInfoes.add(marketingInfo2);
		
		InquiryResponse inquiryResponse = InquiryResponse.builder().resultCode(ResultCode.SUCCESS).marketingInfoes(marketingInfoes).build();
		
		log.info("\ninput : {}, \noutput : {}\n", inquiryRequest, inquiryResponse);
		marketingService.getMarketing(inquiryRequest);
		log.info("result = {}",marketingService.getMarketing(inquiryRequest));
		
		assertThat(marketingService.getMarketing(inquiryRequest)).isEqualTo(marketingInfoes);
		
	}

}
