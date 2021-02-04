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
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(// @formatter:off
//		classes = {MarketingService.class, EAISchemaMapper.class} 
// @formatter:on
)
//@AutoConfigureMockMvc
public class SkillServiceTest {
	
	@MockBean
	private EAISkillService eaiSkillService;
	
//	@MockBean
//	private MarketingManageRepository marketingManageRepository;
//	
	@Autowired
	private MarketingService marketingService;
	
//	@Autowired
//	private EAISchemaMapper eaiSchemaMapper;
//	
//	@Autowired
//	private MarketingManageRepository marketingManageRepository;
//
//	@Before
//	public void setUp() {
//		marketingService = new MarketingService(eaiSchemaMapper, eaiSkillService, marketingManageRepository);
//	}

	
	
	@Test
	public void inquiryTest() {
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
		
		log.info("when 들어가는 중");

		when(eaiSkillService.callEAISkill("CBS00029", testInput)).thenReturn(testOutput);
		log.info("callEAISkill : {}", eaiSkillService.callEAISkill("CBS00029", testInput));
		assertThat(this.eaiSkillService.callEAISkill("CBS00029", testInput)).isEqualTo(testOutput);
		
		InquiryRequest inquiryRequest = InquiryRequest.builder().showsApplied(false)
				.targetChannel("F57")
				.userId("P123456789")
				.start(0)
				.size(0).build();
//		InquiryRequest inquiryRequest = InquiryRequest.builder().showsApplied(false).targetChannel("F57")
//				.userId("P123456789").build();
		MarketingInfo marketingInfo1 = new MarketingInfo("신한데이1","A010ABLDI","(광고)[신한카드]@@ 고객님 신한카드 이용하시고@@ 쿠폰 받아가세요~@@블라블라블라@@블라블라블라@@블라블라블라@@블라블라블라");
		MarketingInfo marketingInfo2 = new MarketingInfo("신한데이2","A010ABLDJ","22(광고)[신한카드]@@ 고객님 신한카드 이용하시고@@ 쿠폰 받아가세요~@@블라블라블라@@블라블라블라@@블라블라블라@@블라블라블라");
		List<MarketingInfo> marketingInfoes = new ArrayList<MarketingInfo>();
		marketingInfoes.add(marketingInfo1);
		marketingInfoes.add(marketingInfo2);
		
		InquiryResponse inquiryResponse = InquiryResponse.builder().resultCode(ResultCode.SUCCESS).marketingInfoes(marketingInfoes).build();
		
		log.info("\ninput : {}, \noutput : {}\n", inquiryRequest, inquiryResponse);
		log.info("result = {}",this.marketingService.inquiryMarketing(inquiryRequest));
		assertThat(marketingService.inquiryMarketing(inquiryRequest)).isEqualTo(marketingInfoes);
	}
	
	@Test
	public void applyTest() {
		Map<String, Object> testInput1 = new HashMap<>();
		testInput1.put("CLNN", "P123456789");
		testInput1.put("CRD_SV_N", "ATF2201203");
		testInput1.put("MO_N", "A010ABLDI");

		Map<String, Object> testOutput1 = new HashMap<>();
		testOutput1.put("_RESULT_CODE", "CLSN0020");
		testOutput1.put("PS_CCD", "01");

		Map<String, Object> testInput2 = new HashMap<>();
		testInput2.put("CLNN", "P123456789");
		testInput2.put("CRD_SV_N", "ATF2201204");
		testInput2.put("MO_N", "A010ABLDI");

		Map<String, Object> testOutput2 = new HashMap<>();
		testOutput2.put("_RESULT_CODE", "CLSN0020");
		testOutput2.put("PS_CCD", "02");

		
		log.info("when 들어가는 중");

		when(eaiSkillService.callEAISkill("CBS00030", testInput1)).thenReturn(testOutput1);
		when(eaiSkillService.callEAISkill("CBS00030", testInput2)).thenReturn(testOutput2);
		
		ApplyRequest applyRequest = ApplyRequest.builder().userId("P123456789").marketingId("A010ABLDI").build();
		ApplyResponse applyResponse = ApplyResponse.builder().resultCode(ResultCode.SUCCESS).marketingName("신한데이1").responseMessage("등록실패").build();		
		assertThat(marketingService.applyMarketing(applyRequest).getMarketingName()).isEqualTo(applyResponse.getMarketingName());
		assertThat(marketingService.applyMarketing(applyRequest).getResponseMessage()).isEqualTo(applyResponse.getResponseMessage());
	}
	

}
