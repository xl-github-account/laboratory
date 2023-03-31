package priv.xl.springboot.core.web.conf;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于Google Gson的HttpMessageConverter
 *
 * @author lei.xu
 * 2023/1/13 9:44 上午
 */
public class GsonHttpMessageConverterExtension extends GsonHttpMessageConverter {

    public GsonHttpMessageConverterExtension() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
        mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
        setSupportedMediaTypes(mediaTypes);
    }

}
