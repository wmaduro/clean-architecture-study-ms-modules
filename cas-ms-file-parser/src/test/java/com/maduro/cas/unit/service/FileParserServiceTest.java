package com.maduro.cas.unit.service;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.maduro.cas.core.network.StorageNetwork;
import com.maduro.cas.unit.dto.FileParserDTO;
import com.maduro.cas.unit.dto.StorageDTO;

class FileParserServiceTest {
	
	@Test
	
	void must_ProcessStorageDTO_Successfuly() throws Exception {

		final String data = "\"game\",\"hand\",\"hand_position\",\"user_name\",\"card_sequence\",\"value_won\",\"board\",\"all_in_action_street\",\"action_pre_flop\",\"value_action_pre_flop\",\"action_flop\",\"value_action_flop\",\"action_turn\",\"value_action_turn\",\"action_river\",\"value_action_river\",\"bb\",\"street_ended\",\"show_down\",\"level\"\n" + 
				"\"2655675429   \",\"202713458879 \",2,zloipitbull2212,\"9c 9s\",,,PRE_FLOP,CALL,4660,,,,,,,61,RIVER,true,5\n" + 
				"\"2655675429   \",\"202713458879 \",2,wmaduro,Ts Tc,10170,,PRE_FLOP,RAISE,4900,,,,,,,61,RIVER,true,5\n" ;

		StorageNetwork storageNetworkMock = Mockito.mock(StorageNetwork.class);
		StorageDTO storageDTO = new StorageDTO();
		Mockito.when(storageNetworkMock.loadFromStorage(storageDTO)).thenReturn(data.getBytes());
		
		FileParserDTO fileParserDTO = new FileParserService(storageNetworkMock).processStorage(storageDTO);

	    boolean testConditions = fileParserDTO.getHandDataModelList().size() == 2;
		assertTrue(testConditions);
		
	}

}
