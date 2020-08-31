/**
 * 
 */
package com.sdgcrm.application.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

public class EmailTemplate {

	private String templateId;

	private String template;

	private Map<String, String> replacementParams;

	public EmailTemplate(String templateId) {
		this.templateId = templateId;
		try {
			this.template = loadTemplate(templateId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			this.template = Constants.BLANK;
		}
	}

	private String loadTemplate(String templateId)  {
		ClassLoader classLoader = getClass().getClassLoader();
		String content = "";
		try {
			
			
			
			InputStream fis = classLoader.getResourceAsStream("email-templates/" + templateId);
			byte[] buffer = new byte[10];
			StringBuilder sb = new StringBuilder();
			while (fis.read(buffer) != -1) {
				sb.append(new String(buffer));
				buffer = new byte[10];
			}
			content = sb.toString();
			fis.close();

			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new Exception("Could not read template with ID = " + templateId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return content;
	}

	public String getTemplate(Map<String, String> replacements) {
		String cTemplate = this.getTemplate();

		if (!AppUtil.isObjectEmpty(cTemplate)) {
			for (Map.Entry<String, String> entry : replacements.entrySet()) {
				System.out.println("looking at" + entry.getKey() +" value: "+ entry.getValue() );
				if(entry.getValue() != null) {
					cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
					
				}
				
			}
		}
		
		return cTemplate;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the replacementParams
	 */
	public Map<String, String> getReplacementParams() {
		return replacementParams;
	}

	/**
	 * @param replacementParams
	 *            the replacementParams to set
	 */
	public void setReplacementParams(Map<String, String> replacementParams) {
		this.replacementParams = replacementParams;
	}

}
