/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 9, 2013-2:06:51 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.service_client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;

import cn.com.timekey.commons.core.log.Log;
import cn.com.timekey.commons.core.log.LogFactory;

/**
 * <b>类名称：</b>SmsServiceClient<br/>
 * <b>类描述：</b>短信服务接口的客户端<br/>
 * <b>创建时间：</b>Aug 9, 2013 2:06:51 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class SmsServiceClient {

	private static final Log LOOGER = LogFactory.getLog(SmsServiceClient.class);

	private String endpointUrl;

	/**
	 * 创建一个新的实例 SmsServiceClient.
	 * 
	 * @param endpointUrl
	 *            webservice接口服务地址
	 */
	public SmsServiceClient(String endpointUrl) {
		super();
		this.endpointUrl = endpointUrl;
	}

	/**
	 * <p>
	 * 发送短信
	 * </p>
	 * 
	 * @param phones
	 *            发送的手机号码
	 * @param message
	 *            短信的内容
	 * @return Boolean，返回true，如果发送成功，否则false。
	 */
	public boolean sendTextMessage(List<String> phones, String message) {
		Validate.notEmpty(phones, "phones cant be empty!");
		Validate.notNull(message, "message cant be null!");
		boolean isSuccess = false;
		List<Object> providers = new ArrayList<Object>();
		providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
		SendSmsBean inputBean = new SendSmsBean();
		inputBean.setMessage(message);
		inputBean.setPhones(phones);
		WebClient client = WebClient.create(endpointUrl + "/send", providers);
		Response r = client.accept("application/json").type("application/json")
				.post(inputBean);
		MappingJsonFactory factory = new MappingJsonFactory();
		JsonParser parser;
		try {
			parser = factory.createJsonParser((InputStream) r.getEntity());
			ServiceResult output = parser.readValueAs(ServiceResult.class);
			String resultMessage = output.getMessage();
			if (Response.Status.OK.getStatusCode() == r.getStatus()
					&& StringUtils.equals(resultMessage, "success")) {
				// 返回200 并且是success才是成功
				isSuccess = true;
			} else {
				LOOGER.error("send message failed, by: %s", resultMessage);
			}
		} catch (JsonParseException e) {
			LOOGER.error(e.getMessage(), e);
		} catch (IllegalStateException e) {
			LOOGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOOGER.error(e.getMessage(), e);
		}
		return isSuccess;
	}

	/**
	 * <b>类名称：</b>SendSmsBean<br/>
	 * <b>类描述：</b>发送短信的参数JsonBean<br/>
	 * <b>创建时间：</b>Aug 9, 2013 2:13:50 PM<br/>
	 * <b>备注：</b><br/>
	 * 
	 * @author kennylee <br />
	 * @version 1.0.0<br/>
	 */
	public static class SendSmsBean {

		private List<String> phones;
		private String message;

		/**
		 * 创建一个新的实例 SendSmsBean.
		 */
		public SendSmsBean() {
			super();
		}

		/**
		 * phones
		 * 
		 * @return the phones
		 */
		public List<String> getPhones() {
			return phones;
		}

		/**
		 * @param phones
		 *            the phones to set
		 */
		public void setPhones(List<String> phones) {
			this.phones = phones;
		}

		/**
		 * message
		 * 
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message
		 *            the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

	}

	/**
	 * <b>类名称：</b>ServiceResult<br/>
	 * <b>类描述：</b>接收返回信息的JsonBean<br/>
	 * <b>创建时间：</b>Aug 9, 2013 2:14:41 PM<br/>
	 * <b>备注：</b><br/>
	 * 
	 * @author kennylee <br />
	 * @version 1.0.0<br/>
	 */
	public static class ServiceResult {

		private String message;

		/**
		 * 创建一个新的实例 ServiceResult.
		 */
		public ServiceResult() {
			super();
		}

		/**
		 * message
		 * 
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message
		 *            the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

	}

}
