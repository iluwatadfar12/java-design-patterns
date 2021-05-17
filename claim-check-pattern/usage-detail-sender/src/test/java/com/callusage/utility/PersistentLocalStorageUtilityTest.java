package com.callusage.utility;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.callusage.domain.Message;
import com.callusage.domain.MessageData;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;

public class PersistentLocalStorageUtilityTest {

	
	private UsageDetail usageDetail;
	private MessageHeader messageHeader;
	private Message<UsageDetail> messageForUsageDetail;
	
	private IPersistentCommonStorageUtility persistentCommonStorageUtility; 
	
	@Before
	public void setUp() throws Exception {
 
		this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName("input.json");
		this.messageHeader.setDataLocation("C://tmp");
		this.messageHeader.setOperataionName("Testing of PersistentLocalStorageUtility class");
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setUserId("Alan");
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(10);
		
		this.messageForUsageDetail = new Message<>(messageHeader, new MessageData<UsageDetail>(this.usageDetail));
		
		this.persistentCommonStorageUtility = new PersistentLocalStorageUtility();
		
		
	}
	
	@Test
	public void testDropMessageToPersistentStorage() {
		
		try {
			this.persistentCommonStorageUtility.dropMessageToPersistentStorage(this.messageForUsageDetail);
		}catch(Exception e) {

			fail("File drop failed : "+e.getStackTrace());
		}
	}

	@Test
	public void testReadMessageFromPersistentStorage() {
		
		this.persistentCommonStorageUtility.dropMessageToPersistentStorage(this.messageForUsageDetail);
		
		try {
			Message message = this.persistentCommonStorageUtility.readMessageFromPersistentStorage(this.messageHeader);
			assertNotNull(message);
		}catch(Exception e) {

			fail("File read failed : "+e.getStackTrace());
		}
	}

	

}
