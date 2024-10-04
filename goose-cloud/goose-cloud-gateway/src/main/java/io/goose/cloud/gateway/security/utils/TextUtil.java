package io.goose.cloud.gateway.security.utils;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@Component
public class TextUtil  {	
		
	@Autowired
    private MessageSource tmpMessageSource;
	private static MessageSource messageSource;
	
	@PostConstruct
	public void init() {			
		messageSource = tmpMessageSource;		
	}
	
	
	/**
	 * Get text from messages.locale.properties under resource/i18n folder.
	 * Locale is from session using lang=xx in param.
	 * @param key
	 * @return
	 */	
	public static String message(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		try {
			return messageSource.getMessage(key, new Object[0], locale);
		}
		catch(NoSuchMessageException e) {
			return "";
			//return "No message "+key+" for locale "+locale;
		}
	}
	


}
