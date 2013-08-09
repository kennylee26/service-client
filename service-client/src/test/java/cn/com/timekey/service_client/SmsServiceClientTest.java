/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 9, 2013-2:28:59 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.service_client;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * <b>类名称：</b>SmsServiceClientTest<br/>
 * <b>类描述：</b>测试SmsServiceClient<br/>
 * <b>创建时间：</b>Aug 9, 2013 2:28:59 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class SmsServiceClientTest {

	private static String endpointUrl = "http://192.168.1.222:8080/sms-service/";
	private static String phone = "13800138000";

	@Test
	public void testSendTextMessage() throws Exception {
		SmsServiceClient client = new SmsServiceClient(endpointUrl);
		List<String> phones = Arrays.asList(new String[] { phone });
		String message = "测试一下客户端";
		boolean result = client.sendTextMessage(phones, message);
		Assert.assertTrue(result);
	}

}
