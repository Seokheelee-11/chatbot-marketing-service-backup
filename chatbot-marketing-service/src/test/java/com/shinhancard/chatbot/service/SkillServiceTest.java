package com.shinhancard.chatbot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.shinhancard.chatbot.domain.MarketingInfo;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.dto.request.ApplyRequest;
import com.shinhancard.chatbot.dto.request.InquiryRequest;
import com.shinhancard.chatbot.dto.response.ApplyResponse;
import com.shinhancard.chatbot.dto.response.InquiryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest(// @formatter:off
//		classes = {MarketingService.class, EAISchemaMapper.class} 
// @formatter:on
)
//@AutoConfigureMockMvc
public class SkillServiceTest {

	@MockBean
	private EAISkillService eaiSkillService;

	@Autowired
	private MarketingService marketingService;


	@Test
	public void inquiryTest() {
		setInquiryEaiSkillService();

		InquiryRequest inquiryRequest = InquiryRequest.builder().showsApplied(false).targetChannel("F57")
				.userId("P123456789").start(0).size(0).build();

		log.info("Input inquiry Request : {} ", inquiryRequest);

		MarketingInfo marketingInfo1 = new MarketingInfo("신한데이1", "A010ABLDI",
				"(광고)[신한카드]@@ 고객님 신한카드 이용하시고@@ 쿠폰 받아가세요~@@블라블라블라@@블라블라블라@@블라블라블라@@블라블라블라");
		MarketingInfo marketingInfo2 = new MarketingInfo("신한데이2", "A010ABLDJ",
				"22(광고)[신한카드]@@ 고객님 신한카드 이용하시고@@ 쿠폰 받아가세요~@@블라블라블라@@블라블라블라@@블라블라블라@@블라블라블라");
		List<MarketingInfo> marketingInfoes = new ArrayList<MarketingInfo>();
		marketingInfoes.add(marketingInfo1);
		marketingInfoes.add(marketingInfo2);

		InquiryResponse inquiryResponse = InquiryResponse.builder().resultCode(ResultCode.SUCCESS)
				.marketingInfoes(marketingInfoes).build();

		log.info("\ninput : {}, \noutput : {}\n", inquiryRequest, inquiryResponse);
		log.info("result = {}", this.marketingService.inquiryMarketing(inquiryRequest));

		assertThat(marketingService.inquiryMarketing(inquiryRequest)).isEqualTo(marketingInfoes);
	}

	@Test
	public void applyTest_Failed() {
		setApplyEAISkillService_Failed();

		ApplyRequest applyRequest = ApplyRequest.builder().userId("P123456789").marketingId("A010ABLDI").build();
		ApplyResponse applyResponse = ApplyResponse.builder().resultCode(ResultCode.SUCCESS).marketingName("신한데이1")
				.responseMessage("등록실패").build();
		assertThat(marketingService.applyMarketing(applyRequest).getMarketingName())
				.isEqualTo(applyResponse.getMarketingName());
		assertThat(marketingService.applyMarketing(applyRequest).getResponseMessage())
				.isEqualTo(applyResponse.getResponseMessage());
	}
	
	@Test
	public void applyTest_Success() {
		setApplyEAISkillService_Success();

		ApplyRequest applyRequest = ApplyRequest.builder().userId("P123456789").marketingId("A010ABLDI").build();
		ApplyResponse applyResponse = ApplyResponse.builder().resultCode(ResultCode.SUCCESS).marketingName("신한데이1")
				.responseMessage("등록성공").build();
		assertThat(marketingService.applyMarketing(applyRequest).getMarketingName())
				.isEqualTo(applyResponse.getMarketingName());
		assertThat(marketingService.applyMarketing(applyRequest).getResponseMessage())
				.isEqualTo(applyResponse.getResponseMessage());
	}

	

	public void setInquiryEaiSkillService() {
		Map<String, Object> test29Input = new HashMap<>();
		test29Input.put("CLNN", "P123456789");
		test29Input.put("MO_BJ_TCD", "F57");
		test29Input.put("RG_OFF_INC_F", "N");

		Map<String, Object> test29GRID1 = new HashMap<>();
		test29GRID1.put("MO_N", "A010ABLDI");
		test29GRID1.put("CRD_SV_N", "A011ABLDI");
		test29GRID1.put("MO_STD", "20210201");
		test29GRID1.put("MO_EDD", "20210228");
		test29GRID1.put("OFF_RG_F", "N");

		Map<String, Object> test29GRID2 = new HashMap<>();
		test29GRID2.put("MO_N", "A010ABLDJ");
		test29GRID2.put("CRD_SV_N", "A011ABLDJ");
		test29GRID2.put("MO_STD", "20210201");
		test29GRID2.put("MO_EDD", "20210228");
		test29GRID2.put("OFF_RG_F", "N");

		List<Map<String, Object>> test29GRID = new ArrayList<>();
		test29GRID.add(test29GRID1);
		test29GRID.add(test29GRID2);

		Map<String, Object> testOutput = new HashMap<>();
		testOutput.put("GRID1", test29GRID);

		when(eaiSkillService.callEAISkill("CBS00029", test29Input)).thenReturn(testOutput);
		log.info("callEAISkill : {}", eaiSkillService.callEAISkill("CBS00029", test29Input));
	}
	
	

	public void setApplyEAISkillService_Failed() {

		Map<String, Object> test30Input1 = new HashMap<>();
		test30Input1.put("CLNN", "P123456789");
		test30Input1.put("CRD_SV_N", "ATF2201203");
		test30Input1.put("MO_N", "A010ABLDI");

		Map<String, Object> test30Output1 = new HashMap<>();
		test30Output1.put("_RESULT_CODE", "CLSN0020");
		test30Output1.put("PS_CCD", "01");

		Map<String, Object> test30Input2 = new HashMap<>();
		test30Input2.put("CLNN", "P123456789");
		test30Input2.put("CRD_SV_N", "ATF2201204");
		test30Input2.put("MO_N", "A010ABLDI");

		Map<String, Object> test30Output2 = new HashMap<>();
		test30Output2.put("_RESULT_CODE", "CLSN0020");
		test30Output2.put("PS_CCD", "02");

		when(eaiSkillService.callEAISkill("CBS00030", test30Input1)).thenReturn(test30Output1);
		when(eaiSkillService.callEAISkill("CBS00030", test30Input2)).thenReturn(test30Output2);
		log.info("callEAISkill : {}", eaiSkillService.callEAISkill("CBS00030", test30Input1));
		log.info("callEAISkill : {}", eaiSkillService.callEAISkill("CBS00030", test30Input2));
	}
	
	public void setApplyEAISkillService_Success() {

		Map<String, Object> test30Input1 = new HashMap<>();
		test30Input1.put("CLNN", "P123456789");
		test30Input1.put("CRD_SV_N", "ATF2201203");
		test30Input1.put("MO_N", "A010ABLDI");

		Map<String, Object> test30Output1 = new HashMap<>();
		test30Output1.put("_RESULT_CODE", "CLSN0020");
		test30Output1.put("PS_CCD", "01");

		Map<String, Object> test30Input2 = new HashMap<>();
		test30Input2.put("CLNN", "P123456789");
		test30Input2.put("CRD_SV_N", "ATF2201204");
		test30Input2.put("MO_N", "A010ABLDI");

		Map<String, Object> test30Output2 = new HashMap<>();
		test30Output2.put("_RESULT_CODE", "CLSN0020");
		test30Output2.put("PS_CCD", "01");

		when(eaiSkillService.callEAISkill("CBS00030", test30Input1)).thenReturn(test30Output1);
		when(eaiSkillService.callEAISkill("CBS00030", test30Input2)).thenReturn(test30Output2);
		log.info("callEAISkill : {}", eaiSkillService.callEAISkill("CBS00030", test30Input1));
		log.info("callEAISkill : {}", eaiSkillService.callEAISkill("CBS00030", test30Input2));
	}

}
