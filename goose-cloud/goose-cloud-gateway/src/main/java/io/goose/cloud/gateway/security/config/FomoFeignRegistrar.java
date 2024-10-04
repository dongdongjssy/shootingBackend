package io.goose.cloud.gateway.security.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;

@Component
public class FomoFeignRegistrar  implements FeignFormatterRegistrar {

    public class DateFormatter implements Formatter<Date> {

        SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");

        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            return formatter.parse(text);
        }

        @Override
        public String print(Date date, Locale locale) {
            return formatter.format(date);
        }
    }
	
	

    //Note: This is not used, seems Date field in object will not trigger the formatter.
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
        SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
        registry.addConverter(Date.class, String.class, date->formatter.format(date));
    }
    
    
	
}
