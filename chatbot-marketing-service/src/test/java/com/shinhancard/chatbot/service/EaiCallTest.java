package com.shinhancard.chatbot.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest(
//		classes = {EAISkillService.class}
)
public class EaiCallTest {

	@Autowired
	private EAISkillService eaiSkillService;
	
	@Test
	public void eaiTest() {
		Map<String, Object> test29Input = new HashMap<>();
		test29Input.put("CLNN", "P123456789");
		test29Input.put("MO_BJ_TCD", "F57");
		test29Input.put("RG_OFF_INC_F", "N");
		
		eaiSkillService.callEAISkill("CBS00029", test29Input);
		
		
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
